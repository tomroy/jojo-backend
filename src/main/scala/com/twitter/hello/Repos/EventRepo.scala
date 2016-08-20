package com.twitter.hello.Repos

import java.util.concurrent.TimeUnit

import com.mongodb.client.model.{Filters, UpdateOptions}
import com.twitter.finagle.http.Request
import com.twitter.hello.JsonUtil
import com.twitter.hello.Response.EventResponse
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala.model.Updates._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

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

      override def onNext(result: Completed): Unit =
        println(request + " Inserted")

      override def onError(e: Throwable): Unit = println(request + " Failed")

      override def onComplete(): Unit = println(request + " Completed")
    })
  }

  def updateDoc(request: String): Boolean = {

    val doc: Document = Document(request)

    val observable: Observable[UpdateResult] = collection.replaceOne(
        Filters.eq("event_id", doc.get("event_id").get.asString().getValue),
        doc)

    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get

    result match {
      case Success(t) => true
      case Failure(e) => { println(e); false }
    }
  }

  def updateDocEither(request: String): Either[Throwable, Document] = {
    println("updateDocEither, request=" + request)
    val doc: Document = Document(request)

    val eventId = doc.get("event_id").get.asString().getValue

    val observable: Observable[UpdateResult] =
      collection.updateOne(Filters.eq("event_id", eventId), doc)

    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get

    result match {
      case Success(t) =>
        getDocumentById(eventId) match {
          case Right(o) => Right(o)
          case Left(x) => Left(x)
        }
      case Failure(e) => Left(e)
    }

  }

  def updateBson(request: UpdateBsonId): Either[Throwable, Document] = {
    println("updateBson, request=" + request)
    val eventId = request.event_id
    val observable: Observable[UpdateResult] =
      collection.updateOne(Filters.eq("event_id", eventId), request.criteria)
    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get

    result match {
      case Success(t) =>
        getDocumentById(eventId) match {
          case Right(o) => Right(o)
          case Left(x) => Left(x)
        }
      case Failure(e) => Left(e)
    }
  }

  def getJsonById(id: String): Either[Throwable, String] = {
    val observable = collection.find(equal("event_id", id)).first()
    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get
    result match {
      case Success(t) => Right(t.toJson())
      case Failure(e) => Left(e)
    }
  }

  def getDocumentById(id: String): Either[Throwable, Document] = {
    val observable = collection.find(equal("event_id", id)).first()
    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get
    result match {
      case Success(t) => {println("getDocumentById doc=" + t.toJson());Right(t)}
      case Failure(e) => Left(e)
    }
  }

  def get(id: String): Either[Throwable, EventResponse] = {
    val observable = collection.find(equal("event_id", id)).first()
    val result =
      Await.ready(observable.head(), Duration(3, TimeUnit.SECONDS)).value.get
    result match {
      case Success(t) => Right(JsonUtil.fromJson[EventResponse](t.toJson()))
      case Failure(e) => Left(e)
    }
//    val response: EventResponse = {

//    }
    // Subscribe with custom onNext and onError
//    observable.subscribe((result: Document) => JsonUtil.fromJson[EventResponse](result.toJson()),
//                         (e: Throwable) => println(s"There was an error: $e"))

  }
}
