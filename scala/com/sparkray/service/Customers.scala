package com.sparkray.service

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Customers {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().
      setAppName("Customer Application")

    val sc = new SparkContext(conf)

    val filePath = args(0)
    val outputPath = args(1)

    val data = sc.textFile(filePath);

    val dataSelection = data.map(cust =>
      (cust.split(",")(1),
      cust.split(",")(6),
      cust.split(",")(7)))

    val dataFilter = dataSelection.filter( f =>
     "TX")
        && (f._3.equals(
        f._2.equals("Plano")))

    //dataFilter.collect();

    //dataFilter.take(20).foreach(println)

    dataFilter.saveAsTextFile(outputPath)

    System.out.println("-------------------------> All Done...")
  }

}
