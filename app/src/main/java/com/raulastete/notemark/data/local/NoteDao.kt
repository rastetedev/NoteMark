package com.raulastete.notemark.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE id = :noteId")
    fun getById(noteId: String): NoteEntity?

    @Query("DELETE FROM NoteEntity WHERE id = :noteId")
    suspend fun deleteNote(noteId: String)

    @Query("SELECT * FROM NoteEntity ORDER BY updatedAt DESC")
    fun getAll(): Flow<List<NoteEntity>>
}