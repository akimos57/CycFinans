package ru.cyclone.cycfinans.domain.usecases.note

import ru.cyclone.cycfinans.data.local.dao.NoteRepositoryImpl
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepositoryImpl) {
    suspend operator fun invoke() = noteRepository.getAllNotes()
}
