package com.example.globalkinetictechnicalassessment.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.VolleyError
import com.example.globalkinetictechnicalassessment.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VolleyConnectionManager @Inject constructor(@ApplicationContext context: Context?) :
    IConnectionManager {
    companion object {
        private const val debugTag = "ConnectionManager"

        @Volatile
        private var instance: VolleyConnectionManager? = null

        fun getInstance(context: Context?) =
            instance ?: synchronized(this) {
                instance ?: VolleyConnectionManager(context).also {
                    instance = it
                }
            }
    }

    private val connectivityManager by lazy {
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private val requestQueue by lazy {
        VolleyRequestQueue.getInstance(context)
    }

    private var dialog: AlertDialog? = null

    private fun isOnline(): Boolean {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    override fun <T> enqueueRequest(request: Request<T>) {
        requestQueue.enqueueRequest(request)
    }

    override fun cancelAllPendingRequests() {
        requestQueue.cancelAllPendingRequests()
    }

    override fun handleError(context: Context?, error: VolleyError, retry: () -> Unit) {
        context ?: return

        val errorMessage =
            if (isOnline()) {
                error.localizedMessage
                    ?: context.resources.getString(R.string.error_unknown_message)
            } else {
                context.resources.getString(R.string.error_internet_unavailable)
            }
        Log.d(
            debugTag,
            errorMessage
        )
        dialog?.dismiss()
        dialog = AlertDialog.Builder(context)
            .setTitle(R.string.alert_dialog_title_error)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.alert_dialog_action_retry) { _, _ ->
                retry()
            }
            .setCancelable(false)
            .show()
    }
}