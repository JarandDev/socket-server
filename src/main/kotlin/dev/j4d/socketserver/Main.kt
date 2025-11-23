package dev.j4d.socketserver

import dev.j4d.socketserver.clientlistener.ClientListener

fun main(args: Array<String> = emptyArray()) {
    val arguments = args.associate { it.split("=")[0] to it.split("=")[1] }

    println("New version")

    val clientListener =
        ClientListener(
            acceptPort = arguments["acceptPort"]?.toInt() ?: 8080,
            acceptTimeout = arguments["acceptTimeout"]?.toLong() ?: 60_000L,
            restartOnTimeout = arguments["restartOnTimeout"]?.toBoolean() ?: true,
        )

    clientListener.listen()
}
