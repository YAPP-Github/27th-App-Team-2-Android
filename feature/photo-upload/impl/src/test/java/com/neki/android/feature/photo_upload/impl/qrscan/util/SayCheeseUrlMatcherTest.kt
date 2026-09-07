package com.neki.android.feature.photo_upload.impl.qrscan.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SayCheeseUrlMatcherTest {

    @Test
    fun `세이치즈 QR URL을 지원한다`() {
        val url = "http://thesaycheese.co.kr/?idx=KOR8GOC4Q38U1GMA316F&ymd=260826"

        assertTrue(SayCheeseUrlMatcher.isSupportedQrUrl(url))
    }

    @Test
    fun `세이치즈 도메인을 포함한 다른 호스트의 QR URL은 지원하지 않는다`() {
        val url = "http://example.com/?redirect=http://thesaycheese.co.kr/"

        assertFalse(SayCheeseUrlMatcher.isSupportedQrUrl(url))
    }

    @Test
    fun `세이치즈 원본 이미지 URL을 감지한다`() {
        val url = "http://thesaycheese.co.kr/image/260826/KOR8GOC4Q38U1GMA316F.jpg"

        assertTrue(SayCheeseUrlMatcher.isOriginalImageUrl(url))
    }

    @Test
    fun `세이치즈 장식용 JPG URL은 원본 이미지로 감지하지 않는다`() {
        val url = "http://thesaycheese.co.kr/img/top_img.jpg"

        assertFalse(SayCheeseUrlMatcher.isOriginalImageUrl(url))
    }

    @Test
    fun `세이치즈 이미지 경로를 포함한 다른 호스트는 원본 이미지로 감지하지 않는다`() {
        val url = "http://example.com/proxy/thesaycheese.co.kr/image/260826/KOR8GOC4Q38U1GMA316F.jpg"

        assertFalse(SayCheeseUrlMatcher.isOriginalImageUrl(url))
    }

    @Test
    fun `세이치즈 동영상 URL은 원본 이미지로 감지하지 않는다`() {
        val url = "http://thesaycheese.co.kr/video/260826/KOR8GOC4Q38U1GMA316F.mp4"

        assertFalse(SayCheeseUrlMatcher.isOriginalImageUrl(url))
    }
}
