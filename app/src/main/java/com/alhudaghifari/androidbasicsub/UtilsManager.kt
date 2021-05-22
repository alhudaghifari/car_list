package com.alhudaghifari.androidbasicsub

import android.app.Activity
import java.io.IOException
import java.nio.charset.Charset

/**
 * method to load JSON from Assets folder
 */
fun loadJSONFromAsset(activity: Activity, fileName: String): String? {
    val json: String?
    try {
        val `is` = activity.getAssets().open(fileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer, Charset.defaultCharset())
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}