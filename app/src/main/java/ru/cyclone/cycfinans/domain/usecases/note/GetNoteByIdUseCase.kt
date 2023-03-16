package ru.cyclone.cycfinans.domain.usecases.note

import ru.cyclone.cycfinans.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Long) = noteRepository.getNoteById(id = id)
}