package io.github.aelite.koala

import okio.FileSystem
import okio.buffer
import okio.Path.Companion.toPath

class Runtime(classpath: Classpath) {
    private val fileSystem = FileSystem.SYSTEM
    private val classes = HashMap<String, Class>()
    private val parser = Parser()

    init {
        this.loadClasspath(classpath)
    }

    fun loadClasspath(classpath: Classpath) {
        classpath.sourceFiles.forEach { sourceFile ->
            val path = sourceFile.toPath()
            val bufferedSource = this.fileSystem.source(path).buffer()
            val clazz = this.parser.parse(bufferedSource)
            this.classes[clazz.name] = clazz
        }
    }

    fun findClass(name: String): Class {
        return this.classes[name] ?: throw Exception("class $name not found")
    }
}
