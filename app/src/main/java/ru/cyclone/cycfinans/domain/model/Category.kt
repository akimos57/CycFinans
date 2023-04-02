package ru.cyclone.cycfinans.domain.model

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycnote.R
import java.util.*

data class Category(
    @Json(name = "category_table")
    val name: String,
    val type: Boolean,
    val language: String,
    var limit: Int = 0
)

object Categories {
    private val expensesCategoriesEN = listOf(
        "Food",
        "Clothes",
        "Taxes",
        "Entertainment",
        "Transport",
    )
    private val expensesCategoriesRU = listOf(
        "Еда",
        "Одежда",
        "Налоги",
        "Развлечения",
        "Транспорт",
    )
    private val expensesCategoriesZH = listOf(
        "食物",
        "布",
        "稅收",
        "娛樂",
        "運輸",
    )
    private val expensesCategoriesKO = listOf(
        "음식",
        "옷감",
        "구실",
        "오락",
        "수송",
    )

    private val incomeCategoriesEN = listOf(
        "Salary",
        "Business",
    )
    private val incomeCategoriesRU = listOf(
        "Зарплата",
        "Бизнес",
    )
    private val incomeCategoriesZH = listOf(
        "薪水",
        "商業",
    )
    private val incomeCategoriesKO = listOf(
        "샐러리",
        "사업",
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
                    "zh" -> incomeCategoriesZH + categoryList
                    "ko" -> incomeCategoriesKO + categoryList
                    else -> incomeCategoriesEN + categoryList
                }
            }
            false -> {
                when (locale.language) {
                    "ru" -> expensesCategoriesRU + categoryList
                    "zh" -> expensesCategoriesZH + categoryList
                    "ko" -> expensesCategoriesKO + categoryList
                    else -> expensesCategoriesEN + categoryList
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

