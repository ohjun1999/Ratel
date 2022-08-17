package com.link.ratel.dataClass


data class InquiryDataClass(
    var uid: String? = null,
    //제목
    var title: String? = null,
    //내용
    var content: String? = null,
    //유저 이름
    var creator: String? = null,
    //작성 시간
    var pubDate: String? = null,
    //답변 여부
    var check: String? = null,
    //게시 시간
    var modifiedDate: String? = null,
    //문의
    var question: String? = null


)