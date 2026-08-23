package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.api.RetrofitClient
import com.example.shohojseba.data.model.ProviderArea
import com.example.shohojseba.data.model.ServiceDetails
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

class ServiceRepository {

    private val api =
        RetrofitClient.serviceApi


    // =====================================================
    // OLD CATEGORY-ONLY VERSION
    // =====================================================

    suspend fun getServicesByCategory(
        categoryId: Long
    ): List<ServiceDetails> {

        return try {

            api.getServicesByCategory(
                "eq.$categoryId"
            )

        } catch (e: Exception) {

            Log.e(
                "SERVICE_TEST",
                "CATEGORY ERROR = ${e.message}"
            )

            emptyList()

        }

    }


    // =====================================================
    // AREA + CATEGORY FILTER
    // =====================================================

    suspend fun getServicesByCategoryAndArea(

        categoryId: Long,

        areaId: Long

    ): List<ServiceDetails> {

        return try {

            Log.d(
                "SERVICE_TEST",
                "Loading category=$categoryId area=$areaId"
            )


            // ---------------------------------------------
            // Find providers serving selected area
            // ---------------------------------------------

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


            val providerIds =
                providerAreas
                    .map {
                        it.provider_id
                    }
                    .distinct()


            Log.d(
                "SERVICE_TEST",
                "AREA PROVIDERS = $providerIds"
            )


            if (providerIds.isEmpty()) {

                Log.d(
                    "SERVICE_TEST",
                    "No providers serve area=$areaId"
                )

                return emptyList()

            }


            // ---------------------------------------------
            // PostgREST IN filter
            // ---------------------------------------------

            val providerFilter =

                "in.(${
                    providerIds.joinToString(
                        ","
                    )
                })"


            val result =
                api.getServicesByCategoryAndProviders(

                    categoryFilter =
                        "eq.$categoryId",

                    providerFilter =
                        providerFilter

                )


            Log.d(
                "SERVICE_TEST",
                "FILTERED SERVICES = $result"
            )


            result

        } catch (e: Exception) {

            Log.e(
                "SERVICE_TEST",
                "AREA FILTER ERROR = ${e.message}"
            )

            emptyList()

        }

    }

}