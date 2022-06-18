package io.github.aelite.koala

fun main(args: Array<String>) {
    if (args.size != 1) throw Exception("no input found")
    val classpath = Classpath().add(args[0])
    val runtime = Runtime()
    runtime.loadClasspath(classpath)
    val Main = runtime.findClass("Main") as Class
    Main.construct().invoke("main")
}
