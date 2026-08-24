package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.AppNotification
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

import kotlinx.serialization.Serializable


class NotificationRepository {


    // =====================================================
    // CUSTOMER INSERT MODEL
    // =====================================================

    @Serializable
    private data class CustomerNotificationInsert(

        val customer_id: Long,

        val provider_id: Long? = null,

        val booking_id: Long?,

        val title: String,

        val message: String,

        val notification_type: String,

        val is_read: Boolean = false

    )


    // =====================================================
    // PROVIDER INSERT MODEL
    // =====================================================

    @Serializable
    private data class ProviderNotificationInsert(

        val customer_id: Long? = null,

        val provider_id: Long,

        val booking_id: Long?,

        val title: String,

        val message: String,

        val notification_type: String,

        val is_read: Boolean = false

    )


    // =====================================================
    // READ UPDATE MODEL
    // =====================================================

    @Serializable
    private data class NotificationReadUpdate(

        val is_read: Boolean

    )


    // =====================================================
    // CREATE CUSTOMER NOTIFICATION
    // EXISTING FUNCTION - KEEP WORKING
    // =====================================================

    suspend fun createNotification(

        customerId: Long,

        bookingId: Long?,

        title: String,

        message: String,

        type: String

    ): Result<Unit> {

        return try {


            val notification =
                CustomerNotificationInsert(

                    customer_id =
                        customerId,

                    booking_id =
                        bookingId,

                    title =
                        title,

                    message =
                        message,

                    notification_type =
                        type,

                    is_read =
                        false

                )


            supabase
                .from("notification")
                .insert(
                    notification
                )


            Log.d(
                "NOTIFICATION_TEST",
                "Customer notification created = $notification"
            )


            Result.success(Unit)


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "CUSTOMER CREATE ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // CREATE PROVIDER NOTIFICATION
    // =====================================================

    suspend fun createProviderNotification(

        providerId: Long,

        bookingId: Long?,

        title: String,

        message: String,

        type: String

    ): Result<Unit> {

        return try {


            val notification =
                ProviderNotificationInsert(

                    provider_id =
                        providerId,

                    booking_id =
                        bookingId,

                    title =
                        title,

                    message =
                        message,

                    notification_type =
                        type,

                    is_read =
                        false

                )


            supabase
                .from("notification")
                .insert(
                    notification
                )


            Log.d(
                "PROVIDER_NOTIFICATION_TEST",
                "Provider notification created = $notification"
            )


            Result.success(Unit)


        } catch (e: Exception) {


            Log.e(
                "PROVIDER_NOTIFICATION_TEST",
                "PROVIDER CREATE ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // GET CUSTOMER NOTIFICATIONS
    // =====================================================

    suspend fun getCustomerNotifications():
            Result<List<AppNotification>> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            val notifications =
                supabase
                    .from("notification")
                    .select {

                        filter {

                            eq(
                                "customer_id",
                                customerId
                            )

                        }

                    }
                    .decodeList<AppNotification>()
                    .sortedByDescending {

                        it.created_at ?: ""

                    }


            Log.d(
                "NOTIFICATION_TEST",
                "Customer notifications = $notifications"
            )


            Result.success(
                notifications
            )


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "CUSTOMER LOAD ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // GET PROVIDER NOTIFICATIONS
    // =====================================================

    suspend fun getProviderNotifications():
            Result<List<AppNotification>> {

        return try {


            val providerId =
                UserSession.providerId
                    ?: throw Exception(
                        "Provider not logged in"
                    )


            val notifications =
                supabase
                    .from("notification")
                    .select {

                        filter {

                            eq(
                                "provider_id",
                                providerId
                            )

                        }

                    }
                    .decodeList<AppNotification>()
                    .sortedByDescending {

                        it.created_at ?: ""

                    }


            Log.d(
                "PROVIDER_NOTIFICATION_TEST",
                "Provider notifications = $notifications"
            )


            Result.success(
                notifications
            )


        } catch (e: Exception) {


            Log.e(
                "PROVIDER_NOTIFICATION_TEST",
                "PROVIDER LOAD ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // MARK ONE AS READ
    // WORKS FOR CUSTOMER OR PROVIDER
    // =====================================================

    suspend fun markAsRead(

        notificationId: Long

    ): Result<Unit> {

        return try {


            supabase
                .from("notification")
                .update(

                    NotificationReadUpdate(
                        is_read = true
                    )

                ) {

                    filter {

                        eq(
                            "notification_id",
                            notificationId
                        )

                    }

                }


            Result.success(Unit)


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "MARK READ ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // MARK ALL CUSTOMER NOTIFICATIONS READ
    // =====================================================

    suspend fun markAllAsRead():
            Result<Unit> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            supabase
                .from("notification")
                .update(

                    NotificationReadUpdate(
                        is_read = true
                    )

                ) {

                    filter {

                        eq(
                            "customer_id",
                            customerId
                        )

                    }

                }


            Result.success(Unit)


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "MARK CUSTOMER ALL READ ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // MARK ALL PROVIDER NOTIFICATIONS READ
    // =====================================================

    suspend fun markAllProviderAsRead():
            Result<Unit> {

        return try {


            val providerId =
                UserSession.providerId
                    ?: throw Exception(
                        "Provider not logged in"
                    )


            supabase
                .from("notification")
                .update(

                    NotificationReadUpdate(
                        is_read = true
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


            Log.e(
                "PROVIDER_NOTIFICATION_TEST",
                "MARK PROVIDER ALL READ ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }

}