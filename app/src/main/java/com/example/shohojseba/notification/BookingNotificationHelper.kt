package com.example.shohojseba.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

import com.example.shohojseba.R
import com.example.shohojseba.data.model.Booking


object BookingNotificationHelper {


    // =====================================================
    // CHANNEL
    // =====================================================

    private const val CHANNEL_ID =
        "booking_status_channel"


    private const val CHANNEL_NAME =
        "Booking Updates"


    private const val CHANNEL_DESCRIPTION =
        "Notifications about changes to your service bookings"


    // =====================================================
    // SHARED PREFERENCES
    // =====================================================

    private const val PREF_NAME =
        "booking_notification_preferences"


    // =====================================================
    // CREATE NOTIFICATION CHANNEL
    // =====================================================

    fun createNotificationChannel(

        context: Context

    ) {


        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {


            val channel =
                NotificationChannel(

                    CHANNEL_ID,

                    CHANNEL_NAME,

                    NotificationManager.IMPORTANCE_HIGH

                ).apply {

                    description =
                        CHANNEL_DESCRIPTION

                }


            val notificationManager =

                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager


            notificationManager
                .createNotificationChannel(
                    channel
                )

        }

    }


    // =====================================================
    // CHECK CUSTOMER BOOKING STATUS CHANGES
    // =====================================================

    fun checkBookingStatusChanges(

        context: Context,

        bookings: List<Booking>

    ) {


        val preferences =

            context.getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )


        val editor =
            preferences.edit()


        bookings.forEach { booking ->


            val key =
                "booking_status_${booking.bookingId}"


            val previousStatus =
                preferences.getString(
                    key,
                    null
                )


            val currentStatus =
                booking.status


            // =================================================
            // FIRST TIME SEEING BOOKING
            // =================================================

            if (
                previousStatus == null
            ) {


                // Save current status as baseline.
                // Do NOT send notification.

                editor.putString(

                    key,

                    currentStatus

                )


                return@forEach

            }


            // =================================================
            // STATUS CHANGED TO ACCEPTED
            // =================================================

            if (
                !previousStatus.equals(
                    currentStatus,
                    ignoreCase = true
                ) &&
                currentStatus.equals(
                    "Accepted",
                    ignoreCase = true
                )
            ) {


                showBookingAcceptedNotification(

                    context =
                        context,

                    booking =
                        booking

                )

            }


            // =================================================
            // ALWAYS SAVE LATEST STATUS
            // =================================================

            editor.putString(

                key,

                currentStatus

            )

        }


        editor.apply()

    }


    // =====================================================
    // ACCEPTED NOTIFICATION
    // =====================================================

    private fun showBookingAcceptedNotification(

        context: Context,

        booking: Booking

    ) {


        // Android 13+
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {


            val permissionGranted =

                ContextCompat.checkSelfPermission(

                    context,

                    Manifest.permission.POST_NOTIFICATIONS

                ) ==
                        PackageManager.PERMISSION_GRANTED


            if (
                !permissionGranted
            ) {

                return

            }

        }


        val serviceName =

            booking.service
                ?.serviceName
                ?: "service"


        val providerName =

            booking.provider
                ?.name
                ?: "your provider"


        val notification =

            NotificationCompat
                .Builder(
                    context,
                    CHANNEL_ID
                )
                .setSmallIcon(
                    R.mipmap.ic_launcher
                )
                .setContentTitle(
                    "Booking Accepted"
                )
                .setContentText(
                    "Your $serviceName booking has been accepted by $providerName."
                )
                .setStyle(

                    NotificationCompat
                        .BigTextStyle()
                        .bigText(

                            "Good news! Your $serviceName booking has been accepted by $providerName."

                        )

                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(
                    true
                )
                .build()


        NotificationManagerCompat
            .from(
                context
            )
            .notify(

                booking.bookingId
                    .toInt(),

                notification

            )

    }

}