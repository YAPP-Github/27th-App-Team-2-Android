package com.neki.android.core.analytics.logger

import com.neki.android.core.analytics.event.MetaAnalyticsEvent
import timber.log.Timber
import javax.inject.Inject

internal class FacebookMetaAnalyticsLogger @Inject constructor(
    private val metaAnalyticsClient: MetaAnalyticsClient,
) : MetaAnalyticsLogger {

    override fun log(event: MetaAnalyticsEvent) {
        runCatching {
            metaAnalyticsClient.track(
                eventName = event.name,
                eventProperties = event.params,
            )
        }.onFailure { throwable ->
            Timber.e(throwable, "Failed to log Meta analytics event: %s", event.name)
        }
    }
}
