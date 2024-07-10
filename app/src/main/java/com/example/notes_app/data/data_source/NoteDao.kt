package com.example.notes_app.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes_app.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert  // to insert and update
    suspend fun upsert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note order by date asc")
    fun getAllNotesOrderByDate(): Flow<List<Note>>

    @Query("SELECT * FROM note order by title asc")
    fun getAllNotesOrderByTitle(): Flow<List<Note>>

}