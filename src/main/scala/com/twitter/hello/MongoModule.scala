package com.twitter.hello

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class MongoModule extends TwitterModule {

  val hosts = "localhost:27017"
  val database = "event"
  val user = ""
  val password = ""
  private[this] val driver = new MongoDriver
  private[this] val strategy = FailoverStrategy(
      initialDelay = 3.second,
      retries = 10,
      delayFactor = attemptNumber => 1 + attemptNumber * 0.5)

  @Singleton
  @Provides
  def provideConnection(): MongoConnection = {
    val h = hosts.split(",").toList

    driver.connection(nodes = h)
  }

  private[this] def getCollection(conn: MongoConnection,
                                  connectionName: String) = {
    conn.database(database, strategy).map(_.collection[BSONCollection](connectionName))
  }

}
