package com.raulastete.notemark.domain.entity

data class Note(
    val id: Int,
    val date: String,
    val title: String,
    val content: String
)