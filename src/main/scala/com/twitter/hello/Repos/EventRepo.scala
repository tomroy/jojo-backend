package com.twitter.hello.Repos

import com.google.inject.Inject
import com.mongodb.ServerAddress
import com.mongodb.connection.ClusterSettings
import com.twitter.finagle.Service
import com.twitter.finagle.http.Request
import com.twitter.hello.AddRequest
import com.twitter.util.Future
import org.mongodb.scala._

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class EventRepo {
  // To directly connect to the default server localhost on port 27017
  val mongoClient: MongoClient = MongoClient()

  // Use a Connection String
//  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

//  // or provide custom MongoClientSettings
//  val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("localhost")).asJava).build()
//  val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
//  val mongoClient: MongoClient = MongoClient(settings)

  val database: MongoDatabase = mongoClient.getDatabase("mydb")
  val collection: MongoCollection[Document] = database.getCollection("test");

  val doc: Document = Document("name" -> "Tom",
                               "type" -> "database",
                               "count" -> 1,
                               "info" -> Document("x" -> 203, "y" -> 102))


  def insert(request: Request) : Unit = {

    val observable: Observable[Completed] = collection.insertOne(doc)

    // Explictly subscribe:
    observable.subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = println("Inserted")

      override def onError(e: Throwable): Unit = println("Failed")

      override def onComplete(): Unit = println("Completed")
    })

  }
}
