package com.example.koin.utils

import android.content.Context
import io.reactivex.Observable
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by Nhat Vo on 14/06/2021.
 */
object Utils {

    /**
     * Get the json data from json file.
     *
     * @param context  the context to acces the resources.
     * @param fileName the name of the json file
     * @return json as string
     */
    fun getJsonFromAsset(
        context: Context,
        fileName: String?
    ): Observable<String> {
        return Observable.create { emitter ->
            try {
                val stream: InputStream = context.assets.open(fileName!!)
                val size: Int = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                emitter.onNext(String(buffer, Charset.forName("UTF-8")))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }
            emitter.onComplete()
        }
    }
}