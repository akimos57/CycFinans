package ru.cyclone.cycfinans.presentation.screens.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.usecases.GetAllPromotionUseCase
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase
): ViewModel() {
    var date: YearMonth = YearMonth.now()
    private val _categories = MutableLiveData<Map<String, Int>>()
    val categories: LiveData<Map<String, Int>>
        get() = _categories

    private fun updateAllPromotions() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let {
                val cm = it.filter { promotion ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = promotion.time.time
                    println(date)
                    date == YearMonth.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)
                }
                _categories.postValue(cm.associate { promotion ->
                    val sum = cm.filter { p -> p.category == promotion.category }.sumOf { p ->
                        if (!p.type) p.price else 0
                    }
                    Pair(promotion.category, sum)
                })
            }
        }
    }
    init {
        updateAllPromotions()
    }
}