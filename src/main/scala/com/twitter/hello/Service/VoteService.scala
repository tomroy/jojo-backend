package com.twitter.hello.Service

import com.google.inject.Inject
import com.twitter.hello.{JsonUtil, UserVote}
import com.twitter.hello.Repos.{AllFieldBO, EventRepo, UpdateBsonId}
import com.twitter.hello.Request.VoteRequest
import com.twitter.hello.Response.VoteResponse
import org.mongodb.scala.Document
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Updates._

/**
  * Created by tom_th_lin on 2016/8/21.
  */
class VoteService @Inject()(repo: EventRepo) {
  def insert(request: VoteRequest): Either[Throwable, VoteResponse] = {

    val user_vote: Document = Document("user_name" -> request.user_name,
                                       "user_sel" -> request.user_sel)

    val doc: Bson = combine(push("poll", user_vote))

//    repo.updateBson(UpdateBsonId(request.event_id, doc)) match {
//      case Right(t) => Right(JsonUtil.fromJson[VoteResponse](t.toJson()))
//      case Left(e) => Left(e)
//    }

    repo.updateBson(UpdateBsonId(request.event_id, doc)) match {
      case Right(t) => {
        val a = JsonUtil.fromJson[AllFieldBO](t.toJson())
        val filled = AllFieldBO(event_name = a.event_name,
                                event_desc = a.event_desc,
                                pwd = a.pwd,
                                is_editable = a.is_editable,
                                opt = a.opt ::: request.opt,
                                event_id = a.event_id,
                                poll = a.poll)
        repo.updateDoc(JsonUtil.toJson(filled))
        repo.getJsonById(a.event_id) match {
          case Right(o) => Right(JsonUtil.fromJson[VoteResponse](o))
          case Left(x) => Left(x)
        }
      }
      case Left(e) => Left(e)
    }
  }

}
