package com.raulastete.notemark.data.remote.dto.note

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val id: String,
    val title: String,
    val content: String,
    val updatedAt: String
)
