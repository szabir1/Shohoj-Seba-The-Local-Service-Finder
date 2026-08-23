package com.example.shohojseba.data.repository

import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.model.Customer
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.Review
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

import kotlinx.serialization.Serializable


class AdminRepository {


    // =====================================================
    // CATEGORY REQUEST MODELS
    // =====================================================

    @Serializable
    private data class CategoryInsert(
        val category_name: String
    )


    @Serializable
    private data class CategoryUpdate(
        val category_name: String
    )


    // =====================================================
    // AREA REQUEST MODELS
    // =====================================================

    @Serializable
    private data class AreaInsert(
        val area_name: String
    )


    @Serializable
    private data class AreaUpdate(
        val area_name: String
    )


    // =====================================================
    // PROVIDER STATUS REQUEST MODEL
    // =====================================================

    @Serializable
    private data class ProviderStatusUpdate(
        val account_status: String
    )


    // =====================================================
    // PROVIDER VERIFICATION REQUEST MODEL
    // =====================================================

    @Serializable
    private data class ProviderVerificationUpdate(
        val is_verified: Boolean
    )


    // =====================================================
    // SERVICE STATUS REQUEST MODEL
    // =====================================================

    @Serializable
    private data class ServiceStatusUpdate(
        val service_status: String
    )


    // =====================================================
    // GET CATEGORIES
    // =====================================================

    suspend fun getCategories(): Result<List<Category>> {

        return try {

            val categories =
                supabase
                    .from("category")
                    .select()
                    .decodeList<Category>()

            Result.success(categories)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // ADD CATEGORY
    // =====================================================

    suspend fun addCategory(
        categoryName: String
    ): Result<Unit> {

        return try {

            supabase
                .from("category")
                .insert(
                    CategoryInsert(
                        category_name = categoryName
                    )
                )

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // UPDATE CATEGORY
    // =====================================================

    suspend fun updateCategory(
        categoryId: Long,
        categoryName: String
    ): Result<Unit> {

        return try {

            supabase
                .from("category")
                .update(
                    CategoryUpdate(
                        category_name = categoryName
                    )
                ) {

                    filter {

                        eq(
                            "category_id",
                            categoryId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // DELETE CATEGORY
    // =====================================================

    suspend fun deleteCategory(
        categoryId: Long
    ): Result<Unit> {

        return try {

            supabase
                .from("category")
                .delete {

                    filter {

                        eq(
                            "category_id",
                            categoryId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // GET AREAS
    // =====================================================

    suspend fun getAreas(): Result<List<Area>> {

        return try {

            val areas =
                supabase
                    .from("area")
                    .select()
                    .decodeList<Area>()

            Result.success(areas)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // ADD AREA
    // =====================================================

    suspend fun addArea(
        areaName: String
    ): Result<Unit> {

        return try {

            supabase
                .from("area")
                .insert(
                    AreaInsert(
                        area_name = areaName
                    )
                )

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // UPDATE AREA
    // =====================================================

    suspend fun updateArea(
        areaId: Long,
        areaName: String
    ): Result<Unit> {

        return try {

            supabase
                .from("area")
                .update(
                    AreaUpdate(
                        area_name = areaName
                    )
                ) {

                    filter {

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


    // =====================================================
    // DELETE AREA
    // =====================================================

    suspend fun deleteArea(
        areaId: Long
    ): Result<Unit> {

        return try {

            supabase
                .from("area")
                .delete {

                    filter {

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


    // =====================================================
    // GET CUSTOMERS
    // =====================================================

    suspend fun getCustomers(): Result<List<Customer>> {

        return try {

            val customers =
                supabase
                    .from("Customer")
                    .select()
                    .decodeList<Customer>()

            Result.success(customers)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // GET PROVIDERS
    // =====================================================

    suspend fun getProviders(): Result<List<Provider>> {

        return try {

            val providers =
                supabase
                    .from("Provider")
                    .select()
                    .decodeList<Provider>()

            Result.success(providers)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // UPDATE PROVIDER STATUS
    // =====================================================

    suspend fun updateProviderStatus(
        providerId: Long,
        status: String
    ): Result<Unit> {

        return try {

            supabase
                .from("Provider")
                .update(
                    ProviderStatusUpdate(
                        account_status = status
                    )
                ) {

                    filter {

                        eq(
                            "provider_id",
                            providerId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // UPDATE PROVIDER VERIFICATION
    // =====================================================

    suspend fun updateProviderVerification(
        providerId: Long,
        verified: Boolean
    ): Result<Unit> {

        return try {

            supabase
                .from("Provider")
                .update(
                    ProviderVerificationUpdate(
                        is_verified = verified
                    )
                ) {

                    filter {

                        eq(
                            "provider_id",
                            providerId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // GET SERVICES
    // =====================================================

    suspend fun getServices(): Result<List<Service>> {

        return try {

            val services =
                supabase
                    .from("Service")
                    .select()
                    .decodeList<Service>()

            Result.success(services)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // UPDATE SERVICE STATUS
    // =====================================================

    suspend fun updateServiceStatus(
        serviceId: Long,
        status: String
    ): Result<Unit> {

        return try {

            supabase
                .from("Service")
                .update(
                    ServiceStatusUpdate(
                        service_status = status
                    )
                ) {

                    filter {

                        eq(
                            "service_id",
                            serviceId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // GET REVIEWS
    // =====================================================

    suspend fun getReviews(): Result<List<Review>> {

        return try {

            val reviews =
                supabase
                    .from("review")
                    .select()
                    .decodeList<Review>()

            Result.success(reviews)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    // =====================================================
    // DELETE REVIEW
    // =====================================================

    suspend fun deleteReview(
        reviewId: Long
    ): Result<Unit> {

        return try {

            supabase
                .from("review")
                .delete {

                    filter {

                        eq(
                            "review_id",
                            reviewId
                        )

                    }
                }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}