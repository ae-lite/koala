package io.github.aelite.koala.runtime

import io.github.aelite.koala.*
import kotlin.math.roundToInt

class KRuntime {
    private val classes: HashMap<String, KClass> = HashMap()

    constructor() {
        val Animal = KClass("Animal")
        this.registerClass(Animal)
        Animal.registerMethod(KMethod("makeNoise") { _, _ ->
            println("I do make some generic noise")
            VOID
        })

        val Cat = KClass("Cat", Animal)
        this.registerClass(Cat)
        Cat.registerMethod(KMethod("makeNoise") { _, _ ->
            println("miau!")
            VOID
        })
        Cat.registerMethod(KMethod("constructor") { _, _ ->
            VOID
        })

        val Dog = KClass("Dog", Animal)
        this.registerClass(Dog)
        Dog.registerMethod(KMethod("constructor") { _, _ ->
            VOID
        })

        std()
    }

    private fun std(){
        KString.registerMethod(KMethod("printString") { self, _ ->
            if (self !is KPrimitiveObject<*> || self.value !is String) throw Exception()
            println(self.value)
            VOID
        })
        KString.registerMethod(KMethod("charAt") { self, args ->
            val index = args[0]
            if (self !is KPrimitiveObject<*> || self.value !is String) throw Exception()
            if (index !is KPrimitiveObject<*> || index.value !is Double) throw Exception()
            KString.construct(self.value[index.value.roundToInt()].toString())
        })
    }

    fun registerClass(clazz: KClass) {
        this.classes[clazz.name] = clazz
    }

    fun findClass(name: String): KClass {
        return this.classes[name] ?: throw Exception("class $name not found")
    }
}
