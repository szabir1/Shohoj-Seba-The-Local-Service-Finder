package com.example.shohojseba.viewmodel

import android.util.Log

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.repository.AuthRepository

import kotlinx.coroutines.launch


class AuthViewModel : ViewModel() {


    private val repository =
        AuthRepository()


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)


    val isLoading:
            State<Boolean> =
        _isLoading


    // =====================================================
    // MESSAGE
    // =====================================================

    private val _message =
        mutableStateOf("")


    val message:
            State<String> =
        _message


    // =====================================================
    // REGISTER
    // =====================================================

    fun register(

        role: String,

        name: String,

        phone: String,

        email: String,

        password: String,

        experience: Int = 0

    ) {


        if (
            name.isBlank()
        ) {

            _message.value =
                "Please enter your name."

            return

        }


        if (
            phone.isBlank()
        ) {

            _message.value =
                "Please enter your phone number."

            return

        }


        if (
            email.isBlank()
        ) {

            _message.value =
                "Please enter your email address."

            return

        }


        if (
            password.isBlank()
        ) {

            _message.value =
                "Please enter a password."

            return

        }


        viewModelScope.launch {


            _isLoading.value =
                true


            _message.value =
                ""


            val result =
                repository.register(

                    role =
                        role,

                    name =
                        name,

                    phone =
                        phone,

                    email =
                        email.trim(),

                    password =
                        password,

                    experience =
                        experience

                )


            if (
                result.isSuccess
            ) {


                _message.value =
                    "Registration successful"


            } else {


                val rawMessage =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: ""


                Log.e(

                    "AUTH_TEST",

                    "REGISTER ERROR = $rawMessage",

                    result.exceptionOrNull()

                )


                _message.value =
                    getFriendlyAuthMessage(
                        rawMessage
                    )

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // LOGIN
    // =====================================================

    fun login(

        email: String,

        password: String,

        onLoginSuccess: (String) -> Unit

    ) {


        if (
            email.isBlank()
        ) {

            _message.value =
                "Please enter your email address."

            return

        }


        if (
            password.isBlank()
        ) {

            _message.value =
                "Please enter your password."

            return

        }


        if (
            !android.util.Patterns
                .EMAIL_ADDRESS
                .matcher(
                    email.trim()
                )
                .matches()
        ) {

            _message.value =
                "Please enter a valid email address."

            return

        }


        viewModelScope.launch {


            _isLoading.value =
                true


            _message.value =
                ""


            val result =
                repository.login(

                    email =
                        email.trim(),

                    password =
                        password

                )


            if (
                result.isSuccess
            ) {


                _message.value =
                    "Login successful"


                onLoginSuccess(

                    result
                        .getOrNull()
                        ?: ""

                )


            } else {


                val rawMessage =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: ""


                Log.e(

                    "AUTH_TEST",

                    "LOGIN ERROR = $rawMessage",

                    result.exceptionOrNull()

                )


                _message.value =
                    getFriendlyAuthMessage(
                        rawMessage
                    )

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // FRIENDLY ERROR CONVERTER
    // =====================================================

    private fun getFriendlyAuthMessage(

        rawMessage: String

    ): String {


        val message =
            rawMessage.lowercase()


        return when {


            message.contains(
                "invalid_credentials"
            ) ||
                    message.contains(
                        "invalid login credentials"
                    ) -> {

                "Incorrect email or password. Please check your credentials and try again."

            }


            message.contains(
                "already registered"
            ) ||
                    message.contains(
                        "user already registered"
                    ) -> {

                "An account already exists with this email address."

            }


            message.contains(
                "invalid email"
            ) -> {

                "Please enter a valid email address."

            }


            message.contains(
                "password"
            ) &&
                    (
                            message.contains(
                                "6"
                            ) ||
                                    message.contains(
                                        "short"
                                    ) ||
                                    message.contains(
                                        "characters"
                                    )
                            ) -> {

                "Your password is too short. Please use at least 6 characters."

            }


            message.contains(
                "network"
            ) ||
                    message.contains(
                        "unable to resolve host"
                    ) ||
                    message.contains(
                        "failed to connect"
                    ) ||
                    message.contains(
                        "timeout"
                    ) -> {

                "Unable to connect. Please check your internet connection and try again."

            }


            message.contains(
                "profile not found"
            ) -> {

                "Your account profile could not be found. Please contact support."

            }


            message.contains(
                "suspended"
            ) -> {

                "Your provider account has been suspended. Please contact the administrator."

            }


            message.contains(
                "no longer active"
            ) -> {

                "This provider account is no longer active."

            }


            message.contains(
                "account status is invalid"
            ) -> {

                "Your provider account status could not be verified. Please contact the administrator."

            }


            else -> {

                "Something went wrong. Please try again."

            }

        }

    }


    // =====================================================
    // CLEAR MESSAGE
    // =====================================================

    fun clearMessage() {


        _message.value =
            ""

    }


    // =====================================================
    // LOGOUT
    // =====================================================

    fun logout(

        onLogoutComplete: () -> Unit = {}

    ) {


        viewModelScope.launch {


            _isLoading.value =
                true


            val result =
                repository.logout()


            if (
                result.isSuccess
            ) {


                _message.value =
                    "Logged out"


                _isLoading.value =
                    false


                onLogoutComplete()


            } else {


                Log.e(

                    "AUTH_TEST",

                    "LOGOUT ERROR = ${
                        result
                            .exceptionOrNull()
                            ?.message
                    }",

                    result.exceptionOrNull()

                )


                // Local session is already cleared in repository.
                // Continue to Login even if remote sign-out failed.

                _message.value =
                    "Logged out"


                _isLoading.value =
                    false


                onLogoutComplete()

            }

        }

    }

}