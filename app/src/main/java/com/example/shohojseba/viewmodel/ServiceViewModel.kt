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
        MutableStateFlow<List<ServiceDetails>>(emptyList())


    val services: StateFlow<List<ServiceDetails>> =
        _services




    fun loadServicesByCategory(
        categoryId: Long
    ) {


        viewModelScope.launch {


            try {


                Log.d(
                    "SERVICE_TEST",
                    "Loading category services ID = $categoryId"
                )



                val result =
                    repository.getServicesByCategory(categoryId)



                Log.d(
                    "SERVICE_TEST",
                    "VIEWMODEL RECEIVED = $result"
                )



                _services.value = result



            } catch (e: Exception) {



                Log.e(
                    "SERVICE_TEST",
                    "VIEWMODEL ERROR = ${e.message}"
                )


                _services.value = emptyList()


            }


        }


    }


}