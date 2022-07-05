package io.github.aelite.koala

class MockedMethod(name: String, parameters: List<FormalParameter>) : Method(name, parameters, Class("MOCKED")) {
    var invocations = 0

    override fun onInvoke(self: Object, parameters: Map<String, Object>): Object {
        this.invocations++
        return self
    }
}
