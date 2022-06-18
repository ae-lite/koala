import io.github.aelite.koala.Class
import io.github.aelite.koala.NativeMethod
import io.github.aelite.koala.Parser
import okio.Buffer
import kotlin.test.Test
import kotlin.test.assertTrue

class HelloWorldIntegrationTest {

    private var Main: Class = Parser().parse(Buffer().writeUtf8("""
        class Main

        private native stdoutPrint(value: String)

        public main() {
            this.stdoutPrint("hello world!")
        }
    """)) as Class

    private var stdoutPrintCalled = false

    init {
        Main.registerMethod(NativeMethod("stdoutPrint") { self, args ->
            stdoutPrintCalled = true
            self
        })
    }

    @Test
    fun `executes correctly - invoking main calls stdoutPrint`() {
        Main.construct().invoke("main")
        assertTrue(stdoutPrintCalled, "stdoutPrint was not called")
    }
}
