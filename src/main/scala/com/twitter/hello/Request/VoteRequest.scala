package com.twitter.hello.Request

import com.twitter.hello.{PollOption, UserVote}

/**
  * Created by tom_th_lin on 2016/8/21.
  */
case class VoteRequest(
    event_id: String,
    user_name: String,
    user_sel: List[Int]
)
