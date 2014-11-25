package com.wix

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by yoava on 7/26/14.
 */
object Sync101 extends App {
  var count = new AtomicInteger(0)
  val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
    def run() {
        for (i <- 1 to 1000)
          count.incrementAndGet()
    }
  }))

  threads.foreach(_.start)

  threads.foreach(_.join)

  println(count)
}
