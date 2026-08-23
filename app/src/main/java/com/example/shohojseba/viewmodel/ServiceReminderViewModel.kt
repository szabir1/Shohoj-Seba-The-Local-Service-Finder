package com.example.shohojseba.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.ServiceReminder
import com.example.shohojseba.data.repository.ServiceReminderRepository

import kotlinx.coroutines.launch


class ServiceReminderViewModel : ViewModel() {


    private val repository =
        ServiceReminderRepository()


    private val _reminders =
        mutableStateOf<List<ServiceReminder>>(
            emptyList()
        )

    val reminders:
            State<List<ServiceReminder>> =
        _reminders


    private val _isLoading =
        mutableStateOf(false)

    val isLoading:
            State<Boolean> =
        _isLoading


    private val _message =
        mutableStateOf("")

    val message:
            State<String> =
        _message


    // =====================================================
    // LOAD CUSTOMER REMINDERS
    // =====================================================

    fun loadReminders() {


        viewModelScope.launch {


            _isLoading.value =
                true


            val result =
                repository
                    .getCustomerReminders()


            if (
                result.isSuccess
            ) {

                _reminders.value =
                    result
                        .getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: "Failed to load reminders"

            }


            _isLoading.value =
                false

        }

    }

}