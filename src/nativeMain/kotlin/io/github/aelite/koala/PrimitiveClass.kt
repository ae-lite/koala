package io.github.aelite.koala

class PrimitiveClass<T>(name: String) : InstantiableClass(name) {
    private val pool: HashMap<T, BoxedObject<T>> = HashMap()

    fun construct(value: T): BoxedObject<T> {
        return this.pool.getOrPut(value) { BoxedObject(this, value) }
    }
}
