package com.raulastete.notemark.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


class FormatNoteDateInCardUseCase(private val dispatcher: CoroutineDispatcher = Dispatchers.Default) {

    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(isoString: String): String {
        return withContext(dispatcher) {
            val instant = Instant.parse(isoString)
            val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            val day = dateTime.day
            val month = dateTime.month.name.take(3)
            val year = dateTime.year

            if (year == now.year) {
                "$day $month"
            } else {
                "$day $month $year"
            }.uppercase()
        }
    }
}