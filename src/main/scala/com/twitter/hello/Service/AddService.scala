package com.twitter.hello.Service

import java.util.UUID

import com.google.inject.Inject
import com.twitter.hello.{JsonUtil, PollOption}
import com.twitter.hello.Repos.EventRepo
import com.twitter.hello.Request.AddRequest
import com.twitter.hello.Response.AddResponse

/**
  * Created by tom_th_lin on 2016/8/20.
  */
class AddService @Inject()(repo: EventRepo) {

  private[this] val addUUID: (AddRequest, String) => (DBAddRequest) = { (request, uuid) =>
    DBAddRequest(event_id = uuid,
                 event_name = request.event_name,
                 event_desc = request.event_desc,
                 pwd = request.pwd,
                 is_editable = request.is_editable,
                 opt = request.opt)
  }

  def insert(request: AddRequest): AddResponse = {

    val uuid = UUID.randomUUID().toString
    repo.insert(JsonUtil.toJson(addUUID(request, uuid)))
    AddResponse(url = s"http://jojo.japanwest.cloudapp.azure.com/poll.html?id=${uuid}")
  }

  case class DBAddRequest(event_id: String,
                          event_name: String,
                          event_desc: String,
                          pwd: String,
                          is_editable: Boolean,
                          opt: List[PollOption])

}
