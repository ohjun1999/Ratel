package com.link.ratel.dataClass

data class NoticeDataClass(
    var uid :String? = null,
    //내용
    var content :String? = null,
    //유저 이름
    var creator :String? = null,
    //게시 시간
    var modifiedDate :String? = null,
    //작성 시간
    var pubDate :String? = null,
    //제목
    var title :String? = null



)