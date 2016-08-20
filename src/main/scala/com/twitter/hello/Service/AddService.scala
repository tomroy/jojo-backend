package com.twitter.hello.Service

import com.google.inject.Inject
import com.twitter.hello.JsonUtil}
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.AddRequest
import com.twitter.hello.Response.AddResponse

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class AddService @Inject()(repo: EventRepo) {

  def insert(request: AddRequest): AddResponse = {
    repo.insert(JsonUtil.toJson(request))
    AddResponse(shortUrl = "shorturl")
  }
}
