package com.example.shohojseba.viewmodel


import android.util.Log

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.repository.ProviderRepository

import kotlinx.coroutines.launch



class ProviderViewModel : ViewModel() {



    private val repository =
        ProviderRepository()





    private val _provider =
        mutableStateOf<Provider?>(null)


    val provider: State<Provider?> =
        _provider






    private val _services =
        mutableStateOf<List<Service>>(emptyList())


    val services: State<List<Service>> =
        _services






    private val _message =
        mutableStateOf("")


    val message: State<String> =
        _message






    private val _isLoading =
        mutableStateOf(false)


    val isLoading: State<Boolean> =
        _isLoading







    fun loadProviderProfile() {



        viewModelScope.launch {



            _isLoading.value = true



            val result =
                repository.getCurrentProvider()



            if(result.isSuccess) {



                val providerData =
                    result.getOrNull()



                _provider.value =
                    providerData




                Log.d(
                    "PROVIDER_TEST",
                    "CURRENT PROVIDER = $providerData"
                )





                providerData?.provider_id?.let { providerId ->



                    Log.d(
                        "PROVIDER_TEST",
                        "PROVIDER ID = $providerId"
                    )



                    loadProviderServices(

                        providerId

                    )



                }



            } else {



                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load provider"



                Log.e(
                    "PROVIDER_TEST",
                    "PROFILE ERROR = ${result.exceptionOrNull()?.message}"
                )



            }





            _isLoading.value = false



        }


    }









    private fun loadProviderServices(

        providerId: Long

    ) {



        viewModelScope.launch {



            Log.d(
                "PROVIDER_TEST",
                "Loading services for provider_id = $providerId"
            )



            val result =
                repository.getProviderServices(

                    providerId

                )





            if(result.isSuccess) {



                val serviceList =
                    result.getOrNull()
                        ?: emptyList()



                Log.d(
                    "PROVIDER_TEST",
                    "SERVICE SIZE = ${serviceList.size}"
                )



                Log.d(
                    "PROVIDER_TEST",
                    "SERVICES = $serviceList"
                )



                _services.value =
                    serviceList




            } else {



                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load services"



                Log.e(
                    "PROVIDER_TEST",
                    "SERVICE ERROR = ${result.exceptionOrNull()?.message}"
                )



            }



        }


    }









    fun addService(

        service: AddServiceRequest

    ) {



        viewModelScope.launch {



            val result =
                repository.addService(service)





            if(result.isSuccess) {



                _message.value =
                    "Service added successfully"





                Log.d(
                    "PROVIDER_TEST",
                    "SERVICE INSERT SUCCESS"
                )





                _provider.value?.provider_id?.let { providerId ->



                    loadProviderServices(

                        providerId

                    )



                }





            } else {



                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to add service"



                Log.e(
                    "PROVIDER_TEST",
                    "INSERT ERROR = ${result.exceptionOrNull()?.message}"
                )



            }



        }



    }




}