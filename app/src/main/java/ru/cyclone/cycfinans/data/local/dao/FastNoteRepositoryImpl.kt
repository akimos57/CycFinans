package ru.cyclone.cycfinans.data.local.dao

import androidx.room.*
import ru.cyclone.cycfinans.domain.model.FastNote

@Dao
interface FastNoteRepositoryImpl {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: FastNote)

    @Query("SELECT * FROM fast_notes")
    suspend fun getAllNotes(): List<FastNote>

    @Query("SELECT * FROM fast_notes WHERE id=:noteId")
    suspend fun getNoteById(noteId: Long): FastNote

    @Delete
    suspend fun deleteNote(note: FastNote)
}