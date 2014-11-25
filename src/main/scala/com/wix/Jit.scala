package com.wix

import scala.util.Random
import scala.io.StdIn

/**
 * Created by yoava on 7/24/14.
 */
object Jit extends App {

  println(
    """Which benchmark to run?
      |1. for 0 to 100
      |2. while 0 to 100
      |3. append to a list
      |4. sort a list of 1000 elements
      |5. append to a vector
      |6. sort a vector of 1000 elements
    """.stripMargin)
  val input = StdIn.readLine()
  input match {
    case "1" => benchmarkFor
    case "2" => benchmarkWhile
    case "3" => benchmarkListAppend
    case "4" => benchmarkListSort
    case "5" => benchmarkVectorAppend
    case "6" => benchmarkVectorSort
    case _ => "What???"
  }

  def time(p: => Unit): Long = {
    val start = System.nanoTime()
    p
    System.nanoTime-start
  }

  def benchmark(name: String)(reset: => Unit)(test: => Unit) {
    println(s"$name:")
    for (cycle <- 1 to 30) {
      var sum: Long = 0
      reset
      for (i <- 1 to 1000)
        sum = sum + time {
          test
        }
      println(s"Cycle $cycle: ${sum/1000} ns")
    }
    println()
  }

  def benchmarkFor {
    benchmark("scala for 0 to 100") {} {
      for (i <- 0 to 100) {}
    }
  }

  def benchmarkWhile {
    benchmark("while 0 to 100") {} {
      var i=0
      while (i < 100)
        i = i + 1
    }
  }

  def benchmarkListAppend {
    var list: List[Int] = List()
    benchmark("append random int to a list") {
      list = List()
    } {
      list = Random.nextInt(10) :: list
    }
  }

  def benchmarkListSort{
    var list: List[Int] = List()
    for (i <- 0 to 1000)
      list = Random.nextInt(10) :: list

    benchmark(s"sort a list of size ${list.size}") {} {
      list.sorted
    }
  }

  def benchmarkVectorAppend {
    var vector: Vector[Int] = Vector()
    benchmark("append random int to a vector") {
      vector = Vector()
    } {
      vector = vector :+ Random.nextInt(10)
    }
  }

  def benchmarkVectorSort {
    var vector: Vector[Int] = Vector()
    for (i <- 0 to 1000)
      vector = vector :+ Random.nextInt(10)

    benchmark(s"sort a vector of size ${vector.size}") {} {
      vector = vector.sorted
    }
  }
}
