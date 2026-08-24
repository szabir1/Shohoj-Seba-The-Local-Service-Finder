package com.example.shohojseba.ui.provider.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProviderBottomNavBar(

    currentRoute: String?,

    unreadNotificationCount: Int = 0,

    onHomeClick: () -> Unit,

    onBookingsClick: () -> Unit,

    onAddServiceClick: () -> Unit,

    onAlertsClick: () -> Unit

) {


    NavigationBar(

        containerColor =
            Color.White,

        tonalElevation =
            8.dp

    ) {


        // =====================================================
        // HOME
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "provider",

            onClick =
                onHomeClick,

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Home,

                    contentDescription =
                        "Home"

                )

            },

            label = {

                Text(
                    "Home"
                )

            }

        )


        // =====================================================
        // REQUESTS
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "provider_bookings",

            onClick =
                onBookingsClick,

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.CalendarMonth,

                    contentDescription =
                        "Booking Requests"

                )

            },

            label = {

                Text(
                    "Requests"
                )

            }

        )


        // =====================================================
        // ADD SERVICE
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "add_service",

            onClick =
                onAddServiceClick,

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.AddCircle,

                    contentDescription =
                        "Add Service"

                )

            },

            label = {

                Text(
                    "Add"
                )

            }

        )


        // =====================================================
        // ALERTS
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "provider_notifications",

            onClick =
                onAlertsClick,

            icon = {

                Box {


                    Icon(

                        imageVector =
                            Icons.Default.Notifications,

                        contentDescription =
                            "Provider Alerts"

                    )


                    if (
                        unreadNotificationCount > 0
                    ) {

                        Box(

                            modifier =
                                Modifier
                                    .align(
                                        Alignment.TopEnd
                                    )
                                    .offset(
                                        x = 7.dp,
                                        y = (-5).dp
                                    )
                                    .size(
                                        17.dp
                                    )
                                    .background(

                                        Color(
                                            0xFFD32F2F
                                        ),

                                        CircleShape

                                    ),

                            contentAlignment =
                                Alignment.Center

                        ) {


                            Text(

                                text =

                                    if (
                                        unreadNotificationCount > 9
                                    ) {

                                        "9+"

                                    } else {

                                        unreadNotificationCount
                                            .toString()

                                    },

                                color =
                                    Color.White,

                                fontSize =
                                    9.sp,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }

            },

            label = {

                Text(
                    "Alerts"
                )

            }

        )

    }

}