package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.AddPromotionUseCase
import javax.inject.Inject


@HiltViewModel
class AddPromotionVM @Inject constructor(
    private val addPromotionUseCase: AddPromotionUseCase
): ViewModel() {

    fun addPromotion(promotion: Promotion, onSuccess: () -> Unit) {
        viewModelScope.launch {
            addPromotionUseCase.invoke(promotion = promotion)
            onSuccess
        }
    }

}