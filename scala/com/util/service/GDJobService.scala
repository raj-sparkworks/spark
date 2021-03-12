package com.util.service

import com.typesafe.config.ConfigFactory
import org.apache.logging.log4j.LogManager
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object GDJobService {
private val LOGGER = LogManager.getLogger(GDJobService.getClass)

  def main(args: Array[String]): Unit = {

    /**
     * Load the config file using ConfigFactory pattern
     */
    //val envConfig = ConfigFactory.load().getConfig(args(0))
    //var filePath = envConfig.getString("file.path")
    //LOGGER.info(s"### Loaded the config file ... : $filePath")
    val spark = SparkSession.
      builder().
      //enableHiveSupport().
      getOrCreate()

    spark.sparkContext.setLogLevel("WARN")
    import spark.sqlContext.implicits._
    val filePath="C:\\Spark\\temp"
    val rawData = spark.read.option("header","true").csv(filePath)
    val employeer = rawData.filter(rawData("account_type") === "EMPLOYER")
    val user = rawData.filter(rawData("account_type") === "USER")

    //Q1 - Account Id for Employeer
    val accountId = employeer.select("account_id").distinct()

    System.out.println("############### Account Id for Employeer ########################")
    accountId.show()

    //Q2 - JobId for each employeer where the job is not yet removed
    val jobId = employeer.filter(employeer("account_action") =!= "REMOVE_JOB").distinct()
    System.out.println("############### Job Id for Employeer where job is not yet removed ########################")
    jobId.show()

    /**
     * Convert the String value to timestamp
     */
    val raw00 = rawData.withColumn("actual_tm",
      to_timestamp(col("timestamp"),
        "yyyy-MM-dd HH:mm:ss"))


    /**
     * Create a window function to get the rows from previous to next rows
     * Window.currentRow = 0
     * Window.unboundedPreceding = Long.MinValue
     * Window.unboundedFollowing = Long.MaxValue
     */
    //Q3 - User who saved a job, unsaved it and saved it again
    val userWindowSpec = Window.partitionBy("account_id").
      orderBy("timestamp","account_id").
      rowsBetween(-1,1)
    /**
     * Calculate the count of actions
     */
    val userActionList = user.withColumn("acc_action_list",
      collect_list(col("account_action")).
        over(userWindowSpec)).
      withColumn("list_count",
        size(col("acc_action_list")))

    /**
     * Aggregate the Business logic SAVE_JOB followed by UNSAVE_JOB &
     * again SAVE_JOB for a user
     */
    val userActionAgg = userActionList.withColumn("Status",
      when((col("list_count") > 2) &&
        (col("acc_action_list").getItem(0) === "SAVE_JOB") &&
        (col("acc_action_list").getItem(1) === "UNSAVE_JOB") &&
        (col("acc_action_list").getItem(2) === "SAVE_JOB"),
        "USER_Performed")).
      filter(col("Status") === "USER_Performed").
      orderBy("timestamp","Status").
      drop("job_id","acc_action_list","list_count")

    System.out.println("############### User who Save the job, unsaved and saved it again  ########################")
    userActionAgg.show()

    //Q4 - The 2 days which had the most increase in saved job compare to its previous day
    /**
     * Calculate the count of account_action per day
     */
    val raw01 = raw00.groupBy(to_date(col("actual_tm"))
      as "load_tm").agg(count(col("account_action"))
      as "tot_action" )

    val windowSpec = Window.partitionBy().orderBy("load_tm")

    /**
     * Implementing the log() to compare the previous count
     */
    val jobCrossCount = raw01.withColumn("prev_count",
      lag(col("tot_action"),1).over(windowSpec))

    /**
     * Calculate if the current day count is more than the previous day
     * job count
     */
    val jobSpike = jobCrossCount.withColumn("SAVED_Jobs_Spike",
      when(isnull(col("tot_action") -
      col("prev_count")),0).
        otherwise(col("tot_action") -
        col("prev_count"))).
      sort(desc("SAVED_Jobs_Spike")).
      drop("tot_action","prev_count")

    System.out.println("############### Most increase in saved job compare to its previous day ########################")
    jobSpike.show()

    //Q5 - The bad data
    /**
     * Creating the temp dataframe for the constant values
     * account_type & account_action
     */
    val account_type = Seq(
      (0,"EMPLOYER"),
      (1,"USER")
    ).toDF("id","values")

    val account_action = Seq(
      (-1,"REMOVE_JOB"),
      (0,"SAVE_JOB"),
      (1,"ADD_JOB")
    ).toDF("id","values")

    /**
     * Lookout for the bad data because of account_type
     * by joining raw_data source with the account_type df
     */
    val badData00 = rawData.join(broadcast(account_type),
      rawData("account_type") === account_type("values"),
    "leftanti")

    val badData01 = rawData.join(broadcast(account_action),
      rawData("account_action") === account_action("values"),
    "leftanti")

    val badData = badData00.union(badData01)
    System.out.println("############### The bad data ########################")
    badData.show()

    LOGGER.info(("### All Done..."))

  }

}
