package ru.cyclone.cycfinans.domain.usecases.fast_note

import ru.cyclone.cycfinans.data.local.dao.FastNoteRepositoryImpl
import ru.cyclone.cycfinans.domain.model.FastNote
import javax.inject.Inject

class AddFastNoteUseCase @Inject constructor(private val noteRepository: FastNoteRepositoryImpl) {
    suspend operator fun invoke(note: FastNote) = noteRepository.insertNote(note = note)
}