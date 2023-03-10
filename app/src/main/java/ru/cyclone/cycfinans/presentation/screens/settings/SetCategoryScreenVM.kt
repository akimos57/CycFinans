package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.usecases.GetAllPromotionUseCase
import ru.cyclone.cycfinans.presentation.components.Categories
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SetCategoryScreenVM @Inject constructor(
    private val getAllPromotionUseCase: GetAllPromotionUseCase
) : ViewModel() {
    lateinit var dataStore: DataStore<Preferences>
    var categories = MutableLiveData<Map<String, String>?>()
    private fun updateLimits() {
        viewModelScope.launch {
            getAllPromotionUseCase.invoke().let { promotions ->
                val promotionList = Categories.getAll(Locale.getDefault()) +
                        (promotions.filter{ p -> (!p.type) and (p.category.isNotEmpty()) }.map { promotion ->
                    promotion.category
                }).distinct()
                val c = promotionList.associateWith { category ->
                    dataStore.data.map { preferences ->
                        preferences[stringPreferencesKey(category)] ?: ""
                    }
                }
                categories.postValue(c.map {
                    Pair(it.key, it.value.first())
                }.toMap())
            }
        }
    }


    init {
        updateLimits()
    }
}