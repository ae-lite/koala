package io.github.aelite.koala

import okio.FileSystem
import okio.buffer
import okio.Path.Companion.toPath

class Runtime {
    private val fileSystem = FileSystem.SYSTEM
    private val classes = HashMap<String, Interface>()
    private val parser = Parser()

    init {
        this.registerClass(io.github.aelite.koala.stdlib.lang.Boolean)
        this.registerClass(io.github.aelite.koala.stdlib.lang.Number)
        this.registerClass(io.github.aelite.koala.stdlib.lang.String)
    }

    fun loadClasspath(classpath: Classpath) {
        classpath.sourceFiles.forEach { sourceFile ->
            val path = sourceFile.toPath()
            val bufferedSource = this.fileSystem.source(path).buffer()
            val clazz = this.parser.parse(bufferedSource.readUtf8())
            this.registerClass(clazz)
        }
    }

    fun registerClass(clazz: Interface) {
        this.classes[clazz.name] = clazz
    }

    fun findClass(name: String): Interface {
        return this.classes[name] ?: throw Exception("class $name not found")
    }
}
