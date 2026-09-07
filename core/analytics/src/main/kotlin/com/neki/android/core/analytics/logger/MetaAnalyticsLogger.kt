package com.neki.android.core.analytics.logger

import com.neki.android.core.analytics.event.MetaAnalyticsEvent

interface MetaAnalyticsLogger {
    fun log(event: MetaAnalyticsEvent)
}
