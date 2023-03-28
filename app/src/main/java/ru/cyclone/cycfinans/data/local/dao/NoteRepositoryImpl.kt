package ru.cyclone.cycfinans.data.local.dao

import androidx.room.*
import ru.cyclone.cycfinans.domain.model.Note

@Dao
interface NoteRepositoryImpl {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id=:noteId")
    suspend fun getNoteById(noteId: Long): Note

    @Delete
    suspend fun deleteNote(note: Note)
}