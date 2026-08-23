package com.example.shohojseba


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat

import com.example.shohojseba.navigation.NavGraph
import com.example.shohojseba.notification.BookingNotificationHelper
import com.example.shohojseba.ui.theme.ShohojSebaTheme


class MainActivity : ComponentActivity() {


    // =====================================================
    // NOTIFICATION PERMISSION REQUEST
    // =====================================================

    private val notificationPermissionLauncher =

        registerForActivityResult(

            ActivityResultContracts
                .RequestPermission()

        ) { isGranted ->


            // Nothing else required here.
            // If granted, notifications can be displayed.

        }


    override fun onCreate(

        savedInstanceState: Bundle?

    ) {


        super.onCreate(
            savedInstanceState
        )


        // =================================================
        // CREATE NOTIFICATION CHANNEL
        // =================================================

        BookingNotificationHelper
            .createNotificationChannel(
                this
            )


        // =================================================
        // REQUEST NOTIFICATION PERMISSION
        // Android 13+
        // =================================================

        requestNotificationPermission()


        // =================================================
        // COMPOSE UI
        // =================================================

        setContent {


            ShohojSebaTheme {


                NavGraph()


            }


        }

    }


    // =====================================================
    // REQUEST PERMISSION
    // =====================================================

    private fun requestNotificationPermission() {


        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {


            val permissionStatus =

                ContextCompat
                    .checkSelfPermission(

                        this,

                        Manifest.permission
                            .POST_NOTIFICATIONS

                    )


            if (
                permissionStatus !=
                PackageManager
                    .PERMISSION_GRANTED
            ) {


                notificationPermissionLauncher
                    .launch(

                        Manifest.permission
                            .POST_NOTIFICATIONS

                    )

            }

        }

    }

}