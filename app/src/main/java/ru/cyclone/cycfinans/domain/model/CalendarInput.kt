package ru.cyclone.cycfinans.domain.model

data class CalendarInput(
    val day: Int,
    val toDos: List<String> = emptyList()
)
