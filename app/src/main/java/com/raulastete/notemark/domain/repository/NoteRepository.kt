package com.raulastete.notemark.domain.repository

import com.raulastete.notemark.domain.DataError
import com.raulastete.notemark.domain.EmptyDataResult
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNote(note: Note) : EmptyDataResult<DataError>

    suspend fun getNote(noteId: String) : Result<Note?, DataError>

    suspend fun deleteNote(noteId: String) : EmptyDataResult<DataError>

    fun getNotes(): Flow<List<Note>>
}