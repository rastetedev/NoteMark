package com.raulastete.notemark.domain.repository

import com.raulastete.notemark.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNote(
        note: Note
    )

    suspend fun getNote(noteId: String) : Note?

    suspend fun deleteNote(
        noteId: String
    )

    fun getNotes(): Flow<List<Note>>
}