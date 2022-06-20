package io.github.aelite.koala

class MockedMethod(name: String) : Method(name) {
    var invocations = 0

    override fun invoke(self: Object, vararg args: Object): Object {
        this.invocations++
        return self
    }
}
