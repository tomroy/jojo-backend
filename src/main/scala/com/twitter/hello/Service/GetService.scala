package com.twitter.hello.Service

import com.google.inject.Inject
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Response.EventResponse

/**
  * Created by tom_th_lin on 2016/8/21.
  */
class GetService @Inject()(repo: EventRepo) {
  def get(id: String): Either[Throwable ,EventResponse] = {
    repo.get(id)
  }
}
