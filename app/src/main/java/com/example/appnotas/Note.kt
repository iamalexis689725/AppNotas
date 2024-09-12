package com.example.appnotas

data class Note(
    var title: String,
    var description: String,
    var backgroundColor: Int = android.graphics.Color.TRANSPARENT
)