package ru.cyclone.cycfinans.presentation.screens.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.domain.usecases.note.AddNoteUseCase
import ru.cyclone.cycfinans.domain.usecases.note.DeleteNoteUseCase
import ru.cyclone.cycfinans.domain.usecases.note.GetNoteByIdUseCase
import javax.inject.Inject

@HiltViewModel
class AddNoteVM @Inject constructor(
    private val getNoteById: GetNoteByIdUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            getNoteById.invoke(id = id).let {
                _note.postValue(it)
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            addNoteUseCase.invoke(note = note)
            onSuccess()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.invoke(note)
        }
    }
}