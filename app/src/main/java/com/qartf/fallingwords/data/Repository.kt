package com.qartf.fallingwords.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class Repository(
    private val localData: LocalData
    , private val dispatcher: CoroutineDispatcher
) {

    suspend fun getWords(): List<Word> {
        return withContext(dispatcher) {
            localData.getWords()
        }
    }
}