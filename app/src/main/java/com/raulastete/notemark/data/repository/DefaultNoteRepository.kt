package com.raulastete.notemark.data.repository

import com.raulastete.notemark.data.local.NoteDao
import com.raulastete.notemark.data.mapper.NoteMapper
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultNoteRepository(
    private val httpClient: HttpClient,
    private val noteDao: NoteDao,
    private val noteMapper: NoteMapper,
    private val applicationScope: CoroutineScope
) : NoteRepository {

    override suspend fun upsertNote(note: Note) {
        noteDao.upsertNote(noteMapper.fromDomainToEntity(note))
    }

    override suspend fun getNote(noteId: String): Note? {
        return noteDao.getById(noteId)?.let { noteMapper.fromEntityToDomain(it) }
    }

    override suspend fun deleteNote(noteId: String) {
        noteDao.deleteNote(noteId)
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getAll().map { noteEntityList ->
            noteEntityList.map { noteMapper.fromEntityToDomain(it) }
        }
    }
}