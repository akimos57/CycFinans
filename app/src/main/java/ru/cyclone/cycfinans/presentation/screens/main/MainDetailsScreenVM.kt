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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainDetailsScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase,
    private val deletePromotionUseCase: DeletePromotionUseCase,
    private val addPromotionUseCase: AddPromotionUseCase
): ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
    get() = _promotions
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
}