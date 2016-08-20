package com.twitter.hello.Service

import com.google.inject.Inject
import com.twitter.hello.JsonUtil
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.EditRequest

/**
  * Created by tom_th_lin on 2016/8/21.
  */
class EditService @Inject()(repo: EventRepo) {
  def insert(request: EditRequest): Boolean = {
    repo.updateDoc(JsonUtil.toJson(request))
  }
}
