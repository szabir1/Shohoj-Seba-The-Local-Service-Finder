package com.example.shohojseba.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shohojseba.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    fun register(
        role: String,
        name: String,
        phone: String,
        email: String,
        password: String,
        experience: Int = 0
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            val result = repository.register(
                role,
                name,
                phone,
                email,
                password,
                experience
            )

            _message.value =
                if (result.isSuccess)
                    "Registration successful"
                else
                    result.exceptionOrNull()?.message ?: "Registration failed"

            _isLoading.value = false
        }
    }

    fun login(
        email: String,
        password: String,
        onLoginSuccess: (String) -> Unit
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            val result = repository.login(email, password)

            if (result.isSuccess) {

                _message.value = "Login successful"

                onLoginSuccess(result.getOrNull() ?: "")

            } else {

                _message.value =
                    result.exceptionOrNull()?.message ?: "Login failed"
            }

            _isLoading.value = false
        }
    }

    fun logout() {

        viewModelScope.launch {

            repository.logout()

            _message.value = "Logged out"
        }
    }
}