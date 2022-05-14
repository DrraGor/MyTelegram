package ru.dragor.mytelegram.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",
    var state: String = ""

)