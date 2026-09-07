package com.neki.android.core.analytics.event

import com.facebook.appevents.AppEventsConstants

sealed interface MetaAnalyticsEvent {
    val name: String
    val params: Map<String, Any?>

    data object CompleteRegistration : MetaAnalyticsEvent {
        override val name: String = AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION
        override val params: Map<String, Any?> = mapOf(
            AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD to "kakao",
        )
    }
}
