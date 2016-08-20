package com.twitter.hello.Response

import com.twitter.hello.{PollOption, UserVote}

/**
  * Created by tom_th_lin on 2016/8/21.
  */
case class EventResponse(
    event_name: String,
    event_desc: String,
    opt: List[PollOption],
    poll: List[UserVote]
)
