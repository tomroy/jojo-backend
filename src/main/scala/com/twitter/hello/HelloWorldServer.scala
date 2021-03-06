package com.twitter.hello

import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{
  CommonFilters,
  LoggingMDCFilter,
  TraceIdMDCFilter
}
import com.twitter.finatra.http.routing.HttpRouter

import scala.sys.ShutdownHookThread

object HelloWorldServerMain extends HelloWorldServer

class HelloWorldServer extends HttpServer {

  override def configureHttp(router: HttpRouter) {
    router
      .filter(new HttpFilter(Cors.UnsafePermissivePolicy),
              beforeRouting = true)
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HelloWorldController]
  }

  ShutdownHookThread {
    info("============================================")
    info("-------- AccountMainApp stopped.... --------")
    info("============================================")
  }

  info("============================================")
  info(">>>>>>>> AccountMainApp started.... <<<<<<<<")
  info("============================================")
}
