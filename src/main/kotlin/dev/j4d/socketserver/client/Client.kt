package dev.j4d.socketserver.client

import dev.j4d.socketserver.command.Ping
import dev.j4d.socketserver.command.Pong
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.time.Instant
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Client(
    private val socket: Socket,
) {
    fun run() =
        runBlocking(Dispatchers.IO) {
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)

            launch {
                while (!socket.isClosed) {
                    val ping =
                        Ping(
                            id = UUID.randomUUID(),
                            type = "ClientAlive",
                            time = Instant.now(),
                        )
                    val output = ping.serialize()
                    writer.println(output)
                    println("Output: $output")
                    Thread.sleep(10_000L)
                }
            }

            launch {
                while (!socket.isClosed) {
                    val line = reader.readLine()
                    println("Input: $line")
                    if (line == null) {
                        socket.close()
                    } else if (line.startsWith("PING")) {
                        val ping = Ping.deserialize(line = line)
                        println("Ping received with id: ${ping.id}")
                        val pong = Pong.from(ping = ping)
                        val output = pong.serialize()
                        writer.println(output)
                        println("Output: $output")
                    } else if (line.startsWith("PONG")) {
                        val pong = Pong.deserialize(line = line)
                        println("Pong received with id: ${pong.id} triggered by ping with id: ${pong.pingId}")
                    }
                }
            }
        }
}
