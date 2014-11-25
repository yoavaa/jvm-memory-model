package com.wix

/**
  * Created by yoava on 7/22/14.
  */

class MyClassLoader(parent: ClassLoader) extends ClassLoader(parent) {
  override def loadClass(className: String):Class[_] = {
    println(s"loading class $className")
    super.loadClass(className)
  }
}

object PrintLoadedClasses extends App{
  val cl = new MyClassLoader(this.getClass.getClassLoader)
  val bClass = cl.loadClass("com.wix.B")
  val b = bClass.newInstance()
  val bMethod = bClass.getMethod("useClassA")
  bMethod.invoke(b)
}
