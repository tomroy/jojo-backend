package com.twitter.hello.Service

import com.twitter.finagle.http.Request
import com.twitter.hello.Request.AddRequest
import com.twitter.hello.Response.AddResponse

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class AddService {

  def insert(request: AddRequest) : AddResponse = {
    println(request.event_name + " is added")

    AddResponse(shortUrl = "shorturl")
  }
}
