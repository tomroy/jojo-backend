package com.twitter.hello.Service

import com.google.inject.Inject
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.LoginRequest

/**
  * Created by ChuHsuan on 2016/8/20.
  */
class LoginService @Inject()(repo: EventRepo) {
  def login(request: LoginRequest): Boolean = {
    val request_pwd = request.pwd
    repo.getDocumentById(request.event_id) match {
      case Right(t) => comparePwd(request_pwd, t.get("pwd").get.asString().getValue)
      case Left(e) => false
    }
  }

  private[this] val comparePwd: (String, String) => Boolean = {(requestPwd, DBPwd) =>
    requestPwd == DBPwd
    }
}
