package com.example.shohojseba.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.ProviderArea
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.repository.ProviderRepository

import kotlinx.coroutines.launch

class ProviderViewModel : ViewModel() {

    private val repository =
        ProviderRepository()


    // =====================================================
    // PROVIDER
    // =====================================================

    private val _provider =
        mutableStateOf<Provider?>(null)

    val provider: State<Provider?> =
        _provider


    // =====================================================
    // SERVICES
    // =====================================================

    private val _services =
        mutableStateOf<List<Service>>(emptyList())

    val services: State<List<Service>> =
        _services


    // =====================================================
    // CATEGORIES
    // =====================================================

    private val _categories =
        mutableStateOf<List<Category>>(emptyList())

    val categories: State<List<Category>> =
        _categories


    // =====================================================
    // ALL AREAS
    // =====================================================

    private val _areas =
        mutableStateOf<List<Area>>(emptyList())

    val areas: State<List<Area>> =
        _areas


    // =====================================================
    // SELECTED PROVIDER AREAS
    // =====================================================

    private val _selectedAreaIds =
        mutableStateOf<Set<Long>>(emptySet())

    val selectedAreaIds: State<Set<Long>> =
        _selectedAreaIds


    // =====================================================
    // MESSAGE
    // =====================================================

    private val _message =
        mutableStateOf("")

    val message: State<String> =
        _message


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading


    // =====================================================
    // LOAD PROVIDER PROFILE
    // =====================================================

    fun loadProviderProfile() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getCurrentProvider()

            if (result.isSuccess) {

                val providerData =
                    result.getOrNull()

                _provider.value =
                    providerData

                Log.d(
                    "PROVIDER_TEST",
                    "CURRENT PROVIDER = $providerData"
                )

                providerData
                    ?.provider_id
                    ?.let { providerId ->

                        loadProviderServices(
                            providerId
                        )

                        loadCategories()

                        loadAreas()

                        loadProviderAreas(
                            providerId
                        )

                    }

            } else {

                _message.value =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: "Failed to load provider"

            }

            _isLoading.value =
                false

        }

    }


    // =====================================================
    // LOAD SERVICES
    // =====================================================

    private fun loadProviderServices(
        providerId: Long
    ) {

        viewModelScope.launch {

            val result =
                repository.getProviderServices(
                    providerId
                )

            if (result.isSuccess) {

                _services.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: "Failed to load services"

            }

        }

    }


    // =====================================================
    // LOAD CATEGORIES
    // =====================================================

    fun loadCategories() {

        viewModelScope.launch {

            val result =
                repository.getCategories()

            if (result.isSuccess) {

                _categories.value =
                    result.getOrNull()
                        ?: emptyList()

            }

        }

    }


    // =====================================================
    // LOAD ALL AREAS
    // =====================================================

    fun loadAreas() {

        viewModelScope.launch {

            val result =
                repository.getAreas()

            if (result.isSuccess) {

                _areas.value =
                    result.getOrNull()
                        ?: emptyList()

                Log.d(
                    "AREA_TEST",
                    "AREAS = ${_areas.value}"
                )

            } else {

                Log.e(
                    "AREA_TEST",
                    "AREA ERROR = ${
                        result.exceptionOrNull()?.message
                    }"
                )

            }

        }

    }


    // =====================================================
    // LOAD PROVIDER SELECTED AREAS
    // =====================================================

    private fun loadProviderAreas(
        providerId: Long
    ) {

        viewModelScope.launch {

            val result =
                repository.getProviderAreas(
                    providerId
                )

            if (result.isSuccess) {

                val providerAreas =
                    result.getOrNull()
                        ?: emptyList()

                _selectedAreaIds.value =
                    providerAreas
                        .map {
                            it.area_id
                        }
                        .toSet()

                Log.d(
                    "AREA_TEST",
                    "SELECTED AREAS = ${
                        _selectedAreaIds.value
                    }"
                )

            }

        }

    }


    // =====================================================
    // TOGGLE AREA
    // =====================================================

    fun toggleArea(
        areaId: Long
    ) {

        val current =
            _selectedAreaIds.value
                .toMutableSet()

        if (
            current.contains(
                areaId
            )
        ) {

            current.remove(
                areaId
            )

        } else {

            current.add(
                areaId
            )

        }

        _selectedAreaIds.value =
            current

    }


    // =====================================================
    // SAVE AREA SELECTIONS
    // =====================================================

    fun saveProviderAreas() {

        val providerId =
            _provider.value
                ?.provider_id
                ?: return

        viewModelScope.launch {

            _isLoading.value =
                true

            val existingResult =
                repository.getProviderAreas(
                    providerId
                )

            val existingAreaIds =
                existingResult
                    .getOrNull()
                    ?.map {
                        it.area_id
                    }
                    ?.toSet()
                    ?: emptySet()

            val selected =
                _selectedAreaIds.value


            // Areas that need INSERT
            val areasToAdd =
                selected -
                        existingAreaIds


            // Areas that need DELETE
            val areasToRemove =
                existingAreaIds -
                        selected


            areasToAdd.forEach { areaId ->

                repository.addProviderArea(

                    providerId =
                        providerId,

                    areaId =
                        areaId

                )

            }


            areasToRemove.forEach { areaId ->

                repository.removeProviderArea(

                    providerId =
                        providerId,

                    areaId =
                        areaId

                )

            }


            _message.value =
                "Service areas updated successfully"

            loadProviderAreas(
                providerId
            )

            _isLoading.value =
                false

        }

    }


    // =====================================================
    // ADD SERVICE
    // =====================================================

    fun addService(
        service: AddServiceRequest
    ) {

        viewModelScope.launch {

            val result =
                repository.addService(
                    service
                )

            if (result.isSuccess) {

                _message.value =
                    "Service added successfully"

                _provider.value
                    ?.provider_id
                    ?.let { providerId ->

                        loadProviderServices(
                            providerId
                        )

                    }

            } else {

                _message.value =
                    result
                        .exceptionOrNull()
                        ?.message
                        ?: "Failed to add service"

            }

        }

    }

}