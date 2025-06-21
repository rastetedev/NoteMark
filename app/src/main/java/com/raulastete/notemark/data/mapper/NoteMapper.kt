package com.raulastete.notemark.data.mapper

import com.raulastete.notemark.data.local.NoteEntity
import com.raulastete.notemark.data.remote.dto.note.NoteRequest
import com.raulastete.notemark.domain.entity.Note

class NoteMapper {

    fun fromDomainToEntity(note: Note): NoteEntity {
        return NoteEntity(
            note.id,
            note.title,
            note.content,
            note.createdAt,
            note.lastEditedAt
        )
    }

    fun fromEntityToDomain(noteEntity: NoteEntity): Note {
        return Note(
            noteEntity.id,
            noteEntity.title,
            noteEntity.content,
            noteEntity.createdAt,
            noteEntity.updatedAt
        )
    }

    fun fromDomainToDto(note: Note): NoteRequest {
        return NoteRequest(
            note.id,
            note.title,
            note.content,
            note.createdAt,
            note.lastEditedAt
        )
    }
}