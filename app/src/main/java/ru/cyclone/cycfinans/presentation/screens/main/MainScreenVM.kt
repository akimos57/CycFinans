package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.GetAllPromotionUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase
) : ViewModel() {
    private val _promotions = MutableLiveData<List<List<Promotion>>>()
    val promotions: LiveData<List<List<Promotion>>>
        get() = _promotions

    init {
        getHistory()
    }

    private fun getHistory(){
//        viewModelScope.launch {
//            getAllPromotionUseCase.invoke().let {
//                it.map { it.time }
//                _promotions.postValue(it)
//            }
//        }
    }
}