package com.twitter.hello.Repos

import com.twitter.hello.{PollOption, UserVote}

/**
  * Created by tom_th_lin on 2016/8/21.
  */
case class AllFieldBO(event_name: String,
                      event_desc: String,
                      pwd: String,
                      is_editable: Boolean,
                      opt: List[PollOption],
                      event_id: String,
                      poll: List[UserVote])
