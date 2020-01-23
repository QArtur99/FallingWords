package com.qartf.fallingwords.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qartf.fallingwords.data.Repository
import com.qartf.fallingwords.data.Word
import kotlinx.coroutines.launch

class GameViewModel(private val repository: Repository) : ViewModel() {

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> = _wordList

    fun getWordList() {
        viewModelScope.launch {
            try {
                _wordList.value = repository.getWords()
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }


}