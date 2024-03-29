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
import ru.cyclone.cycfinans.domain.usecases.note.GetAllNotesUseCase
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarScreenVM @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>>
        get() = _notes

    val yearMonth = MutableLiveData<YearMonth>()
    val day = MutableLiveData<Int>()

    fun updateNotes() {
       viewModelScope.launch {
           _notes.postValue(getAllNotesUseCase.invoke().sortedByDescending { it.time })
       }
    }
    init {
        updateNotes()
        yearMonth.value = YearMonth.now()
        day.value = LocalDate.now().dayOfMonth
    }
    fun deleteNote(
        note: Note,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            notes.value?.let {
                deleteNoteUseCase.invoke(note = note)
                updateNotes()
                onSuccess()
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            addNoteUseCase.invoke(note = note)
            updateNotes()
            onSuccess()
        }
    }
}