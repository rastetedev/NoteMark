package com.raulastete.notemark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class NoteMarkDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}
