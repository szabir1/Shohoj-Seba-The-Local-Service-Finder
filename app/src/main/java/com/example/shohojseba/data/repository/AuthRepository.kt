package com.example.shohojseba.data.repository

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Admin
import com.example.shohojseba.data.model.Customer
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from


class AuthRepository {


    // =====================================================
    // REGISTER
    // =====================================================

    suspend fun register(

        role: String,

        name: String,

        phone: String,

        email: String,

        password: String,

        experience: Int = 0

    ): Result<Unit> {

        return try {


            // -------------------------------------------------
            // CREATE SUPABASE AUTH ACCOUNT
            // -------------------------------------------------

            supabase.auth.signUpWith(Email) {

                this.email =
                    email

                this.password =
                    password

            }


            val userId =
                supabase.auth
                    .currentUserOrNull()
                    ?.id
                    ?: throw Exception(
                        "User ID not found"
                    )


            // =================================================
            // CUSTOMER
            // =================================================

            if (
                role == "CUSTOMER"
            ) {


                val customer =
                    Customer(

                        auth_user_id =
                            userId,

                        name =
                            name,

                        phone =
                            phone,

                        email =
                            email

                    )


                supabase
                    .from("Customer")
                    .insert(
                        customer
                    )

            }


            // =================================================
            // PROVIDER
            // =================================================

            else if (
                role == "PROVIDER"
            ) {


                val provider =
                    Provider(

                        auth_user_id =
                            userId,

                        name =
                            name,

                        phone =
                            phone,

                        email =
                            email,

                        experience =
                            experience,

                        account_status =
                            "ACTIVE"

                    )


                supabase
                    .from("Provider")
                    .insert(
                        provider
                    )

            }


            // =================================================
            // ADMIN CANNOT REGISTER THROUGH APP
            // =================================================

            else {


                throw Exception(
                    "Invalid role"
                )

            }


            Result.success(
                Unit
            )


        } catch (
            e: Exception
        ) {


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // LOGIN
    // =====================================================

    suspend fun login(

        email: String,

        password: String

    ): Result<String> {

        return try {


            // -------------------------------------------------
            // SUPABASE AUTHENTICATION
            // -------------------------------------------------

            supabase.auth.signInWith(Email) {

                this.email =
                    email

                this.password =
                    password

            }


            val userId =
                supabase.auth
                    .currentUserOrNull()
                    ?.id
                    ?: throw Exception(
                        "User ID not found"
                    )


            // =================================================
            // CUSTOMER CHECK
            // =================================================

            val customer =
                supabase
                    .from("Customer")
                    .select {

                        filter {

                            eq(
                                "auth_user_id",
                                userId
                            )

                        }

                    }
                    .decodeSingleOrNull<Customer>()


            if (
                customer != null
            ) {


                UserSession.customerId =
                    customer.customer_id


                UserSession.providerId =
                    null


                return Result.success(
                    "CUSTOMER"
                )

            }


            // =================================================
            // PROVIDER CHECK
            // =================================================

            val provider =
                supabase
                    .from("Provider")
                    .select {

                        filter {

                            eq(
                                "auth_user_id",
                                userId
                            )

                        }

                    }
                    .decodeSingleOrNull<Provider>()


            if (
                provider != null
            ) {


                when (
                    provider.account_status.uppercase()
                ) {


                    // -----------------------------------------
                    // ACTIVE PROVIDER
                    // -----------------------------------------

                    "ACTIVE" -> {


                        UserSession.providerId =
                            provider.provider_id


                        UserSession.customerId =
                            null


                        return Result.success(
                            "PROVIDER"
                        )

                    }


                    // -----------------------------------------
                    // SUSPENDED PROVIDER
                    // -----------------------------------------

                    "SUSPENDED" -> {


                        UserSession.providerId =
                            null


                        UserSession.customerId =
                            null


                        supabase.auth
                            .signOut()


                        return Result.failure(

                            Exception(
                                "Your provider account has been suspended. Please contact the administrator."
                            )

                        )

                    }


                    // -----------------------------------------
                    // REMOVED PROVIDER
                    // -----------------------------------------

                    "REMOVED" -> {


                        UserSession.providerId =
                            null


                        UserSession.customerId =
                            null


                        supabase.auth
                            .signOut()


                        return Result.failure(

                            Exception(
                                "This provider account is no longer active."
                            )

                        )

                    }


                    // -----------------------------------------
                    // UNKNOWN STATUS
                    // -----------------------------------------

                    else -> {


                        UserSession.providerId =
                            null


                        UserSession.customerId =
                            null


                        supabase.auth
                            .signOut()


                        return Result.failure(

                            Exception(
                                "Provider account status is invalid. Please contact the administrator."
                            )

                        )

                    }

                }

            }


            // =================================================
            // ADMIN CHECK
            // =================================================

            val admin =
                supabase
                    .from("Admin")
                    .select {

                        filter {

                            eq(
                                "auth_user_id",
                                userId
                            )

                        }

                    }
                    .decodeSingleOrNull<Admin>()


            if (
                admin != null
            ) {


                UserSession.customerId =
                    null


                UserSession.providerId =
                    null


                return Result.success(
                    "ADMIN"
                )

            }


            // =================================================
            // NO PROFILE FOUND
            // =================================================

            supabase.auth
                .signOut()


            UserSession.customerId =
                null


            UserSession.providerId =
                null


            Result.failure(

                Exception(
                    "Profile not found"
                )

            )


        } catch (
            e: Exception
        ) {


            Result.failure(
                e
            )

        }

    }


    // =====================================================
    // LOGOUT
    // =====================================================

    suspend fun logout():
            Result<Unit> {

        return try {


            // -------------------------------------------------
            // SIGN OUT FROM SUPABASE
            // -------------------------------------------------

            supabase.auth
                .signOut()


            // -------------------------------------------------
            // CLEAR LOCAL SESSION
            // -------------------------------------------------

            UserSession.customerId =
                null


            UserSession.providerId =
                null


            Result.success(
                Unit
            )


        } catch (
            e: Exception
        ) {


            // Even if remote logout fails,
            // clear local IDs for safety

            UserSession.customerId =
                null


            UserSession.providerId =
                null


            Result.failure(
                e
            )

        }

    }

}