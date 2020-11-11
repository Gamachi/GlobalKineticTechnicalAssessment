package com.example.globalkinetictechnicalassessment.connectivity

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyRequestQueue private constructor(context: Context?) {
    companion object {
        private const val queueTag = "VolleyRequestQueue"

        @Volatile
        private var instance: VolleyRequestQueue? = null

        fun getInstance(context: Context?) =
            instance ?: synchronized(this) {
                instance ?: VolleyRequestQueue(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it prevents memory leaks
        Volley.newRequestQueue(context?.applicationContext)
    }

    fun <T> enqueueRequest(request: Request<T>) {
        request.tag = queueTag
        requestQueue.add(request)
    }

    fun cancelAllPendingRequests() {
        requestQueue.cancelAll(queueTag)
    }
}