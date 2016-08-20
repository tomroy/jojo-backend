package com.twitter.hello.Repos

import com.google.inject.Inject
import com.twitter.hello.MyEvent
import com.twitter.util.{Return, Throw, Future => TwitterFuture}

import scala.util.{Failure, Success}


/**
  * Created by tom_th_lin on 2016/8/20.
  */
class EventRepo @Inject()(collection: EventCollection) {
  val insert: (MyEvent) => Either[Error, MyEvent] = { (event) =>
    (for {
      c <- collection.collection
      r <- c.insert(event)
    } yield {
      r
    }).onComplete {
    case Failure(e) => KO(e)
    case Success(lastError) =>
      println("successfully inserted document with lastError = " + lastError)
    }

  }
}
