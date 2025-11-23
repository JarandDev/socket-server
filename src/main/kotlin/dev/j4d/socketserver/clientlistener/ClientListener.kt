package dev.j4d.socketserver.clientlistener

import dev.j4d.socketserver.client.Client
import java.net.ServerSocket
import java.net.SocketException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ClientListener(
    private val acceptPort: Int,
    private val acceptTimeout: Long,
    private val restartOnTimeout: Boolean,
) {
    private var running = true

    fun listen() =
        runBlocking(Dispatchers.IO) {
            while (running) {
                try {
                    println("Listening for clients on port: $acceptPort")
                    val server = ServerSocket(acceptPort)
                    launch {
                        Thread.sleep(acceptTimeout)
                        server.close()
                    }

                    val socket = server.accept()

                    launch {
                        Client(socket).run()
                    }

                    server.close()
                } catch (_: SocketException) {
                    if (!restartOnTimeout) {
                        running = false
                    }
                }
            }
        }
}
