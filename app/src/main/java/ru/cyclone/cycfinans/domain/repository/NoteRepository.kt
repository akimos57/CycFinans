package ru.cyclone.cycfinans.domain.repository

import ru.cyclone.cycfinans.data.local.dao.NoteRepositoryImpl
import ru.cyclone.cycfinans.domain.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteRepositoryImpl: NoteRepositoryImpl) {
    suspend fun insertNote(note: Note) = noteRepositoryImpl.insertNote(note = note)

    suspend fun getAllNotes(): List<Note> = noteRepositoryImpl.getAllNotes()

    suspend fun getNoteById(id: Long) = noteRepositoryImpl.getNoteById(noteId = id)

    suspend fun deleteNote(note: Note) = noteRepositoryImpl.deleteNote(note = note)
}