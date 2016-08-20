package com.twitter.hello.Repos

import org.bson.conversions.Bson

/**
  * Created by tom_th_lin on 2016/8/21.
  */
case class UpdateBsonId(event_id: String, criteria: Bson)
