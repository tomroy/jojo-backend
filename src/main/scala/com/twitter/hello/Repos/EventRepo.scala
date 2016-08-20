package com.twitter.hello.Repos

import com.twitter.hello.Response.AddResponse
import org.mongodb.scala._

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class EventRepo {
  // To directly connect to the default server localhost on port 27017
  private[this] val mongoClient: MongoClient = MongoClient()

  // Use a Connection String
//  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

//  // or provide custom MongoClientSettings
//  val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("localhost")).asJava).build()
//  val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
//  val mongoClient: MongoClient = MongoClient(settings)

  private[this] val database: MongoDatabase = mongoClient.getDatabase("mydb")
  private[this] val collection: MongoCollection[Document] =
    database.getCollection("test");

//  private[this] val doc: Document = Document(
//      "name" -> "Tom",
//      "type" -> "database",
//      "count" -> 1,
//      "info" -> Document("x" -> 203, "y" -> 102))

  def insert(request: String): Unit = {
    println("request = " + request)
    val doc: Document = Document(request)

    val observable: Observable[Completed] = collection.insertOne(doc)

    // Explictly subscribe:
    observable.subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = println(request + " Inserted")

      override def onError(e: Throwable): Unit = println(request + " Failed")

      override def onComplete(): Unit = println(request + " Completed")
    })
  }
}
