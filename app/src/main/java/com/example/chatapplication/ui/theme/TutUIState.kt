package com.example.chatapplication.ui.theme

data class TutUIState(
    val name: String = "",
    val pwd: String = "",
    val username: String = "",
    val search: String = ""
)
object SharedData{
    var data:String? = ""
}

object Datasource {
    var list = mutableListOf<String>()
}