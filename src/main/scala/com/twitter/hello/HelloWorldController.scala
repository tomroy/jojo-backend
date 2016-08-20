package com.twitter.hello

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.{AddRequest, LoginRequest}
import com.twitter.hello.Service.{AddService, GetService, LoginService}

class HelloWorldController @Inject()(addService: AddService,
                                     getService: GetService,
                                     loginService: LoginService,
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

  get("/event/get") { request: Request =>
    val id = request.params.getOrElse("id", "unknown")
    id match {
      case "unknown" => response.badRequest("unknown uuid")
      case _ =>
        getService.get(id) match {
          case Right(t) => t
          case Left(e) => response.badRequest(s"${id} not found")
        }
    }
  }

  post("/event/login") { request: LoginRequest =>
    loginService.login(request) match {
      case true => response.ok()
      case false => response.badRequest("invalid password")
    }
  }

}
