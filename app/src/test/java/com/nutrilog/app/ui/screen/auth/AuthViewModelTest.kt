package com.nutrilog.app.ui.screen.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.usecase.AuthUseCase
import com.nutrilog.app.utils.DummyData
import com.nutrilog.app.utils.MainDispatcherRule
import com.nutrilog.app.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var useCase: AuthUseCase

    private lateinit var viewModel: AuthViewModel

    private val dummyUserId = DummyData.idDummy
    private val dummyName = DummyData.nameDummy
    private val dummyEmail = DummyData.emailDummy
    private val dummyPassword = DummyData.passwordDummy
    private val dummyGender = DummyData.genderDummy
    private val dummyDateOfBirth = DummyData.dateOfBirthDummy
    private val dummyToken = DummyData.tokenDummy
    private val dummyUser = DummyData.userDummy

    @Before
    fun setUp() {
        useCase = mock(AuthUseCase::class.java)
        viewModel = AuthViewModel(useCase)
    }

    @Test
    fun `Sign in success and get result success`(): Unit = runTest {
        val expectedResult = ResultState.Success(dummyToken)
        val flowResult = flowOf(expectedResult)

        `when`(useCase.signIn(dummyEmail, dummyPassword)).thenReturn(flowResult)

        val result = viewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        assertTrue(result is ResultState.Success)
        assertEquals(dummyToken, (result as ResultState.Success).data)
        verify(useCase).signIn(dummyEmail, dummyPassword)
    }

    @Test
    fun `signIn failure should return Error result`() = runTest {
        val errorMessage = "Sign in failed"
        val expectedResult = ResultState.Error(errorMessage)
        val flowResult = flowOf(expectedResult)

        `when`(useCase.signIn(dummyEmail, dummyPassword)).thenReturn(flowResult)

        val result = viewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        assertTrue(result is ResultState.Error)
        assertEquals(errorMessage, (result as ResultState.Error).message)
        verify(useCase).signIn(dummyEmail, dummyPassword)
    }

    @Test
    fun `signUp success should return Success result`() = runTest {
        val successMessage = "Sign up success"
        val expectedResult = ResultState.Success(successMessage)
        val flowResult = flowOf(expectedResult)

        `when`(useCase.signUp(dummyName, dummyEmail, dummyPassword, dummyGender, dummyDateOfBirth)).thenReturn(flowResult)

        val result = viewModel.signUp(dummyName, dummyEmail, dummyPassword, dummyGender, dummyDateOfBirth).getOrAwaitValue()

        assertTrue(result is ResultState.Success)
        assertEquals(successMessage, (result as ResultState.Success).data)
        verify(useCase).signUp(dummyName, dummyEmail, dummyPassword, dummyGender, dummyDateOfBirth)
    }

    @Test
    fun `getSession should return User`() = runTest {
        val flowResult = flowOf(dummyUser)

        `when`(useCase.getSession()).thenReturn(flowResult)

        val result = viewModel.user.getOrAwaitValue()

        assertEquals(dummyUser, result)
        verify(useCase).getSession()
    }

    @Test
    fun `signOut success should return Success result`() = runTest {
        val expectedResult = ResultState.Success(dummyToken)
        val flowResult = flowOf(expectedResult)

        `when`(useCase.signOut(dummyUserId)).thenReturn(flowResult)

        val result = viewModel.signOut(dummyUserId).getOrAwaitValue()

        assertTrue(result is ResultState.Success)
        assertEquals(dummyToken, (result as ResultState.Success).data)
        verify(useCase).signOut(dummyUserId)
    }
}