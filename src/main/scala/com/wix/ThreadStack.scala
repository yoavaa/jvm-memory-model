package com.wix

/**
 * Created by yoava on 7/28/14.
 */
object ThreadStack extends App {

  var count = 0

  def doCount {
    count = count + 1
    doCount
    if (count > 0)
      println()
  }

  try {
    doCount
  }
  catch {
    case t => println(count)
  }

}
