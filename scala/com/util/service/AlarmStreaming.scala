package com.util.service


import com.typesafe.config.ConfigFactory
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.logging.log4j.LogManager
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, ConsumerStrategies, HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object AlarmStreaming {
  private val LOGGER = LogManager.getLogger(AlarmStreaming.getClass)

  def main(args: Array[String]): Unit = {

    /**
     * Load the config file using ConfigFactory pattern
     */
    val envConfig = ConfigFactory.load().getConfig(args(0))
    val KAFKA_SECURE = envConfig.getString("KAFKA.SECURE")
    val mongoURI = envConfig.getString("mongo.uri")
    val mongoDB = envConfig.getString("mongo.db")
    val mongoCollection = envConfig.getString("mongo.coll_1")
    val conf = new SparkConf().
      set("spark.streaming.kafka.consumer.cache.enabled",
      envConfig.getString("spark.streaming.kafka.consumer.cache.enabled")).
    set("spark.streaming.kafka.consumer.poll.ms",
      envConfig.getString("spark.streaming.kafka.consumer.poll.ms"))

    val ssc = new StreamingContext(conf, Seconds(envConfig.
      getInt("context.interval")))


    LOGGER.info(s"### Loaded the config file ... ")
    val spark = SparkSession.
      builder().
      config(conf).
      enableHiveSupport().
      getOrCreate()


    var kafkaParams = Map[String,Object] (
      "bootstrap.servers" ->
        envConfig.getString("bootstrap.servers"),
      "key.deserializer" ->
        classOf[StringDeserializer],
      "value.deserializer" ->
        classOf[StringDeserializer],
      "group.id" ->
        envConfig.getString("group.id"),
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false : java.lang.Boolean)
    )

    val topics = Array(envConfig.getString("topics"))

    //Config the Kafka Stream
    val stream = KafkaUtils.createDirectStream[String,String](
      ssc,
      PreferConsistent,
      Subscribe[String,String](topics,kafkaParams)
    )

    //Initialize the offset
    var offsetRanges = Array[OffsetRange]()
    stream
      .transform{
        rdd =>
          offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
          rdd
      }
    //Setting up the window interval
      .map(_.value).window(Seconds(envConfig.getInt("window_1.interval")),
    Seconds(envConfig.getInt("window_2.interval")))
      .foreachRDD(rddRaw => {
        val stream00 = spark.read.json(rddRaw)
        //stream00.createOrReplaceTempView("stream01")
        try {

          val alarmAgg = stream00.withColumn("Status",
            when((col("list_count") > 2) &&
              (col("acc_action_list")
                .getItem(0) === "SAVE_JOB") &&
              (col("acc_action_list")
                .getItem(1) === "UNSAVE_JOB") &&
              (col("acc_action_list")
                .getItem(2) === "SAVE_JOB"),
              "USER_Performed"))
            .filter(col("Status") === "USER_Performed")
            .orderBy("timestamp","Status")
            .drop("job_id","acc_action_list","list_count")

          //Some more business logics...

          alarmAgg.write.mode("append")
            .format(envConfig.getString("mongo.driver"))
            .option("uri",mongoURI)
            .option("database",mongoDB)
            .option("collection",mongoCollection)
            .save()

          LOGGER.debug(s"Alarms pushed to Mongo collection : $mongoCollection")
        }
        catch {
          case exception:
            org.apache.spark.sql.AnalysisException =>
            LOGGER.error("Analysis Exception "+exception.getMessage())

          case unknown:
            Exception =>
            LOGGER.error("Exception in AlarmStreaming "+unknown.getMessage)
          }
        finally {
          stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
          LOGGER.debug("Offset commited to kafka...")
        }
      })

    LOGGER.info(("### All Done..."))

  }

}

