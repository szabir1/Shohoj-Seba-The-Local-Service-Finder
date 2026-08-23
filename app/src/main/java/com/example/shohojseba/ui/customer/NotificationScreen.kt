package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.AppNotification
import com.example.shohojseba.viewmodel.NotificationViewModel

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(

    viewModel: NotificationViewModel =
        viewModel()

) {


    val notifications by
    viewModel.notifications


    val isLoading by
    viewModel.isLoading


    // =====================================================
    // LOAD NOTIFICATIONS
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel
            .loadNotifications()

    }


    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Notifications"
                    )

                },

                actions = {

                    if (
                        notifications.any {

                            !it.is_read

                        }
                    ) {

                        TextButton(

                            onClick = {

                                viewModel
                                    .markAllAsRead()

                            }

                        ) {

                            Text(
                                "Mark all read"
                            )

                        }

                    }

                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                Color.Transparent

                        )

            )

        }

    ) { padding ->


        Box(

            modifier =
                Modifier
                    .fillMaxSize()
                    .background(

                        Brush.verticalGradient(

                            listOf(

                                Color(
                                    0xFFEFFFFB
                                ),

                                Color.White

                            )

                        )

                    )
                    .padding(
                        padding
                    )

        ) {


            when {


                // =================================================
                // LOADING
                // =================================================

                isLoading -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        CircularProgressIndicator(

                            color =
                                Color(
                                    0xFF007A7A
                                )

                        )

                    }

                }


                // =================================================
                // EMPTY
                // =================================================

                notifications.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    24.dp
                                ),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Card(

                            shape =
                                RoundedCornerShape(
                                    26.dp
                                ),

                            elevation =
                                CardDefaults
                                    .cardElevation(
                                        5.dp
                                    )

                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(
                                        30.dp
                                    ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.NotificationsNone,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(
                                            60.dp
                                        ),

                                    tint =
                                        Color(
                                            0xFF007A7A
                                        )

                                )


                                Spacer(
                                    Modifier.height(
                                        12.dp
                                    )
                                )


                                Text(

                                    text =
                                        "No notifications yet",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleLarge

                                )


                                Spacer(
                                    Modifier.height(
                                        6.dp
                                    )
                                )


                                Text(

                                    text =
                                        "Your booking and service updates will appear here.",

                                    color =
                                        Color.Gray

                                )

                            }

                        }

                    }

                }


                // =================================================
                // NOTIFICATION LIST
                // =================================================

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(
                                16.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                10.dp
                            )

                    ) {


                        items(

                            items =
                                notifications,

                            key = { notification ->

                                notification.notification_id
                                    ?: notification.hashCode()

                            }

                        ) { notification ->


                            NotificationCard(

                                notification =
                                    notification,

                                onClick = {

                                    notification
                                        .notification_id
                                        ?.let { id ->

                                            viewModel
                                                .markAsRead(
                                                    id
                                                )

                                        }

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}


@Composable
private fun NotificationCard(

    notification: AppNotification,

    onClick: () -> Unit

) {


    val icon =
        when (
            notification
                .notification_type
                .uppercase()
        ) {

            "ACCEPTED" ->
                "✅"

            "REJECTED" ->
                "❌"

            "COMPLETED" ->
                "🎉"

            "REMINDER" ->
                "🔔"

            else ->
                "🔔"

        }


    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {

                    onClick()

                },

        shape =
            RoundedCornerShape(
                20.dp
            ),

        colors =
            CardDefaults
                .cardColors(

                    containerColor =
                        if (
                            notification.is_read
                        ) {

                            Color.White

                        } else {

                            Color(
                                0xFFDDF8F3
                            )

                        }

                ),

        elevation =
            CardDefaults
                .cardElevation(
                    4.dp
                )

    ) {


        Row(

            modifier =
                Modifier.padding(
                    16.dp
                ),

            verticalAlignment =
                Alignment.Top

        ) {


            Text(

                text =
                    icon,

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium

            )


            Spacer(
                Modifier.width(
                    14.dp
                )
            )


            Column(

                modifier =
                    Modifier.weight(
                        1f
                    )

            ) {


                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Text(

                        text =
                            notification.title,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    if (
                        !notification.is_read
                    ) {

                        Box(

                            modifier =
                                Modifier
                                    .size(
                                        10.dp
                                    )
                                    .background(

                                        Color(
                                            0xFF007A7A
                                        ),

                                        CircleShape

                                    )

                        )

                    }

                }


                Spacer(
                    Modifier.height(
                        5.dp
                    )
                )


                Text(

                    text =
                        notification.message,

                    color =
                        Color.DarkGray

                )


                notification.created_at
                    ?.let { createdAt ->


                        Spacer(
                            Modifier.height(
                                8.dp
                            )
                        )


                        Text(

                            text =
                                formatNotificationDate(
                                    createdAt
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                Color.Gray

                        )

                    }

            }

        }

    }

}


private fun formatNotificationDate(

    date: String

): String {

    return try {

        val cleanDate =
            date
                .substringBefore(".")


        val input =
            SimpleDateFormat(

                "yyyy-MM-dd'T'HH:mm:ss",

                Locale.getDefault()

            )


        input.timeZone =
            TimeZone.getTimeZone(
                "UTC"
            )


        val parsed =
            input.parse(
                cleanDate
            )


        val output =
            SimpleDateFormat(

                "dd MMM yyyy, hh:mm a",

                Locale.getDefault()

            )


        if (
            parsed != null
        ) {

            output.format(
                parsed
            )

        } else {

            date

        }

    } catch (e: Exception) {

        date

    }

}