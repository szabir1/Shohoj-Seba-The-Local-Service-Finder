package com.example.shohojseba.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.repository.AreaRepository
import kotlinx.coroutines.launch

class AreaViewModel : ViewModel() {

    private val repository =
        AreaRepository()

    private val _areas =
        mutableStateOf<List<Area>>(
            emptyList()
        )

    val areas: State<List<Area>> =
        _areas

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading

    fun loadAreas() {

        viewModelScope.launch {

            _isLoading.value = true

            val result =
                repository.getAreas()

            if (result.isSuccess) {

                _areas.value =
                    result.getOrNull()
                        ?: emptyList()

                Log.d(
                    "AREA_TEST",
                    "CUSTOMER AREAS = ${_areas.value}"
                )

            } else {

                Log.e(
                    "AREA_TEST",
                    "CUSTOMER AREA ERROR = ${
                        result.exceptionOrNull()?.message
                    }"
                )

            }

            _isLoading.value = false

        }

    }

}