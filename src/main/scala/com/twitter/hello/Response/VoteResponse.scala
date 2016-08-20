package com.twitter.hello.Response

import com.twitter.hello.UserVote

/**
  * Created by tom_th_lin on 2016/8/21.
  */
case class VoteResponse(
    event_name: String,
    event_desc: String,
    poll: List[UserVote]
)
