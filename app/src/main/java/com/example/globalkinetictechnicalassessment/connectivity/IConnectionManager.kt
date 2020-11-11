package com.example.globalkinetictechnicalassessment.connectivity

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError

interface IConnectionManager {
    fun <T> enqueueRequest(request: Request<T>)
    fun cancelAllPendingRequests()
    fun handleError(context: Context?, error: VolleyError, retry: () -> Unit)
}