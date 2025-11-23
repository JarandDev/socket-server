package dev.j4d.socketserver

import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `main can launch`() {
        main(args = arrayOf("acceptPort=8080", "acceptTimeout=100", "restartOnTimeout=false"))
    }
}
