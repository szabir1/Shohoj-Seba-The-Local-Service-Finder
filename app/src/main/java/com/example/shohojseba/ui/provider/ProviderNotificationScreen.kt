package com.example.shohojseba.ui.provider

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.AppNotification
import com.example.shohojseba.viewmodel.ProviderNotificationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderNotificationsScreen(

    onServiceRequestsClick: () -> Unit,

    viewModel: ProviderNotificationViewModel =
        viewModel()

) {


    val notifications by
    viewModel.notifications


    val isLoading by
    viewModel.isLoading


    val unreadCount =
        notifications.count {

            !it.is_read

        }


    // =====================================================
    // LOAD
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

                    Column {

                        Text(

                            text =
                                "Notifications",

                            fontWeight =
                                FontWeight.Bold

                        )


                        Text(

                            text =

                                if (
                                    unreadCount > 0
                                ) {

                                    "$unreadCount unread update${
                                        if (
                                            unreadCount == 1
                                        ) {
                                            ""
                                        } else {
                                            "s"
                                        }
                                    }"

                                } else {

                                    "You're all caught up"

                                },

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                Color(
                                    0xFF66706D
                                )

                        )

                    }

                },

                actions = {

                    if (
                        unreadCount > 0
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
                                    0xFFE8FAF6
                                ),

                                Color(
                                    0xFFF7FBFA
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


                isLoading -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Column(

                            horizontalAlignment =
                                Alignment.CenterHorizontally

                        ) {

                            CircularProgressIndicator(

                                color =
                                    Color(
                                        0xFF00897B
                                    )

                            )


                            Spacer(
                                Modifier.height(
                                    12.dp
                                )
                            )


                            Text(

                                text =
                                    "Loading notifications...",

                                color =
                                    Color(
                                        0xFF66706D
                                    )

                            )

                        }

                    }

                }


                notifications.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    20.dp
                                ),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Card(

                            modifier =
                                Modifier.fillMaxWidth(),

                            shape =
                                RoundedCornerShape(
                                    26.dp
                                ),

                            colors =
                                CardDefaults.cardColors(

                                    containerColor =
                                        Color.White

                                ),

                            elevation =
                                CardDefaults.cardElevation(
                                    3.dp
                                )

                        ) {

                            Column(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            30.dp
                                        ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {

                                Surface(

                                    modifier =
                                        Modifier.size(
                                            74.dp
                                        ),

                                    shape =
                                        CircleShape,

                                    color =
                                        Color(
                                            0xFFE3F5F1
                                        )

                                ) {

                                    Box(

                                        contentAlignment =
                                            Alignment.Center

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.NotificationsNone,

                                            contentDescription =
                                                null,

                                            modifier =
                                                Modifier.size(
                                                    36.dp
                                                ),

                                            tint =
                                                Color(
                                                    0xFF00897B
                                                )

                                        )

                                    }

                                }


                                Spacer(
                                    Modifier.height(
                                        15.dp
                                    )
                                )


                                Text(

                                    text =
                                        "No notifications yet",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleLarge,

                                    fontWeight =
                                        FontWeight.Bold

                                )


                                Spacer(
                                    Modifier.height(
                                        6.dp
                                    )
                                )


                                Text(

                                    text =
                                        "Booking and quotation updates will appear here.",

                                    color =
                                        Color(
                                            0xFF66706D
                                        )

                                )

                            }

                        }

                    }

                }


                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(

                                start =
                                    18.dp,

                                end =
                                    18.dp,

                                top =
                                    8.dp,

                                bottom =
                                    24.dp

                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                10.dp
                            )

                    ) {


                        items(

                            items =
                                notifications,

                            key = {

                                it.notification_id
                                    ?: it.hashCode()

                            }

                        ) { notification ->


                            ProviderNotificationCard(

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


                                    onServiceRequestsClick()

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
private fun ProviderNotificationCard(

    notification: AppNotification,

    onClick: () -> Unit

) {


    val icon =
        when (
            notification
                .notification_type
                .uppercase()
        ) {

            "NEW_BOOKING" ->
                "📩"

            "QUOTATION_REQUEST" ->
                "💰"

            "QUOTATION_ACCEPTED" ->
                "✅"

            "QUOTATION_REJECTED" ->
                "❌"

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
            CardDefaults.cardColors(

                containerColor =

                    if (
                        notification.is_read
                    ) {

                        Color.White

                    } else {

                        Color(
                            0xFFE3F5F1
                        )

                    }

            ),

        elevation =
            CardDefaults.cardElevation(
                2.dp
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


            Surface(

                modifier =
                    Modifier.size(
                        48.dp
                    ),

                shape =
                    RoundedCornerShape(
                        15.dp
                    ),

                color =
                    Color.White

            ) {

                Box(

                    contentAlignment =
                        Alignment.Center

                ) {

                    Text(

                        text =
                            icon,

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall

                    )

                }

            }


            Spacer(
                Modifier.width(
                    13.dp
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
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold

                    )


                    if (
                        !notification.is_read
                    ) {

                        Box(

                            modifier =
                                Modifier
                                    .size(
                                        9.dp
                                    )
                                    .background(

                                        Color(
                                            0xFF00897B
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
                        Color(
                            0xFF56605D
                        ),

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium

                )


                Spacer(
                    Modifier.height(
                        7.dp
                    )
                )


                Text(

                    text =
                        "Tap to view service request",

                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,

                    color =
                        Color(
                            0xFF00897B
                        ),

                    fontWeight =
                        FontWeight.SemiBold

                )

            }

        }

    }

}