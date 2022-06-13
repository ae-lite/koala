package io.github.aelite.koala

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Classpath {
    val sourceFiles = HashSet<String>()
    private val fileSystem = FileSystem.SYSTEM

    fun add(name: String): Classpath {
        val path = this.fileSystem.canonicalize(name.toPath(true))
        if (this.fileSystem.metadata(path).isDirectory) {
            this.fileSystem.list(path).map(Path::toString).forEach(this::add)
        } else {
            val pattern = Regex(".+\\.koala")
            if (pattern.matches(path.name))
                this.sourceFiles.add(path.toString())
        }
        return this
    }
}
