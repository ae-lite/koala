package io.github.aelite.koala

class MockedMethod(name: String) : Method(name, listOf(), Class("MOCKED")) {
    var invocations = 0

    override fun onInvoke(self: Object, parameters: Map<String, Object>): Object {
        this.invocations++
        return self
    }
}
