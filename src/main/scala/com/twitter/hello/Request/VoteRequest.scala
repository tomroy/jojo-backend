package com.twitter.hello.Request

import com.twitter.hello.UserVote

/**
  * Created by ChuHsuan on 2016/8/20.
  */
case class VoteRequest (
                         event_id: String,
                         user: UserVote
                       )
