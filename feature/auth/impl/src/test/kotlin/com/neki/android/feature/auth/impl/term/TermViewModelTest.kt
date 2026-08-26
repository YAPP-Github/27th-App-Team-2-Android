package com.neki.android.feature.auth.impl.term

import com.neki.android.core.analytics.event.MetaAnalyticsEvent
import com.neki.android.core.analytics.logger.MetaAnalyticsLogger
import com.neki.android.core.dataapi.repository.AuthRepository
import com.neki.android.core.dataapi.repository.TermRepository
import com.neki.android.core.model.AppVersion
import com.neki.android.core.model.Auth
import com.neki.android.core.model.Term
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TermViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `약관 동의 성공 시 회원가입 완료를 정확히 한 번 전송하고 메인으로 이동한다`() = runTest {
        val termRepository = FakeTermRepository(agreeResult = Result.success(Unit))
        val authRepository = FakeAuthRepository()
        val metaAnalyticsLogger = RecordingMetaAnalyticsLogger()
        val viewModel = TermViewModel(termRepository, authRepository, metaAnalyticsLogger)
        advanceUntilIdle()
        val sideEffects = mutableListOf<TermSideEffect>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.store.sideEffects.toList(sideEffects)
        }

        viewModel.store.onIntent(TermIntent.ClickAgreeAll)
        viewModel.store.onIntent(TermIntent.ClickNext)
        viewModel.store.onIntent(TermIntent.ClickNext)
        advanceUntilIdle()

        assertEquals(1, termRepository.agreeCallCount)
        assertEquals(listOf(MetaAnalyticsEvent.CompleteRegistration), metaAnalyticsLogger.events)
        assertEquals(listOf(true), authRepository.completedOnboardingValues)
        assertEquals(listOf(TermSideEffect.NavigateToMain), sideEffects)
    }

    @Test
    fun `약관 동의 실패 시 회원가입 완료 이벤트를 전송하지 않는다`() = runTest {
        val termRepository = FakeTermRepository(agreeResult = Result.failure(IllegalStateException("failure")))
        val metaAnalyticsLogger = RecordingMetaAnalyticsLogger()
        val viewModel = TermViewModel(termRepository, FakeAuthRepository(), metaAnalyticsLogger)
        advanceUntilIdle()

        viewModel.store.onIntent(TermIntent.ClickAgreeAll)
        viewModel.store.onIntent(TermIntent.ClickNext)
        advanceUntilIdle()

        assertEquals(emptyList<MetaAnalyticsEvent>(), metaAnalyticsLogger.events)
    }

    private class FakeTermRepository(
        private val agreeResult: Result<Unit>,
    ) : TermRepository {
        var agreeCallCount = 0

        override suspend fun getTerms(): Result<List<Term>> = Result.success(
            listOf(
                Term(
                    id = 1L,
                    title = "필수 약관",
                    url = "https://example.com/required",
                    isRequired = true,
                ),
                Term(
                    id = 2L,
                    title = "선택 약관",
                    url = "https://example.com/optional",
                    isRequired = false,
                ),
            ),
        )

        override suspend fun agreeTerms(termIds: List<Long>): Result<Unit> {
            agreeCallCount++
            return agreeResult
        }

        override suspend fun updateTermAgreement(termId: Long, agreed: Boolean): Result<Unit> =
            error("Not used")
    }

    private class FakeAuthRepository : AuthRepository {
        val completedOnboardingValues = mutableListOf<Boolean>()

        override val dismissedVersion: Flow<String> = flowOf("")

        override suspend fun getAppVersion(): Result<AppVersion> = error("Not used")

        override suspend fun setDismissedVersion(version: String) = error("Not used")

        override suspend fun loginWithKakao(idToken: String): Result<Auth> = error("Not used")

        override suspend fun updateAccessToken(refreshToken: String): Result<Auth> = error("Not used")

        override suspend fun logout(): Result<Unit> = error("Not used")

        override suspend fun withdrawAccount(): Result<Unit> = error("Not used")

        override fun hasCompletedOnboarding(): Flow<Boolean> = flowOf(false)

        override suspend fun setCompletedOnboarding(value: Boolean) {
            completedOnboardingValues += value
        }
    }

    private class RecordingMetaAnalyticsLogger : MetaAnalyticsLogger {
        val events = mutableListOf<MetaAnalyticsEvent>()

        override fun log(event: MetaAnalyticsEvent) {
            events += event
        }
    }
}
