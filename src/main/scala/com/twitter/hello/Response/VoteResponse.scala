package com.twitter.hello.Response

import com.twitter.hello.UserVote

/**
  * Created by ChuHsuan on 2016/8/20.
  */
case class VoteResponse (
                          is_voted: Boolean,
                          event_name: String,
                          event_desc: String,
                          poll: List[UserVote]
                        )
