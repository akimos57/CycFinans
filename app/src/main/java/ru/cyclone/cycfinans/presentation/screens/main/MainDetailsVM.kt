package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.AddPromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.DeletePromotionUseCase
import ru.cyclone.cycfinans.domain.usecases.GetAllPromotionUseCase
import javax.inject.Inject

@HiltViewModel
class MainDetailsVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase,
    private val deletePromotionUseCase: DeletePromotionUseCase,
    private val addPromotionUseCase: AddPromotionUseCase
): ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
    get() = _promotions

    init {
        updateAllPromotions()
    }

    fun addPromotion(promotion: Promotion, onSuccess: () -> Unit) {
        viewModelScope.launch {
            addPromotionUseCase.invoke(promotion = promotion)
            onSuccess()
        }
    }

    private fun updateAllPromotions() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let {
                _promotions.postValue(it)
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
                onSuccess
            }
        }
    }



}