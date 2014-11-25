package com.wix

import scala.io.StdIn
import scala.util.Random

/**
 * Created by yoava on 7/28/14.
 */
object MemoryUsage extends App {

  @volatile var running = true
  val threads: List[Thread] = List.fill(10)(new Thread(new Runnable() {
    def run() {
      var list: List[Array[AccessList]] = List()
      while (running) {
        Thread.sleep(100)
        if (Random.nextInt(500) == 1) {
          println(s"${Thread.currentThread().getName} dereferencing a list of ${list.size} elements.")
          list = List()
        }
        else {
          list = Array.fill(Random.nextInt(1000)){randomAccessList} :: list
          if (list.size % 100 == 0)
            println(s"${Thread.currentThread().getName} has ${list.size} elements taking ${list.foldLeft(0)(_ + _.size) * 12} bytes")
        }
      }
    }
  }))

  threads.foreach(_.start)

  StdIn.readLine()
  running = false

  threads.foreach(_.join)

  def randomAccessList = {
    def randomByte: Byte = {
      Random.nextInt(255).asInstanceOf[Byte]
    }
    AccessList(Random.nextInt(65000), IPAddress(randomByte, randomByte, randomByte, randomByte),
      IPAddress(randomByte, randomByte, randomByte, randomByte))
  }
}

case class IPAddress(n1: Byte, n2: Byte, n3: Byte, n4: Byte)
case class AccessList(permit: Int, ip: IPAddress, mask: IPAddress)
