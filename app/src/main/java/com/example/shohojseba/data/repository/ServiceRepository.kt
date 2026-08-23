package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.api.RetrofitClient
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.ProviderArea
import com.example.shohojseba.data.model.ServiceDetails
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from


class ServiceRepository {


    private val api =
        RetrofitClient.serviceApi


    // =====================================================
    // GET PROVIDERS
    // =====================================================

    private suspend fun getProviders():
            List<Provider> {

        return try {

            val providers =
                supabase
                    .from("Provider")
                    .select()
                    .decodeList<Provider>()


            Log.d(
                "SERVICE_TEST",
                "ALL PROVIDERS = $providers"
            )


            providers

        } catch (e: Exception) {

            Log.e(
                "SERVICE_TEST",
                "PROVIDER LOAD ERROR = ${e.message}",
                e
            )


            emptyList()

        }

    }


    // =====================================================
    // ATTACH PROVIDER INFORMATION
    // =====================================================

    private suspend fun attachProviderInformation(

        services: List<ServiceDetails>

    ): List<ServiceDetails> {


        Log.d(
            "SERVICE_TEST",
            "PROVIDER INFO INPUT = $services"
        )


        val providers =
            getProviders()


        val updatedServices =
            services.map { service ->


                val provider =
                    providers
                        .firstOrNull {

                            it.provider_id ==
                                    service.provider_id

                        }


                Log.d(
                    "SERVICE_TEST",
                    "MATCH provider_id=${service.provider_id} -> $provider"
                )


                service.copy(

                    is_verified =
                        provider
                            ?.is_verified
                            ?: false,

                    availability_status =
                        provider
                            ?.availability_status
                            ?: "AVAILABLE"

                )

            }


        Log.d(
            "SERVICE_TEST",
            "PROVIDER INFO OUTPUT = $updatedServices"
        )


        return updatedServices

    }


    // =====================================================
    // CATEGORY ONLY
    // =====================================================

    suspend fun getServicesByCategory(

        categoryId: Long

    ): List<ServiceDetails> {

        return try {


            Log.d(
                "SERVICE_TEST",
                "CATEGORY SEARCH categoryId=$categoryId"
            )


            val result =
                api
                    .getServicesByCategory(

                        categoryFilter =
                            "eq.$categoryId"

                    )


            Log.d(
                "SERVICE_TEST",
                "CATEGORY API RESULT = $result"
            )


            val finalResult =
                attachProviderInformation(
                    result
                )


            Log.d(
                "SERVICE_TEST",
                "CATEGORY FINAL RESULT = $finalResult"
            )


            finalResult


        } catch (e: Exception) {


            Log.e(
                "SERVICE_TEST",
                "CATEGORY ERROR = ${e.message}",
                e
            )


            emptyList()

        }

    }


    // =====================================================
    // CATEGORY + AREA
    // =====================================================

    suspend fun getServicesByCategoryAndArea(

        categoryId: Long,

        areaId: Long

    ): List<ServiceDetails> {

        return try {


            Log.d(
                "AREA_DEBUG",
                "======================================"
            )


            Log.d(
                "AREA_DEBUG",
                "SEARCH START categoryId=$categoryId areaId=$areaId"
            )


            // =================================================
            // STEP 1
            // FIND PROVIDERS WHO SERVE THE AREA
            // =================================================

            val providerAreas =
                supabase
                    .from("provider_area")
                    .select {

                        filter {

                            eq(
                                "area_id",
                                areaId
                            )

                        }

                    }
                    .decodeList<ProviderArea>()


            Log.d(
                "AREA_DEBUG",
                "PROVIDER AREAS = $providerAreas"
            )


            // =================================================
            // STEP 2
            // EXTRACT PROVIDER IDS
            // =================================================

            val providerIds =
                providerAreas
                    .map {

                        it.provider_id

                    }
                    .distinct()


            Log.d(
                "AREA_DEBUG",
                "PROVIDER IDS = $providerIds"
            )


            // =================================================
            // STEP 3
            // NO PROVIDERS IN AREA
            // =================================================

            if (
                providerIds.isEmpty()
            ) {

                Log.d(
                    "AREA_DEBUG",
                    "NO PROVIDERS FOUND FOR areaId=$areaId"
                )


                return emptyList()

            }


            // =================================================
            // STEP 4
            // CREATE POSTGREST PROVIDER FILTER
            // =================================================

            val providerFilter =
                "in.(${
                    providerIds.joinToString(
                        ","
                    )
                })"


            Log.d(
                "AREA_DEBUG",
                "CATEGORY FILTER = eq.$categoryId"
            )


            Log.d(
                "AREA_DEBUG",
                "PROVIDER FILTER = $providerFilter"
            )


            // =================================================
            // STEP 5
            // GET MATCHING CATEGORY + PROVIDERS
            // =================================================

            val result =
                api
                    .getServicesByCategoryAndProviders(

                        categoryFilter =
                            "eq.$categoryId",

                        providerFilter =
                            providerFilter

                    )


            Log.d(
                "AREA_DEBUG",
                "SERVICE DETAILS API RESULT = $result"
            )


            // =================================================
            // STEP 6
            // ADD VERIFIED + AVAILABILITY INFO
            // =================================================

            val finalResult =
                attachProviderInformation(
                    result
                )


            Log.d(
                "AREA_DEBUG",
                "FINAL RESULT = $finalResult"
            )


            Log.d(
                "AREA_DEBUG",
                "======================================"
            )


            finalResult


        } catch (e: Exception) {


            Log.e(
                "AREA_DEBUG",
                "AREA FILTER ERROR = ${e.message}",
                e
            )


            emptyList()

        }

    }

}