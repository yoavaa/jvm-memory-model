package com.wix

import java.net.URLClassLoader

/**
  * Created by yoava on 7/22/14.
  */
object PrintClassLoaders extends App {
   def listJars(classLoader: ClassLoader) {
     println()
     println(classLoader)
     println("===============")
     classLoader match {
       case urlcl: URLClassLoader =>
         urlcl.getURLs.foreach(println(_))
       case _ =>
     }
     if (classLoader.getParent != null)
         listJars(classLoader.getParent)
   }
   listJars(this.getClass.getClassLoader)
   import java.util.List
 }
