package com.raulastete.notemark.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FormatUsernameInitials(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(username: String): String {
        require(username.length >= 2)
        return withContext(dispatcher) {

            val wordsInUsername = username.split(" ")
            val wordsNumber = wordsInUsername.size

            val firstTwoCharacters = username.take(2)
            val firstCharacterOfEachWord = buildString {
                append(wordsInUsername.first().first())
                append(wordsInUsername[SECOND_WORD_INDEX].first())
            }
            val firstCharacterFromTheFirstAndLastWord = buildString {
                append(wordsInUsername.first().first())
                append(wordsInUsername.last().first())
            }

            when (wordsNumber) {
                ONE_WORD -> firstTwoCharacters
                TWO_WORDS -> firstCharacterOfEachWord
                else -> firstCharacterFromTheFirstAndLastWord
            }
        }
    }

    companion object {
        const val ONE_WORD = 1
        const val TWO_WORDS = 2
        const val SECOND_WORD_INDEX = 1
    }
}