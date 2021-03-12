package com.sparkray.service

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf


object Books {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().
      setAppName("Books")
    val sc = new SparkContext(conf)
    val path = args(0)

    val data = sc.textFile(path);
    val dataSelection = data.map(book => (
      book.split(",")(1),book.split(",")(6),
      book.split(",")(7)
    ))
    val dataFilter = dataSelection.filter(book =>
      book._3.equals("TX"))

    dataFilter.take(20).foreach(println)

    dataFilter.saveAsTextFile(args(1))

    System.out.println("Done...")


  }

}
