package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beust.klaxon.Klaxon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.presentation.components.Category
import javax.inject.Inject


@HiltViewModel
class SetCategoryScreenVM @Inject constructor() : ViewModel() {
    var categories = MutableLiveData<Map<String, String>?>()
    private fun updateLimits() {
        viewModelScope.launch {
            categories.postValue(
                PreferencesController().fileNameList.mapNotNull { Klaxon().parse<Category>(it) }.associate {
                    Pair(it.name, it.limit.toString())
                }
            )
        }
    }

    init {
        updateLimits()
    }
}