package com.example.shohojseba.viewmodel


import android.util.Log

import androidx.compose.runtime.mutableStateMapOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.ServiceDetails
import com.example.shohojseba.data.repository.FavoriteRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavoriteViewModel : ViewModel() {


    private val repository =
        FavoriteRepository()


    // =====================================================
    // FAVORITE IDS
    // =====================================================

    private val _favoriteIds =
        MutableStateFlow<Set<Long>>(
            emptySet()
        )


    val favoriteIds:
            StateFlow<Set<Long>> =
        _favoriteIds


    // =====================================================
    // FAVORITE SERVICES
    // =====================================================

    private val _favoriteServices =
        MutableStateFlow<List<ServiceDetails>>(
            emptyList()
        )


    val favoriteServices:
            StateFlow<List<ServiceDetails>> =
        _favoriteServices


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        MutableStateFlow(
            false
        )


    val isLoading:
            StateFlow<Boolean> =
        _isLoading


    // =====================================================
    // MESSAGE
    // =====================================================

    private val _message =
        MutableStateFlow(
            ""
        )


    val message:
            StateFlow<String> =
        _message


    // =====================================================
    // LOAD FAVORITE IDS
    // =====================================================

    fun loadFavoriteIds() {


        viewModelScope.launch {


            try {


                val result =
                    repository
                        .getFavoriteServiceIds()


                if (
                    result.isSuccess
                ) {


                    _favoriteIds.value =

                        result
                            .getOrDefault(
                                emptySet()
                            )


                } else {


                    Log.e(
                        "FAVORITE_TEST",
                        "Could not load favorite IDs"
                    )

                }


            } catch (e: Exception) {


                Log.e(
                    "FAVORITE_TEST",
                    "LOAD IDS ERROR = ${e.message}",
                    e
                )

            }

        }

    }


    // =====================================================
    // LOAD FAVORITE SERVICES
    // =====================================================

    fun loadFavoriteServices() {


        viewModelScope.launch {


            _isLoading.value =
                true


            try {


                val result =
                    repository
                        .getFavoriteServices()


                if (
                    result.isSuccess
                ) {


                    _favoriteServices.value =

                        result
                            .getOrDefault(
                                emptyList()
                            )


                    _favoriteIds.value =

                        _favoriteServices
                            .value
                            .map {

                                it.service_id

                            }
                            .toSet()


                } else {


                    _favoriteServices.value =
                        emptyList()


                    _message.value =
                        result
                            .exceptionOrNull()
                            ?.message
                            ?: "Unable to load favorites"

                }


            } catch (e: Exception) {


                _favoriteServices.value =
                    emptyList()


                _message.value =
                    e.message
                        ?: "Unable to load favorites"


            } finally {


                _isLoading.value =
                    false

            }

        }

    }


    // =====================================================
    // CHECK LOCAL FAVORITE STATE
    // =====================================================

    fun isFavorite(
        serviceId: Long
    ): Boolean {


        return _favoriteIds
            .value
            .contains(
                serviceId
            )

    }


    // =====================================================
    // TOGGLE FAVORITE
    // =====================================================

    fun toggleFavorite(
        serviceId: Long
    ) {


        viewModelScope.launch {


            val currentlyFavorite =

                _favoriteIds
                    .value
                    .contains(
                        serviceId
                    )


            if (
                currentlyFavorite
            ) {


                val result =
                    repository
                        .removeFavorite(
                            serviceId
                        )


                if (
                    result.isSuccess
                ) {


                    _favoriteIds.value =

                        _favoriteIds
                            .value -
                                serviceId


                    _favoriteServices.value =

                        _favoriteServices
                            .value
                            .filterNot {

                                it.service_id ==
                                        serviceId

                            }


                    _message.value =
                        "Removed from favorites"

                } else {


                    _message.value =
                        "Could not remove favorite"

                }


            } else {


                val result =
                    repository
                        .addFavorite(
                            serviceId
                        )


                if (
                    result.isSuccess
                ) {


                    _favoriteIds.value =

                        _favoriteIds
                            .value +
                                serviceId


                    _message.value =
                        "Added to favorites"

                } else {


                    _message.value =
                        "Could not add favorite"

                }

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

}