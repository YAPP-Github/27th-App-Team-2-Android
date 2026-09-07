package com.neki.android.feature.photo_upload.impl.qrscan.util

import com.neki.android.feature.photo_upload.impl.BuildConfig
import java.net.URI

internal object SayCheeseUrlMatcher {

    fun isSupportedQrUrl(url: String): Boolean {
        val configuredUrl = BuildConfig.SAY_CHEESE_URL
        if (configuredUrl.isNullOrBlank()) return false

        val uri = url.toUriOrNull() ?: return false
        val configuredHost = configuredUrl.substringBefore('/')

        return uri.host.equals(configuredHost, ignoreCase = true)
    }

    fun isOriginalImageUrl(url: String): Boolean {
        val configuredImageUrl = BuildConfig.SAY_CHEESE_IMAGE_URL
        val configuredMimeType = BuildConfig.SAY_CHEESE_IMAGE_URL_MIME_TYPE
        if (configuredImageUrl.isNullOrBlank() || configuredMimeType.isNullOrBlank()) return false

        val uri = url.toUriOrNull() ?: return false
        val configuredHost = configuredImageUrl.substringBefore('/')
        val configuredPath = configuredImageUrl.substringAfter(configuredHost)

        return uri.host.equals(configuredHost, ignoreCase = true) &&
            uri.path.startsWith(configuredPath) &&
            uri.path.endsWith(configuredMimeType)
    }

    private fun String.toUriOrNull(): URI? = runCatching { URI(this) }.getOrNull()
}
