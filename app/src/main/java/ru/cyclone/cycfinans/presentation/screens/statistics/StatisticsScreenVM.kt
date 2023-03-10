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
    private val _categories1 = MutableLiveData<Map<String, Int>>()
    val categories1: LiveData<Map<String, Int>>
        get() = _categories1

    fun updateAllPromotions() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let {
                val promotionListExpenses = it.filter { promotion ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = promotion.time.time
                    (date == YearMonth.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)) and
                            (!promotion.type)
                }
                val promotionListIncomes = it.filter { promotion ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = promotion.time.time
                    (date == YearMonth.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)) and
                            (promotion.type)
                }
                _categories.postValue(promotionListExpenses.associate { promotion ->
                    val sum = promotionListExpenses.filter { p -> p.category == promotion.category }.sumOf { p ->
                        p.price
                    }
                    Pair(promotion.category, sum)
                })
                _categories1.postValue(promotionListIncomes.associate { promotion ->
                    val sum = promotionListIncomes.filter { p -> p.category == promotion.category }.sumOf { p ->
                        p.price
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