package com.wix

import java.util.concurrent.atomic.AtomicInteger
import scala.io.StdIn
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by yoava on 7/26/14.
 */
object Sync101 extends App {

  println(
    """Which test to run?
      |1. counter
      |2. volatile counter
      |3. lock counter - synchronized
      |4. lock counter - re-entrant lock
      |5. atomic counter
    """.stripMargin)
  val input = StdIn.readLine()
  input match {
    case "1" => doCountInt()
    case "2" => doCountVolatile()
    case "3" => doCountSync()
    case "4" => doCountReentrantLock()
    case "5" => doCountAtomic()
    case _ => "What???"
  }


  def doCountInt() {
    var countInt = 0
    class IntAdder extends Runnable {
      def run() {
        for (i <- 1 to 1000)
          countInt = countInt + 1
      }
    }

    val threads: List[Thread] = List.fill(10)(new Thread(new IntAdder))

    threads.foreach(_.start)
    threads.foreach(_.join)
    println(countInt)
  }

  def doCountVolatile() {
    @volatile var countVol = 0

    val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
      def run() {
        for (i <- 1 to 1000)
          countVol = countVol + 1
      }
    }))

    threads.foreach(_.start)
    threads.foreach(_.join)
    println(countVol)
  }

  def doCountSync() {
    var countSync = 0

    val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
      def run() {
        for (i <- 1 to 1000)
          synchronized {
            countSync = countSync + 1
          }
      }
    }))

    threads.foreach(_.start)
    threads.foreach(_.join)
    println(countSync)
  }

  def doCountReentrantLock() {
    var countLock = 0
    val lock = new ReentrantLock()

    val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
      def run() {
        for (i <- 1 to 1000) {
          lock.lock()
          try {
            countLock = countLock + 1
          }
          finally {
            lock.unlock()
          }
        }
      }
    }))

    threads.foreach(_.start)
    threads.foreach(_.join)
    println(countLock)
  }

  def doCountAtomic() {
    val count = new AtomicInteger(0)

    val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
      def run() {
        for (i <- 1 to 1000) {
          count.incrementAndGet()
        }
      }
    }))

    threads.foreach(_.start)
    threads.foreach(_.join)
    println(count.get)
  }
}
