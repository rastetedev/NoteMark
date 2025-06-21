package com.raulastete.notemark.data.repository

import com.raulastete.notemark.data.local.NoteDao
import com.raulastete.notemark.data.mapper.NoteMapper
import com.raulastete.notemark.data.remote.service.ktor.NoteRemoteService
import com.raulastete.notemark.domain.DataError
import com.raulastete.notemark.domain.EmptyDataResult
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.asEmptyDataResult
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultNoteRepository(
    private val noteRemoteService: NoteRemoteService,
    private val noteDao: NoteDao,
    private val noteMapper: NoteMapper,
    private val applicationScope: CoroutineScope
) : NoteRepository {

    override suspend fun upsertNote(note: Note): EmptyDataResult<DataError> {

        val result = applicationScope.async {
            if (note.lastEditedAt.isEmpty())
                return@async noteRemoteService.createNote(noteMapper.fromDomainToDto(note))
                    .asEmptyDataResult()
            else
                return@async noteRemoteService.updateNote(noteMapper.fromDomainToDto(note))
                    .asEmptyDataResult()
        }.await()

        noteDao.upsertNote(noteMapper.fromDomainToEntity(note))

        return result
    }

    override suspend fun getNote(noteId: String): Result<Note?, DataError> {
        val result = noteDao.getById(noteId)?.let { noteMapper.fromEntityToDomain(it) }
        return Result.Success(result)
    }

    override suspend fun deleteNote(noteId: String): EmptyDataResult<DataError> {
        val result = applicationScope.async {
            return@async noteRemoteService.deleteNote(noteId).asEmptyDataResult()
        }.await()

        noteDao.deleteNote(noteId)

        return result

    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getAll().map { noteEntityList ->
            noteEntityList.map { noteMapper.fromEntityToDomain(it) }
        }
    }
}