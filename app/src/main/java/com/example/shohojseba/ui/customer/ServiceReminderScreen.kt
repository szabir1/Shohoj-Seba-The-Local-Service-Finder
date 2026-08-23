package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.NotificationsActive

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.ServiceReminderViewModel

import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRemindersScreen(

    viewModel: ServiceReminderViewModel =
        viewModel()

) {


    val reminders by
    viewModel.reminders


    val isLoading by
    viewModel.isLoading


    LaunchedEffect(Unit) {

        viewModel
            .loadReminders()

    }


    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Service Reminders"
                    )

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


                // =============================================
                // LOADING
                // =============================================

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


                // =============================================
                // EMPTY
                // =============================================

                reminders.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Card(

                            shape =
                                RoundedCornerShape(
                                    26.dp
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
                                        Icons.Default.NotificationsActive,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(
                                            0xFF007A7A
                                        ),

                                    modifier =
                                        Modifier.size(
                                            55.dp
                                        )

                                )


                                Spacer(
                                    Modifier.height(
                                        12.dp
                                    )
                                )


                                Text(

                                    text =
                                        "No service reminders yet",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium

                                )


                                Spacer(
                                    Modifier.height(
                                        5.dp
                                    )
                                )


                                Text(

                                    text =
                                        "Your upcoming servicing dates will appear here after eligible services are completed.",

                                    color =
                                        Color.Gray

                                )

                            }

                        }

                    }

                }


                // =============================================
                // REMINDERS
                // =============================================

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(
                                20.dp
                            ),

                        verticalArrangement =
                            Arrangement
                                .spacedBy(
                                    16.dp
                                )

                    ) {


                        item {

                            Text(

                                text =
                                    "Upcoming Servicing",

                                style =
                                    MaterialTheme
                                        .typography
                                        .headlineSmall

                            )

                        }


                        items(
                            reminders
                        ) { reminder ->


                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        26.dp
                                    ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
                                            5.dp
                                        ),

                                colors =
                                    CardDefaults
                                        .cardColors(

                                            containerColor =
                                                Color.White

                                        )

                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            20.dp
                                        )

                                ) {


                                    Text(

                                        text =
                                            "❄️ ${reminder.service_name}",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge

                                    )


                                    Spacer(
                                        Modifier.height(
                                            15.dp
                                        )
                                    )


                                    Card(

                                        colors =
                                            CardDefaults
                                                .cardColors(

                                                    containerColor =
                                                        Color(
                                                            0xFFDDF8F3
                                                        )

                                                ),

                                        shape =
                                            RoundedCornerShape(
                                                18.dp
                                            )

                                    ) {

                                        Row(

                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        16.dp
                                                    ),

                                            verticalAlignment =
                                                Alignment.CenterVertically

                                        ) {

                                            Icon(

                                                imageVector =
                                                    Icons.Default.CalendarMonth,

                                                contentDescription =
                                                    null,

                                                tint =
                                                    Color(
                                                        0xFF007A7A
                                                    )

                                            )


                                            Spacer(
                                                Modifier.width(
                                                    10.dp
                                                )
                                            )


                                            Column {

                                                Text(

                                                    text =
                                                        "Next servicing date",

                                                    color =
                                                        Color.Gray,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .bodySmall

                                                )


                                                Text(

                                                    text =
                                                        formatReminderDate(
                                                            reminder.next_service_date
                                                        ),

                                                    color =
                                                        Color(
                                                            0xFF007A7A
                                                        ),

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .titleMedium

                                                )

                                            }

                                        }

                                    }


                                    Spacer(
                                        Modifier.height(
                                            12.dp
                                        )
                                    )


                                    Text(

                                        text =
                                            "Last serviced: ${
                                                formatReminderDate(
                                                    reminder.completed_date
                                                )
                                            }",

                                        color =
                                            Color.Gray

                                    )


                                    Text(

                                        text =
                                            "Booking #${reminder.booking_id}",

                                        color =
                                            Color.Gray,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall

                                    )

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}


// =====================================================
// DISPLAY DATE NICELY
// =====================================================

private fun formatReminderDate(

    date: String

): String {

    return try {


        val input =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )


        val output =
            SimpleDateFormat(
                "dd MMMM yyyy",
                Locale.getDefault()
            )


        val parsed =
            input.parse(
                date
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