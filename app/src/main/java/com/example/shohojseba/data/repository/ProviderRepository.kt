package com.example.shohojseba.data.repository


import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from



class ProviderRepository {



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




}