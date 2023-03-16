package ru.cyclone.cycfinans.domain.usecases.note

import ru.cyclone.cycfinans.data.local.dao.NoteRepositoryImpl
import ru.cyclone.cycfinans.domain.model.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val noteRepository: NoteRepositoryImpl) {
    suspend operator fun invoke(note: Note) = noteRepository.deleteNote(note = note)
}