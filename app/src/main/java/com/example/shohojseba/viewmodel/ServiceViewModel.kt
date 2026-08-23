package com.example.shohojseba.viewmodel

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.ServiceDetails
import com.example.shohojseba.data.repository.ServiceRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {

    private val repository =
        ServiceRepository()


    private val _services =
        MutableStateFlow<List<ServiceDetails>>(
            emptyList()
        )

    val services:
            StateFlow<List<ServiceDetails>> =
        _services


    private val _isLoading =
        MutableStateFlow(false)

    val isLoading:
            StateFlow<Boolean> =
        _isLoading


    // =====================================================
    // CATEGORY ONLY
    // =====================================================

    fun loadServicesByCategory(
        categoryId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            try {

                _services.value =
                    repository
                        .getServicesByCategory(
                            categoryId
                        )

            } catch (e: Exception) {

                Log.e(
                    "SERVICE_TEST",
                    "VIEWMODEL ERROR = ${e.message}"
                )

                _services.value =
                    emptyList()

            }

            _isLoading.value =
                false

        }

    }


    // =====================================================
    // CATEGORY + AREA
    // =====================================================

    fun loadServicesByCategoryAndArea(

        categoryId: Long,

        areaId: Long

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            try {

                val result =
                    repository
                        .getServicesByCategoryAndArea(

                            categoryId =
                                categoryId,

                            areaId =
                                areaId

                        )


                Log.d(
                    "SERVICE_TEST",
                    "AREA VIEWMODEL RECEIVED = $result"
                )


                _services.value =
                    result

            } catch (e: Exception) {

                Log.e(
                    "SERVICE_TEST",
                    "AREA VIEWMODEL ERROR = ${e.message}"
                )

                _services.value =
                    emptyList()

            }

            _isLoading.value =
                false

        }

    }

}