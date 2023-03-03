package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.DeletePromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.GetAllPromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.GetPromotionById
import javax.inject.Inject

@HiltViewModel
class MainDetailsVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase,
): ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
    get() = _promotions

    init {
        getAllPromotions()
    }

    private fun getAllPromotions() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let {
                _promotions.postValue(it)
            }
        }
    }



}