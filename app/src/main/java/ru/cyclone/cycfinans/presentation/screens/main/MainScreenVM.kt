package ru.cyclone.cycfinans.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.usecases.promotion.GetAllPromotionUseCase
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase
) : ViewModel() {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>>
        get() = _promotions
    private val yearMonth = YearMonth.now()

    init {
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
}