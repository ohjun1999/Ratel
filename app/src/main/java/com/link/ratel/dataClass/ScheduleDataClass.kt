package com.link.ratel.dataClass




data class ScheduleDataClass(

    //내용
    val content: String? = "content",
    //유저 이름
    val creator: String? = "creator",
    //날짜
    val date: String? = "date",
    //게시 시간
    val modifiedDate: String? = "modifiedDate",
    //작성 시간
    val pubDate: String? = "pubDate",
    //제목
    val title: String? = "title"
)