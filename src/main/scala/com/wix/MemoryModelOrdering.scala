package com.wix

import scala.collection.mutable
/**
 * Created by yoava on 7/25/14.
 */
object MemoryModelOrdering extends App {

  object Result extends Enumeration{
    type Result = Value
    val r00, r01, r10, r11 = Value
  }

  def testOrdering: Result.Result = {
    var x = 0
    var y = 0
    var i = 0
    var j = 0

    val t1 = new Thread(new Runnable {
      def run() {
        Thread.sleep(1)
        x=1
        j=y
      }
    })
    val t2 = new Thread(new Runnable {
      def run() {
        Thread.sleep(1)
        y=1
        i=x
      }
    })

    t1.start()
    t2.start()

    t1.join()
    t2.join()

    if ((i==0) && (j==0))
      Result.r00
    else if ((i==1) && (j==0))
      Result.r10
    else if ((i==0) && (j==1))
      Result.r01
    else
      Result.r11

  }

  val map = mutable.Map(Result.r00 -> 0,
    Result.r10 -> 0,
    Result.r01 -> 0,
    Result.r11 -> 0)

  for (i <- 1 to 10000) {
    val res = testOrdering
    map.update(res, map.get(res).get + 1)
  }

  map.foreach(p => println(s"${p._1} -> ${p._2}"))

}
