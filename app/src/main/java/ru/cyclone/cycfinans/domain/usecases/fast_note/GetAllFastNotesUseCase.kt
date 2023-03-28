package ru.cyclone.cycfinans.domain.usecases.fast_note

import ru.cyclone.cycfinans.data.local.dao.FastNoteRepositoryImpl
import javax.inject.Inject

class GetAllFastNotesUseCase @Inject constructor(private val noteRepository: FastNoteRepositoryImpl) {
    suspend operator fun invoke() = noteRepository.getAllNotes()
}
