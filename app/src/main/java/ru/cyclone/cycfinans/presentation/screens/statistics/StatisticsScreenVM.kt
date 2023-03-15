package ru.cyclone.cycfinans.presentation.screens.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.usecases.promotion.GetAllPromotionUseCase
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase
): ViewModel() {
    private val _categories = MutableLiveData<Map<String, Int>>()
    val categories: LiveData<Map<String, Int>>
        get() = _categories

    private val _categories1 = MutableLiveData<Map<String, Int>>()
    val categories1: LiveData<Map<String, Int>>
        get() = _categories1

    fun updateAllPromotions(date: YearMonth = YearMonth.now()) {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let { promotions ->
                // Get the calendar instance
                val c = Calendar.getInstance()
                val promotionListExpenses = promotions.filter { promotion ->
                    // Set the time to the promotion time
                    c.timeInMillis = promotion.time.time
                    // Check if the date is equal to the year and month of the calendar instance
                    (date == YearMonth.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)) and
                            // Check if the promotion type is false
                            !promotion.type
                }.sortedByDescending { it.price }
                val promotionListIncomes = promotions.filter { promotion ->
                    // Set the time to the promotion time
                    c.timeInMillis = promotion.time.time
                    // Check if the date is equal to the year and month of the calendar instance
                    (date == YearMonth.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)) and
                            // Check if the promotion type is true
                            promotion.type
                }.sortedByDescending { it.price }
                _categories.postValue(promotionListExpenses.associate { promotion ->
                    // Calculate the sum of the prices for each category
                    val sum = promotionListExpenses.filter { p -> p.category == promotion.category }.sumOf { p ->
                        p.price
                    }
                    // Create a pair with the category and the sum
                    Pair(promotion.category, sum)
                })
                _categories1.postValue(promotionListIncomes.associate { promotion ->
                    // Calculate the sum of the prices for each category
                    val sum = promotionListIncomes.filter { p -> p.category == promotion.category }.sumOf { p ->
                        p.price
                    }
                    // Create a pair with the category and the sum
                    Pair(promotion.category, sum)
                })
            }
        }
    }
    init {
        updateAllPromotions()
    }
}