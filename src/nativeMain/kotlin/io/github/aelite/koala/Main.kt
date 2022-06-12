package io.github.aelite.koala

import io.github.aelite.koala.runtime.KRuntime

fun main() {
    val runtime = KRuntime()
    KString.construct("Hellovlasgal").call("charAt", KNumber.construct(4.0)).call("printString")

    val Cat = runtime.findClass("Cat")
    val Dog = runtime.findClass("Dog")
    Cat.construct().call("makeNoise")
    Dog.construct().call("makeNoise")

    KString.construct("saf")
}

open class KObject(private val clazz: KClass) {
    fun call(name: String, vararg args: KObject): KObject {
        return clazz.findMethod(name).call(this, *args)
    }
}

class KPrimitiveObject<T>(val value: T, clazz: KPrimitiveClass<T>) : KObject(clazz) { }

class KPrimitiveClass<T>(name: String) : KClass(name) {
    fun construct(value: T): KObject {
        return KPrimitiveObject(value, this)
    }
}

val KBoolean = KPrimitiveClass<Boolean>("Boolean")
val KNumber = KPrimitiveClass<Double>("Number")
val KString = KPrimitiveClass<String>("String")
val KVoid = KPrimitiveClass<Unit>("Void")
val KNull = KPrimitiveClass<Unit>("Null")
val TRUE = KBoolean.construct(true)
val FALSE = KBoolean.construct(false)
val VOID = KVoid.construct()
val NULL = KNull.construct()

open class KClass {
    val name: String
    private val base: KClass?
    private val methods: HashMap<String, KMethod>

    constructor(name: String): this(name, null)

    constructor(name: String, base: KClass?) {
        this.name = name
        this.base = base
        this.methods = HashMap()
    }

    fun construct(vararg args: KObject): KObject {
        val instance = KObject(this)
        try { this.findMethod("constructor").call(instance, *args) } catch (_: Exception) { }
        return instance
    }

    fun registerMethod(method: KMethod) {
        this.methods[method.name] = method
    }

    fun findMethod(name: String): KMethod {
        val method = this.methods[name]
        if (method != null) return method
        return this.base?.findMethod(name) ?: throw Exception("method $name not found")
    }
}

class KMethod(
    val name: String,
    private val callback: KMethodCallable
) : KMethodCallable {
    override fun call(self: KObject, vararg args: KObject): KObject {
        return callback.call(self, *args)
    }
}

fun interface KMethodCallable {
    fun call(self: KObject, vararg args: KObject): KObject
}
