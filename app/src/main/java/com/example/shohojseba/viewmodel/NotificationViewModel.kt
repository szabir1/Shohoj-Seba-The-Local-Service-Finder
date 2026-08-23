package com.example.shohojseba.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.AppNotification
import com.example.shohojseba.data.repository.NotificationRepository

import kotlinx.coroutines.launch


class NotificationViewModel : ViewModel() {


    private val repository =
        NotificationRepository()


    // =====================================================
    // NOTIFICATIONS
    // =====================================================

    private val _notifications =
        mutableStateOf<List<AppNotification>>(
            emptyList()
        )

    val notifications:
            State<List<AppNotification>> =
        _notifications


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)

    val isLoading:
            State<Boolean> =
        _isLoading


    // =====================================================
    // UNREAD COUNT
    // =====================================================

    val unreadCount: Int
        get() =
            _notifications
                .value
                .count {

                    !it.is_read

                }


    // =====================================================
    // LOAD
    // =====================================================

    fun loadNotifications() {


        viewModelScope.launch {


            _isLoading.value =
                true


            val result =
                repository
                    .getCustomerNotifications()


            if (
                result.isSuccess
            ) {

                _notifications.value =
                    result
                        .getOrNull()
                        ?: emptyList()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // MARK ONE READ
    // =====================================================

    fun markAsRead(

        notificationId: Long

    ) {


        viewModelScope.launch {


            val result =
                repository
                    .markAsRead(
                        notificationId
                    )


            if (
                result.isSuccess
            ) {


                _notifications.value =
                    _notifications
                        .value
                        .map {

                            if (
                                it.notification_id ==
                                notificationId
                            ) {

                                it.copy(
                                    is_read = true
                                )

                            } else {

                                it

                            }

                        }

            }

        }

    }


    // =====================================================
    // MARK ALL READ
    // =====================================================

    fun markAllAsRead() {


        viewModelScope.launch {


            val result =
                repository
                    .markAllAsRead()


            if (
                result.isSuccess
            ) {


                _notifications.value =
                    _notifications
                        .value
                        .map {

                            it.copy(
                                is_read = true
                            )

                        }

            }

        }

    }

}