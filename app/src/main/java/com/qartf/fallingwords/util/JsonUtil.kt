package com.qartf.fallingwords.util

import android.content.Context
import java.io.InputStream

object JsonUtil {

    fun readJSONFromAsset(context: Context, fileId: Int): String? {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(fileId)
            inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
    }
}