package com.neki.android.core.analytics.logger

import com.neki.android.core.analytics.event.MetaAnalyticsEvent
import org.junit.Assert.assertEquals
import org.junit.Test

class FacebookMetaAnalyticsLoggerTest {

    @Test
    fun `회원가입 완료 이벤트를 Meta 표준 이름과 카카오 가입 방식으로 전달한다`() {
        val client = RecordingMetaAnalyticsClient()
        val logger = FacebookMetaAnalyticsLogger(client)

        logger.log(MetaAnalyticsEvent.CompleteRegistration)

        assertEquals(
            listOf(
                TrackedEvent(
                    name = "fb_mobile_complete_registration",
                    properties = mapOf("fb_registration_method" to "kakao"),
                ),
            ),
            client.trackedEvents,
        )
    }

    @Test
    fun `Meta SDK가 예외를 던져도 호출자에게 전파하지 않는다`() {
        val client = ThrowingMetaAnalyticsClient()
        val logger = FacebookMetaAnalyticsLogger(client)

        logger.log(MetaAnalyticsEvent.CompleteRegistration)

        assertEquals(1, client.trackCallCount)
    }

    private class RecordingMetaAnalyticsClient : MetaAnalyticsClient {
        val trackedEvents = mutableListOf<TrackedEvent>()

        override fun track(eventName: String, eventProperties: Map<String, Any?>) {
            trackedEvents += TrackedEvent(eventName, eventProperties)
        }
    }

    private class ThrowingMetaAnalyticsClient : MetaAnalyticsClient {
        var trackCallCount = 0

        override fun track(eventName: String, eventProperties: Map<String, Any?>) {
            trackCallCount++
            error("Meta SDK failure")
        }
    }

    private data class TrackedEvent(
        val name: String,
        val properties: Map<String, Any?>,
    )
}
