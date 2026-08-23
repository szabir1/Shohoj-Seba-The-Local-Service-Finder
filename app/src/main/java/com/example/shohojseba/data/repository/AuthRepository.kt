package com.example.shohojseba.data.repository

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Customer
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

class AuthRepository {

    suspend fun register(
        role: String,
        name: String,
        phone: String,
        email: String,
        password: String,
        experience: Int = 0
    ): Result<Unit> {

        return try {

            // Create Supabase Auth account
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val userId =
                supabase.auth.currentUserOrNull()?.id
                    ?: throw Exception("User ID not found")

            if (role == "CUSTOMER") {

                val customer = Customer(
                    auth_user_id = userId,
                    name = name,
                    phone = phone,
                    email = email
                )

                supabase
                    .from("Customer")
                    .insert(customer)

            } else if (role == "PROVIDER") {

                val provider = Provider(
                    auth_user_id = userId,
                    name = name,
                    phone = phone,
                    email = email,
                    experience = experience
                )

                supabase
                    .from("Provider")
                    .insert(provider)

            } else {

                throw Exception("Invalid role")

            }

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {

        return try {

            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val userId =
                supabase.auth.currentUserOrNull()?.id
                    ?: throw Exception("User ID not found")

            // CUSTOMER

            val customer = supabase
                .from("Customer")
                .select {
                    filter {
                        eq("auth_user_id", userId)
                    }
                }
                .decodeSingleOrNull<Customer>()

            if (customer != null) {

                UserSession.customerId = customer.customer_id
                UserSession.providerId = null

                return Result.success("CUSTOMER")
            }

            // PROVIDER

            val provider = supabase
                .from("Provider")
                .select {
                    filter {
                        eq("auth_user_id", userId)
                    }
                }
                .decodeSingleOrNull<Provider>()

            if (provider != null) {

                UserSession.providerId = provider.provider_id
                UserSession.customerId = null

                return Result.success("PROVIDER")
            }

            Result.failure(Exception("Profile not found"))

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun logout() {

        supabase.auth.signOut()

        UserSession.customerId = null
        UserSession.providerId = null

    }

}