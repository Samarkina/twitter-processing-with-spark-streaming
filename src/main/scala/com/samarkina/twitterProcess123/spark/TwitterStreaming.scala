package com.samarkina.twitterProcess123.spark

import com.samarkina.twitterProcess123.twitter._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.OAuthAuthorization
import twitter4j.Status
import org.apache.spark.streaming.twitter.TwitterUtils

object TwitterStreaming {

    def getTwitts() {

      val appName = "TwitterData"
      val conf = new SparkConf()
      conf.setAppName(appName).setMaster("local[10]")
      val ssc = new StreamingContext(conf, Seconds(5))


      val cb = Twitter.keys()

      val auth = new OAuthAuthorization(cb.build)
      val tweets = TwitterUtils.createStream(ssc, Some(auth))

      // Now extract the text of each status update into RDD's using map()
      val statuses = tweets.map{
//        println("********" + Thread.currentThread().getName() + "********")
        status => status.getText()
        println("********" + Thread.currentThread().getName() + "********")
      }
      println("********222222" + Thread.currentThread().getName() + "********")

      statuses.print()

//      tweets .saveAsTextFiles("tweets", "json")
      ssc.start()
      ssc.awaitTermination()
    }
}