package com.example.shohojseba.ui.customer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
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
fun CustomerBottomNavBar(

    currentRoute: String?,

    unreadNotificationCount: Int = 0,

    onHomeClick: () -> Unit,

    onBookingsClick: () -> Unit,

    onSavedClick: () -> Unit,

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
                currentRoute == "home",

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
        // BOOKINGS
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "customer_bookings",

            onClick =
                onBookingsClick,

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Book,

                    contentDescription =
                        "Bookings"

                )

            },

            label = {

                Text(
                    "Bookings"
                )

            }

        )


        // =====================================================
        // SAVED
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "favorites",

            onClick =
                onSavedClick,

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Favorite,

                    contentDescription =
                        "Saved"

                )

            },

            label = {

                Text(
                    "Saved"
                )

            }

        )


        // =====================================================
        // ALERTS
        // =====================================================

        NavigationBarItem(

            selected =
                currentRoute == "notifications",

            onClick =
                onAlertsClick,

            icon = {

                Box {


                    Icon(

                        imageVector =
                            Icons.Default.Notifications,

                        contentDescription =
                            "Alerts"

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

                                        x =
                                            7.dp,

                                        y =
                                            (-5).dp

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