package com.wix

import scala.io.StdIn

/**
 * Created by yoava on 7/26/14.
 */
object Visability extends App {

  println(
    """Which test to run?
      |1. regular var
      |2. volatile
    """.stripMargin)
  val input = StdIn.readLine()
  input match {
    case "1" => regular()
    case "2" => volatile()
    case _ => "What???"
  }


  def regular() {

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

  def volatile() {

    @volatile var running = true

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

}
