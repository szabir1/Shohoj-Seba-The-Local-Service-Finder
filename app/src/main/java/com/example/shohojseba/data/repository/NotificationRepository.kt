package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.AppNotification
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

import kotlinx.serialization.Serializable


class NotificationRepository {


    // =====================================================
    // INSERT MODEL
    // =====================================================

    @Serializable
    private data class NotificationInsert(

        val customer_id: Long,

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
    // CREATE NOTIFICATION
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
                NotificationInsert(

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
                "Notification created = $notification"
            )


            Result.success(Unit)


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "CREATE ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // GET CURRENT CUSTOMER NOTIFICATIONS
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
                "Notifications = $notifications"
            )


            Result.success(
                notifications
            )


        } catch (e: Exception) {


            Log.e(
                "NOTIFICATION_TEST",
                "LOAD ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // MARK ONE AS READ
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
    // MARK ALL AS READ
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
                "MARK ALL READ ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }

}