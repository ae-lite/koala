package io.github.aelite.koala

class BoxedObject<T>(clazz: InstantiableClass, val value: T) : Object(clazz)
