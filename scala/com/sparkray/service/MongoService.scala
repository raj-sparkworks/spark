package com.sparkray.service

import org.apache.spark.sql.SparkSession

object MongoService {

  def main(args: Array[String]): Unit = {

    val spark=SparkSession.builder.master("local").getOrCreate()

    //Read from local file

    val cars = spark.read.option("header","true").
      csv("file:///ClouderaShared/USA_cars_datasets.csv")

cars.printSchema()

    //Load to MongoDB

    cars.write.
      format("com.mongodb.spark.sql.DefaultSource").
      option("mode","append").
      option("uri","mongodb://localhost:27017").
      option("database","cars_db").
      option("collection","cars_scala_prg").
      save()


  }

}
