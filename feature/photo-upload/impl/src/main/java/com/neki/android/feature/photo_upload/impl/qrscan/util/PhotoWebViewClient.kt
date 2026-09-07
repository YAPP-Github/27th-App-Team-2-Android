package com.neki.android.feature.photo_upload.impl.qrscan.util

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import timber.log.Timber

class PhotoWebViewClient(
    private val onPageFinished: () -> Unit,
    private val onPageError: () -> Unit,
    private val onImageUrlDetected: (String) -> Unit,
) : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?,
    ): WebResourceResponse? {
        val url = request?.url.toString()
        Timber.d(request?.url.toString())

        when (QRUrlMatcher.detectImageProvider(url)) {
            QRImageProvider.PHOTOISM -> {
                Timber.d("포토이즘 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.PIXPIXLINK -> {
                Timber.d("픽픽링크 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.LIFE_FOUR_CUT -> {
                Timber.d("인생네컷 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.PHOTO_SIGNATURE -> {
                Timber.d("포토시그니처 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.HARU_FILM -> {
                Timber.d("하루필름 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.PHOTO_GRAY -> {
                Timber.d("포토그레이 이미지")
                onImageUrlDetected(url)
            }

            QRImageProvider.MONO_MANSION -> {
                Timber.d("모노맨션 이미지")
                onImageUrlDetected(url)
            }

            null -> Unit
        }

        return super.shouldInterceptRequest(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinished()
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
        super.onReceivedError(view, request, error)
        if (request?.isForMainFrame == true) {
            Timber.e("WebView main frame error: ${error?.description}")
            onPageError()
        }
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (request?.isForMainFrame == true) {
            Timber.e("WebView main frame HTTP error: ${errorResponse?.statusCode}")
            onPageError()
        }
    }
}
