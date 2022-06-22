package io.github.aelite.koala.integration

import io.github.aelite.koala.Class
import io.github.aelite.koala.MockedMethod
import io.github.aelite.koala.Parser
import kotlin.test.Test
import kotlin.test.assertEquals

class HelloWorldIntegrationTest {

    private val source = """
        class Main

        native stdoutPrint(value: String)

        main() {
            this.stdoutPrint(value: "hello world!")
        }
    """

    private var stdoutPrint = MockedMethod("stdoutPrint")
    private var Main: Class = Parser().parse(this.source).registerMethod(this.stdoutPrint) as Class

    @Test
    fun executesCorrectly() {
        this.Main.construct().invoke("main")
        assertEquals(1, this.stdoutPrint.invocations, "stdoutPrint was not called exactly once")
    }
}
