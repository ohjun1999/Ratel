package com.link.ratel.dataClass

data class UserDataClass(
    val id: String? = "id",
    val year: String? = "year",
    val name: String? = "name",
    val birthdate: String? = "birthdate",
    val phoneNum: String? = "phoneNum",
    val email: String? = "email",
    val company: String? = "company",
    val department: String? = "department",
    val comPosition: String? = "comPosition",
    val comTel: String? = "comTel",
    val comAdr: String? = "comAdr",
    val faxNum: String? = "faxNum",
    val image: String? = "files",
    val filenames: ArrayList<Any>? = ArrayList(),
    val files: ArrayList<Any>? = ArrayList(),
    val bookmark: ArrayList<Any>? = ArrayList(),
    val uid:String? = "uid"

)