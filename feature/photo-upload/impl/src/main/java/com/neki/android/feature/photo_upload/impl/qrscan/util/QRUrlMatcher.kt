package com.neki.android.feature.photo_upload.impl.qrscan.util

import com.neki.android.feature.photo_upload.impl.BuildConfig

internal enum class QRImageProvider {
    PHOTOISM,
    PIXPIXLINK,
    LIFE_FOUR_CUT,
    PHOTO_SIGNATURE,
    HARU_FILM,
    PHOTO_GRAY,
    MONO_MANSION,
}

internal object QRUrlMatcher {
    fun isSupportedBrand(url: String): Boolean {
        return listOf(
            BuildConfig.PHOTOISM_URL,
            BuildConfig.PIXPIXLINK_URL,
            BuildConfig.LIFE_FOUR_CUT_URL,
            BuildConfig.PHOTO_SIGNATURE_URL_1,
            BuildConfig.PHOTO_SIGNATURE_URL_2,
            BuildConfig.HARU_FILM_URL,
            BuildConfig.PHOTO_GRAY_URL,
            BuildConfig.MONO_MANSION_URL,
        ).any { url.containsConfiguredUrl(it) }
    }

    fun isFirstDownloadRequired(url: String): Boolean {
        return listOf(
            BuildConfig.MONO_MANSION_URL,
            BuildConfig.PHOTO_GRAY_URL,
            BuildConfig.PHOTO_SIGNATURE_URL_1,
            BuildConfig.PHOTO_SIGNATURE_URL_2,
        ).any { url.containsConfiguredUrl(it) }
    }

    fun detectImageProvider(url: String): QRImageProvider? {
        return when {
            url.matchesImage(
                host = BuildConfig.PHOTOISM_IMAGE_URL,
                mimeType = BuildConfig.PHOTOISM_IMAGE_URL_MIME_TYPE,
            ) || url.matchesImage(
                host = BuildConfig.PHOTOISM_IMAGE_URL_2,
                mimeType = BuildConfig.PHOTOISM_IMAGE_URL_MIME_TYPE,
            ) -> QRImageProvider.PHOTOISM

            url.matchesImage(
                host = BuildConfig.PIXPIXLINK_IMAGE_URL,
                mimeType = BuildConfig.PIXPIXLINK_IMAGE_URL_MIME_TYPE,
            ) -> QRImageProvider.PIXPIXLINK

            url.matchesImage(
                host = BuildConfig.LIFE_FOUR_CUT_IMAGE_URL,
                mimeType = BuildConfig.LIFE_FOUR_CUT_IMAGE_URL_MIME_TYPE,
            ) -> QRImageProvider.LIFE_FOUR_CUT

            url.matchesImage(
                host = BuildConfig.PHOTO_SIGNATURE_IMAGE_URL_1,
                mimeType = BuildConfig.PHOTO_SIGNATURE_IMAGE_URL_MIME_TYPE_1,
            ) || url.matchesImage(
                host = BuildConfig.PHOTO_SIGNATURE_IMAGE_URL_2,
                mimeType = BuildConfig.PHOTO_SIGNATURE_IMAGE_URL_MIME_TYPE_2,
            ) -> QRImageProvider.PHOTO_SIGNATURE

            url.matchesImage(
                host = BuildConfig.HARU_FILM_IMAGE_URL,
                mimeType = BuildConfig.HARU_FILM_IMAGE_URL_MIME_TYPE,
            ) -> QRImageProvider.HARU_FILM

            url.matchesImage(
                host = BuildConfig.PHOTO_GRAY_IMAGE_URL,
                mimeType = BuildConfig.PHOTO_GRAY_IMAGE_URL_MIME_TYPE,
            ) -> QRImageProvider.PHOTO_GRAY

            url.matchesImage(
                host = BuildConfig.MONO_MANSION_IMAGE_URL,
                mimeType = BuildConfig.MONO_MANSION_IMAGE_URL_MIME_TYPE,
                ignoreCase = true,
            ) -> QRImageProvider.MONO_MANSION

            else -> null
        }
    }

    private fun String.containsConfiguredUrl(configuredUrl: String?): Boolean {
        if (configuredUrl.isNullOrBlank()) return false

        return contains(configuredUrl)
    }

    private fun String.matchesImage(
        host: String?,
        mimeType: String?,
        ignoreCase: Boolean = false,
    ): Boolean {
        if (host.isNullOrBlank() || mimeType.isNullOrBlank()) return false

        return contains(host, ignoreCase = ignoreCase) && endsWith(mimeType, ignoreCase = ignoreCase)
    }
}
