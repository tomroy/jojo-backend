package com.twitter.hello

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.AddRequest
import com.twitter.hello.Service.AddService

class HelloWorldController @Inject()(addService: AddService,
                                     eventRepo: EventRepo)
    extends Controller {

  get("/hi") { request: Request =>
    info("hi")
    "Hello " + request.params.getOrElse("name", "unnamed")
  }

  post("/hi") { hiRequest: HiRequest =>
    "Hello " + hiRequest.name + " with id " + hiRequest.id
  }

  post("/event/add") { request: AddRequest =>
    addService.insert(request)
  }

}
