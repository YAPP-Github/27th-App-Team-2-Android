package com.neki.android.feature.photo_upload.impl.qrscan.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QRUrlMatcherTest {
    @Test
    fun `포토이즘과 픽픽링크 QR은 지원 브랜드로 인식한다`() {
        assertTrue(QRUrlMatcher.isSupportedBrand("https://qr.seobuk.kr/s/RQ1Tl~L"))
        assertTrue(QRUrlMatcher.isSupportedBrand("https://t.pixpixlink.com/g4?d=T4797&i=SoaVlwvw"))
        assertFalse(QRUrlMatcher.isSupportedBrand("https://unsupported.example/qr"))
    }

    @Test
    fun `포토이즘과 픽픽링크는 첫 다운로드가 필요하지 않다`() {
        assertFalse(QRUrlMatcher.isFirstDownloadRequired("https://qr.seobuk.kr/s/RQ1Tl~L"))
        assertFalse(QRUrlMatcher.isFirstDownloadRequired("https://t.pixpixlink.com/g4?d=T4797&i=SoaVlwvw"))
    }

    @Test
    fun `포토이즘의 기존 S3와 신규 다운로드 호스트를 모두 감지한다`() {
        assertEquals(
            QRImageProvider.PHOTOISM,
            QRUrlMatcher.detectImageProvider(
                "https://photoism-cms-prd.s3.ap-northeast-2.amazonaws.com/old.jpg",
            ),
        )
        assertEquals(
            QRImageProvider.PHOTOISM,
            QRUrlMatcher.detectImageProvider(
                "https://download.seobuk.kr/tmp/tmp-id_Pic.jpg",
            ),
        )
    }

    @Test
    fun `픽픽링크 사진은 감지하고 영상은 사진으로 감지하지 않는다`() {
        assertEquals(
            QRImageProvider.PIXPIXLINK,
            QRUrlMatcher.detectImageProvider(
                "https://t.pixpixlink.com/t/T4797/SoaVlwvw.jpg",
            ),
        )
        assertEquals(
            null,
            QRUrlMatcher.detectImageProvider(
                "https://t.pixpixlink.com/t/T4797/SoaVlwvw.mp4",
            ),
        )
    }
}
