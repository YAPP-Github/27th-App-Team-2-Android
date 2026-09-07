package com.neki.android.core.analytics.logger

import android.os.Bundle
import com.facebook.appevents.AppEventsLogger
import javax.inject.Inject

internal interface MetaAnalyticsClient {
    fun track(eventName: String, eventProperties: Map<String, Any?>)
}

internal class FacebookSdkMetaAnalyticsClient @Inject constructor(
    private val appEventsLogger: AppEventsLogger,
) : MetaAnalyticsClient {

    override fun track(eventName: String, eventProperties: Map<String, Any?>) {
        val parameters = Bundle().apply {
            eventProperties.forEach { (key, value) ->
                when (value) {
                    null -> putString(key, null)
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Float -> putFloat(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> error("Unsupported Meta event property type: ${value::class.simpleName}")
                }
            }
        }
        appEventsLogger.logEvent(eventName, parameters)
    }
}
