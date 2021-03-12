package com.util.service

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.{col, count, length, substring, sum, to_timestamp, trim, window}
import org.apache.spark.sql.types.{IntegerType, LongType}

object StreamAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.
      builder.
      getOrCreate()

    //Load the csv files from local filesystem
    val users = spark.read.option("header","true").
      csv("file:///opt/data/share01/rkumar/stream/users.csv")
    val sessions = spark.read.option("header","true").
      csv("file:///opt/data/share01/rkumar/stream/sessions.csv")
    val videos = spark.read.option("header","true").
      csv("file:///opt/data/share01/rkumar/stream/video_plays.csv")

    //Stat for daily user count
    val dailyCount = users.
      filter(length(trim(col("user_first_seen"))) > 0). //remove the empty columns
      withColumn("Date", substring($"user_first_seen",1,10)). //parse to pick only date
      groupBy("Date").
      agg(count("user_id").alias("UserCount")).
      orderBy("Date")


    //Stat for weekly user count
    val weeklyCount = users.
      withColumn("DateTime",
        to_timestamp($"user_first_seen",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).  //convert the field to timestamp
      groupBy(window(col("DateTime"),  //apply window fn to slide for 1 week
        "1 week","1 week","64 hours")).
      agg(count("user_id") as "weekly_count").
      select("window.start","window.end","weekly_count").
      orderBy("window.start")

    //Percentage of users played video on daily basis
    val dailyVideoCount = videos.
      withColumn("Date",
        substring($"timestamp",1,10)). //parse to pick only date
      group("Date").
      agg(((count("user_id") / users.count())) * 100 as "UserCount"). //calculate the percentage for users
      orderBy("Date")

    val WEEK = 7

    val wklyVideoCount = videos.
      withColumn("DateTime",
        to_timestamp($"timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).
      groupBy(window(col("DateTime"),
        "1 week", "1 week", "64 hours")).
      agg(((count("user_id") / users.count()) * 100) / WEEK as "UserCount"). //calculate the weekly percentage count(user.id) / tot_users and multiply by 100 and again div by 7 days
      select("window.start","window.end","UserCount").
      orderBy("window.start")

    //Percentage of videos lasting more than 10 seonds
    val video10SecMore = videos.
      filter(col("viewed_seconds") > 10).
      agg((count("viewed_seconds") / videos.count()) *100 // calculate the percentage
      as "% of sessions played > 10 seconds")

    //The app page where user spend most of the time
    val mostFavPage = sessions.
      withColumn("Duration_Sec", //convert the timestamp to long and subtract end_time - start_time
      to_timestamp($"end_timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").
      cast(LongType) -
      to_timestamp($"start_timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").
      cast(LongType)).
      groupBy("pages_viewed").
      agg(sum("Duration_Sec").alias("total_duration")).
      orderBy(col("total_duration").desc)

//List of Program watched for long time

    /*val mostFavProgram = videos.
      withColumn("Intervel", //Calculate the intervel by subtract total duration with viewed duration
        $"program_duration_seconds".cast(IntegerType)
      - $"viewed_seconds".cast(IntegerType)).orderBy("Intervel").
      select(
        "program_title","program_duration_seconds",
        "viewed_seconds","Intervel").
      withColumnRenamed("program_title", "ProgramWatchedForLongTime").
      withColumnRenamed("viewed_seconds", "MaxWatchedSeconds")
      )*/




  }

}
