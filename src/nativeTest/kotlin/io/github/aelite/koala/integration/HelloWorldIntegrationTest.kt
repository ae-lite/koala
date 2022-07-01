package io.github.aelite.koala.integration

import io.github.aelite.koala.Class
import io.github.aelite.koala.FormalParameter
import io.github.aelite.koala.MockedMethod
import io.github.aelite.koala.Parser
import io.github.aelite.koala.stdlib.lang.String
import kotlin.test.Test
import kotlin.test.assertEquals

class HelloWorldIntegrationTest {

    private val source = """
        class Main

        native stdoutPrint(value: String)

        main() {
            this.stdoutPrint("hello world!")
        }
    """

    private var stdoutPrint = MockedMethod("stdoutPrint", listOf(FormalParameter("value", String)))
    private var Main: Class = Parser().parse(this.source).registerMethod(this.stdoutPrint) as Class

    @Test
    fun executesCorrectly() {
        this.Main.construct().invoke("main")
        assertEquals(1, this.stdoutPrint.invocations, "stdoutPrint was not called exactly once")
    }
}
