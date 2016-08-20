package com.twitter.hello

/**
  * Created by ChuHsuan on 2016/8/20.
  */
case class EditRequest (
                         event_id: String,
                         event_name: String,
                         event_desc: String,
                         pwd: String,
                         is_editable: Boolean,
                         opt: List[PollOption]
                       )
