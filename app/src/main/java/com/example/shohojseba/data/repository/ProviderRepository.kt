package com.example.shohojseba.data.repository

import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.ProviderArea
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class ProviderRepository {

    // =====================================================
    // CURRENT PROVIDER
    // =====================================================

    suspend fun getCurrentProvider(): Result<Provider> {

        return try {

            val userId =
                supabase.auth.currentUserOrNull()?.id
                    ?: throw Exception("User not logged in")

            val provider = supabase
                .from("Provider")
                .select {

                    filter {

                        eq(
                            "auth_user_id",
                            userId
                        )

                    }

                }
                .decodeList<Provider>()
                .firstOrNull()
                ?: throw Exception("Provider profile not found")

            Result.success(provider)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // PROVIDER SERVICES
    // =====================================================

    suspend fun getProviderServices(
        providerId: Long
    ): Result<List<Service>> {

        return try {

            val services = supabase
                .from("Service")
                .select {

                    filter {

                        eq(
                            "provider_id",
                            providerId
                        )

                    }

                }
                .decodeList<Service>()

            Result.success(services)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // CATEGORIES
    // =====================================================

    suspend fun getCategories(): Result<List<Category>> {

        return try {

            val categories = supabase
                .from("category")
                .select()
                .decodeList<Category>()

            Result.success(categories)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // ADD SERVICE
    // =====================================================

    suspend fun addService(
        service: AddServiceRequest
    ): Result<Unit> {

        return try {

            supabase
                .from("Service")
                .insert(service)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // GET ALL AREAS
    // =====================================================

    suspend fun getAreas(): Result<List<Area>> {

        return try {

            val areas = supabase
                .from("area")
                .select()
                .decodeList<Area>()

            Result.success(areas)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // GET PROVIDER'S CURRENT AREAS
    // =====================================================

    suspend fun getProviderAreas(
        providerId: Long
    ): Result<List<ProviderArea>> {

        return try {

            val areas = supabase
                .from("provider_area")
                .select {

                    filter {

                        eq(
                            "provider_id",
                            providerId
                        )

                    }

                }
                .decodeList<ProviderArea>()

            Result.success(areas)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // ADD PROVIDER AREA
    // =====================================================

    suspend fun addProviderArea(
        providerId: Long,
        areaId: Long
    ): Result<Unit> {

        return try {

            val providerArea =
                ProviderArea(

                    provider_id =
                        providerId,

                    area_id =
                        areaId

                )

            supabase
                .from("provider_area")
                .insert(providerArea)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    // =====================================================
    // REMOVE PROVIDER AREA
    // =====================================================

    suspend fun removeProviderArea(
        providerId: Long,
        areaId: Long
    ): Result<Unit> {

        return try {

            supabase
                .from("provider_area")
                .delete {

                    filter {

                        eq(
                            "provider_id",
                            providerId
                        )

                        eq(
                            "area_id",
                            areaId
                        )

                    }

                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}