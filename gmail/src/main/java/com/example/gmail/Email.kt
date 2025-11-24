package com.example.gmail

data class Email(
    val sender: String,
    val subject: String,
    val summary: String,
    var isStarred: Boolean = false // Dùng để lưu trạng thái dấu sao
)