package com.wix

/**
 * Created by yoava on 7/26/14.
 */
object Visability extends App {

  var running = true

  val t1 = new Thread(new Runnable {
    def run() {
      var count = 0
      while (running)
        count = count + 1
      println(count)
    }
  })

  val t2 = new Thread(new Runnable {
    def run() {
      Thread.sleep(100)
      running = false
      println("thread 2 set running to false")
    }
  })

  t1.start()
  t2.start()

  t1.join()
  t2.join()
}
