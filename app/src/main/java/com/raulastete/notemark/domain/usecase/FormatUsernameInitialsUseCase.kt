package com.raulastete.notemark.domain.usecase

import com.raulastete.notemark.domain.SessionStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FormatUsernameInitialsUseCase(
    private val sessionStorage: SessionStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(): String {

        val username = sessionStorage.get()?.username.orEmpty()

        require(username.length >= 2)

        return withContext(dispatcher) {

            val wordsInUsername = username.split(" ")
            val wordsNumber = wordsInUsername.size

            when (wordsNumber) {
                ONE_WORD -> {
                    val firstTwoCharacters = username.take(2)
                    firstTwoCharacters.uppercase()
                }
                TWO_WORDS -> {
                    val firstCharacterOfEachWord = buildString {
                        append(wordsInUsername.first().first())
                        append(wordsInUsername[SECOND_WORD_INDEX].first())
                    }
                    firstCharacterOfEachWord.uppercase()
                }
                else -> {
                    val firstCharacterFromTheFirstAndLastWord = buildString {
                        append(wordsInUsername.first().first())
                        append(wordsInUsername.last().first())
                    }
                    firstCharacterFromTheFirstAndLastWord.uppercase()
                }
            }
        }
    }

    companion object {
        const val ONE_WORD = 1
        const val TWO_WORDS = 2
        const val SECOND_WORD_INDEX = 1
    }
}