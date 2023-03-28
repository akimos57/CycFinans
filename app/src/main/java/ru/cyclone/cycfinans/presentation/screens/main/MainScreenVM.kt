package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.FastNote
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.fast_note.AddFastNoteUseCase
import ru.cyclone.cycfinans.domain.usecases.fast_note.DeleteFastNoteUseCase
import ru.cyclone.cycfinans.domain.usecases.fast_note.GetAllFastNotesUseCase
import ru.cyclone.cycfinans.domain.usecases.promotion.GetAllPromotionUseCase
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase,
    private val getAllFastNotesUseCase: GetAllFastNotesUseCase,
    private val deleteFastNoteUseCase: DeleteFastNoteUseCase,
    private val addFastNoteUseCase: AddFastNoteUseCase,
) : ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
        get() = _promotions
    private val yearMonth = YearMonth.now()

    private val _notes = MutableLiveData<List<FastNote>>()
    val notes: LiveData<List<FastNote>>
        get() = _notes

    var date: Calendar? = Calendar.getInstance()

    init {
        updateNotes()
        getHistory(yearMonth)
    }

    fun getHistory(_yearMonth: YearMonth){
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let { promotions1 ->
                _promotions.postValue(promotions1.filter {
                    val c = Calendar.getInstance()
                    c.time = it.time
                    (c.get(Calendar.MONTH) + 1 == _yearMonth.month.value) and (c.get(Calendar.YEAR) == _yearMonth.year)
                })
            }
        }
    }

    private fun updateNotes() {
        viewModelScope.launch {
            getAllFastNotesUseCase.invoke().let { notes: List<FastNote> ->
                _notes.postValue(notes.filter { note ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = note.time.time
                    (date?.get(Calendar.YEAR) == c.get(Calendar.YEAR)) and
                            (date?.get(Calendar.MONTH) == c.get(Calendar.MONTH))
                })
            }
        }
    }

    fun addNote(
        note: FastNote,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            addFastNoteUseCase.invoke(note = note)
            updateNotes()
            onSuccess()
        }
    }

    fun deleteNote(
        note: FastNote,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            notes.value?.let {
                deleteFastNoteUseCase.invoke(note = note)
                updateNotes()
                onSuccess()
            }
        }
    }
}