package com.qartf.fallingwords.data

import android.content.Context
import com.qartf.fallingwords.R
import com.qartf.fallingwords.util.JsonUtil
import org.json.JSONArray

class LocalData(private val context: Context) {

    fun getWords(): List<Word> {
        val strJson = JsonUtil.readJSONFromAsset(context, R.raw.words_v2)
        val jsonArray = JSONArray(strJson)
        return (0 until jsonArray.length()).map { index ->
            val jsonObject = jsonArray.getJSONObject(index)
            val textEng = jsonObject.getString("text_eng")
            val textSpa = jsonObject.getString("text_spa")
            Word(textEng, textSpa)
        }
    }
}