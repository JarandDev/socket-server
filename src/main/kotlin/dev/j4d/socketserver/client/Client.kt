package dev.j4d.socketserver.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket

class Client(private val socket: Socket) {

    fun run() = runBlocking(Dispatchers.IO) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)

        println("Accepted client connection from: ${socket.inetAddress.hostAddress}")

        launch {
            println("Sending ping every 10th second")
            while (!socket.isClosed) {
                writer.println("PING")
                println("Sent: PING")
                Thread.sleep(10_000L)
            }
        }

        launch {
            println("Reading from client")
            while (!socket.isClosed) {
                val line = reader.readLine()
                println("Received: $line")
                if (line == null) {
                    socket.close()
                } else if (line == "PING") {
                    writer.println("PONG")
                    println("Sent: PONG")
                }
            }
        }
    }
}
