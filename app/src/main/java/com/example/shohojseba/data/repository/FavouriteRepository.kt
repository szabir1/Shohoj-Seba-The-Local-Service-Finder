package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Favorite
import com.example.shohojseba.data.model.ServiceDetails
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

import kotlinx.serialization.Serializable


class FavoriteRepository {


    // =====================================================
    // INSERT MODEL
    // =====================================================

    @Serializable
    private data class FavoriteInsert(

        val customer_id: Long,

        val service_id: Long

    )


    // =====================================================
    // GET CUSTOMER FAVORITES
    // =====================================================

    suspend fun getFavorites():
            Result<List<Favorite>> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            val favorites =
                supabase
                    .from("favorite")
                    .select {

                        filter {

                            eq(
                                "customer_id",
                                customerId
                            )

                        }

                    }
                    .decodeList<Favorite>()


            Log.d(
                "FAVORITE_TEST",
                "Favorites = $favorites"
            )


            Result.success(
                favorites
            )


        } catch (e: Exception) {


            Log.e(
                "FAVORITE_TEST",
                "GET FAVORITES ERROR = ${e.message}",
                e
            )


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // GET FAVORITE SERVICE IDS
    // =====================================================

    suspend fun getFavoriteServiceIds():
            Result<Set<Long>> {

        return try {


            val result =
                getFavorites()


            if (
                result.isFailure
            ) {

                return Result.failure(

                    result.exceptionOrNull()
                        ?: Exception(
                            "Unable to load favorites"
                        )

                )

            }


            val ids =
                result
                    .getOrDefault(
                        emptyList()
                    )
                    .map {

                        it.service_id

                    }
                    .toSet()


            Result.success(
                ids
            )


        } catch (e: Exception) {


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // CHECK FAVORITE
    // =====================================================

    suspend fun isFavorite(
        serviceId: Long
    ): Boolean {

        return try {


            val customerId =
                UserSession.customerId
                    ?: return false


            val result =
                supabase
                    .from("favorite")
                    .select {

                        filter {

                            eq(
                                "customer_id",
                                customerId
                            )

                            eq(
                                "service_id",
                                serviceId
                            )

                        }

                    }
                    .decodeList<Favorite>()


            result.isNotEmpty()


        } catch (e: Exception) {


            Log.e(
                "FAVORITE_TEST",
                "CHECK FAVORITE ERROR = ${e.message}",
                e
            )


            false

        }

    }


    // =====================================================
    // ADD FAVORITE
    // =====================================================

    suspend fun addFavorite(
        serviceId: Long
    ): Result<Unit> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            // Prevent duplicate favorite

            if (
                isFavorite(
                    serviceId
                )
            ) {

                return Result.success(
                    Unit
                )

            }


            val favorite =
                FavoriteInsert(

                    customer_id =
                        customerId,

                    service_id =
                        serviceId

                )


            supabase
                .from("favorite")
                .insert(
                    favorite
                )


            Log.d(
                "FAVORITE_TEST",
                "Favorite added: serviceId=$serviceId"
            )


            Result.success(
                Unit
            )


        } catch (e: Exception) {


            Log.e(
                "FAVORITE_TEST",
                "ADD FAVORITE ERROR = ${e.message}",
                e
            )


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // REMOVE FAVORITE
    // =====================================================

    suspend fun removeFavorite(
        serviceId: Long
    ): Result<Unit> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            supabase
                .from("favorite")
                .delete {

                    filter {

                        eq(
                            "customer_id",
                            customerId
                        )

                        eq(
                            "service_id",
                            serviceId
                        )

                    }

                }


            Log.d(
                "FAVORITE_TEST",
                "Favorite removed: serviceId=$serviceId"
            )


            Result.success(
                Unit
            )


        } catch (e: Exception) {


            Log.e(
                "FAVORITE_TEST",
                "REMOVE FAVORITE ERROR = ${e.message}",
                e
            )


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // GET FAVORITE SERVICE DETAILS
    // =====================================================

    suspend fun getFavoriteServices():
            Result<List<ServiceDetails>> {

        return try {


            val favoriteResult =
                getFavorites()


            if (
                favoriteResult.isFailure
            ) {

                return Result.failure(

                    favoriteResult
                        .exceptionOrNull()
                        ?: Exception(
                            "Unable to load favorites"
                        )

                )

            }


            val serviceIds =
                favoriteResult
                    .getOrDefault(
                        emptyList()
                    )
                    .map {

                        it.service_id

                    }
                    .distinct()


            if (
                serviceIds.isEmpty()
            ) {

                return Result.success(
                    emptyList()
                )

            }


            val services =
                supabase
                    .from("service_details")
                    .select {

                        filter {

                            isIn(
                                "service_id",
                                serviceIds
                            )

                        }

                    }
                    .decodeList<ServiceDetails>()


            Log.d(
                "FAVORITE_TEST",
                "Favorite services = $services"
            )


            Result.success(
                services
            )


        } catch (e: Exception) {


            Log.e(
                "FAVORITE_TEST",
                "FAVORITE SERVICES ERROR = ${e.message}",
                e
            )


            Result.failure(
                e
            )

        }

    }

}