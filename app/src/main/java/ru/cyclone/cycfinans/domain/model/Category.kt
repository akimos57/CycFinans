package ru.cyclone.cycfinans.domain.model

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.util.*

data class Category(
    @Json(name = "category_table")
    val name: String,
    val type: Boolean,
    val language: String,
    var limit: Int = 0
)

object Categories {
    private val categories = listOf(
        "Food",
        "Clothes",
        "Taxes",
        "Entertainment",
        "Others"
    )
    private val categoriesRU = listOf(
        "Еда",
        "Одежда",
        "Налоги",
        "Развлечения",
        "Транспорт",
        "Другое"
    )

    private val incomeCategories = listOf(
        "!Food",
        "!Clothes",
        "!Taxes",
        "!Entertainment",
        "!Others"
    )
    private val incomeCategoriesRU = listOf(
        "Зарплата",
        "Бизнес",
        "Недвижимость",
        "Акции",
        "Другое"
    )

    fun getAll(
        locale: Locale,
        type: Boolean,
    ) : List<String> {
        val extraCategories = PreferencesController("extra_categories").fileNameList.map { it }

        val categoryList = getCategoryByLocationAndType(
            extraCategories.mapNotNull { Klaxon().parse<Category>(it) },
            language = locale.language,
            type = type
        )

        return when(type) {
            true -> {
                when (locale.language) {
                    "ru" -> incomeCategoriesRU + categoryList
                    else -> incomeCategories + categoryList
                }
            }
            false -> {
                when (locale.language) {
                    "ru" -> categoriesRU + categoryList
                    else -> categories + categoryList
                }
            }
        }
    }

    // For future usage in translation
    private fun getCategoryByLocationAndType(
        categoryList: List<Category>,
        language: String,
        type: Boolean
    ): List<String>  {
        return categoryList.filter { it.type == type }.map { category ->
            val categoryName = category.name
            if (category.language != language) {
                // ToDo translate here
                categoryName
            } else categoryName
        }
    }

    fun currentLocation() : Locale {
        return Locale.getDefault()
    }
}

