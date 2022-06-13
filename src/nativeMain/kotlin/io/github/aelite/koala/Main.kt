package io.github.aelite.koala

fun main(args: Array<String>) {
    val classpath = Classpath().add(args[0])
    val runtime = Runtime(classpath)
    val Main = runtime.findClass("Main")
    Main.construct().invoke("main")
}
