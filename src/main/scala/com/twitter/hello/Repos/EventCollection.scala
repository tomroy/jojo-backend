package com.twitter.hello.Repos

import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.{Future => ScalaFuture}

/**
  * Created by tom_th_lin on 2016/8/20.
  */
case class EventCollection(collection: ScalaFuture[BSONCollection])
