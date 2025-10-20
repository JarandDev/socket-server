package dev.j4d.socketserver.clientlistener

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket
import java.net.SocketException

class ClientListener(
    private val acceptPort: Int,
    private val acceptTimeout: Long
) {

    fun listen() {
        try {
            println("Listening for clients on port: $acceptPort")
            val server = ServerSocket(acceptPort)
            runBlocking(Dispatchers.IO) {
                launch {
                    Thread.sleep(acceptTimeout)
                    server.close()
                }
            }
            val client = server.accept()
        } catch (_: SocketException) {
            println("Server closed")
        }
    }
}
