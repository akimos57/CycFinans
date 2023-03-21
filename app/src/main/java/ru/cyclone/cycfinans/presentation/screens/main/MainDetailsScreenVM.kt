package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.note.DeleteNoteUseCase
import ru.cyclone.cycfinans.domain.usecases.note.GetAllNotesUseCase
import ru.cyclone.cycfinans.domain.usecases.promotion.AddPromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.promotion.DeletePromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.promotion.GetAllPromotionUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainDetailsScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase,
    private val deletePromotionUseCase: DeletePromotionUseCase,
    private val addPromotionUseCase: AddPromotionUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
): ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
    get() = _promotions

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>>
        get() = _notes

    var date: Calendar? = Calendar.getInstance()

    init {
        updateAllPromotions()
    }

    fun addPromotion(promotion: Promotion, onSuccess: () -> Unit) {
        viewModelScope.launch {
            addPromotionUseCase.invoke(promotion = promotion)
            updateAllPromotions()
            onSuccess()
        }
    }

    private fun updateAllPromotions() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let {
                val cm = it.filter { promotion ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = promotion.time.time
                    (date?.get(Calendar.YEAR) == c.get(Calendar.YEAR)) and
                            (date?.get(Calendar.MONTH) == c.get(Calendar.MONTH)) and
                            (date?.get(Calendar.DATE) == c.get(Calendar.DATE))
                }
                _promotions.postValue(cm)
            }
        }
    }

    fun deletePromotion(
        onSuccess: () -> Unit = {},
        promotion: Promotion
    ) {
        viewModelScope.launch {
            promotions.value?.let {
                deletePromotionUseCase.invoke(promotion = promotion)
                updateAllPromotions()
                onSuccess()
            }
        }
    }

    fun updateNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.invoke().let { notes: List<Note> ->
                _notes.postValue(notes.filter { note ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = note.time.time
                    (date?.get(Calendar.YEAR) == c.get(Calendar.YEAR)) and
                            (date?.get(Calendar.MONTH) == c.get(Calendar.MONTH)) and
                            (date?.get(Calendar.DATE) == c.get(Calendar.DATE))
                })
            }
        }
    }

    fun deleteNote(
        onSuccess: () -> Unit = {},
        note: Note
    ) {
        viewModelScope.launch {
            notes.value?.let {
                deleteNoteUseCase.invoke(note = note)
                updateNotes()
                onSuccess()
            }
        }
    }

    init {
        updateNotes()
    }
}