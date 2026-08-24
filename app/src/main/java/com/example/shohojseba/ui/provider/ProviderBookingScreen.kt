package com.example.shohojseba.ui.provider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.TaskAlt

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.ui.provider.components.ProviderBookingCard
import com.example.shohojseba.viewmodel.BookingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderBookingsScreen(

    viewModel: BookingViewModel =
        viewModel()

) {


    val bookings by
    viewModel.bookings


    val isLoading by
    viewModel.isLoading


    var dialogVisible by remember {

        mutableStateOf(false)

    }


    var dialogTitle by remember {

        mutableStateOf("")

    }


    var dialogMessage by remember {

        mutableStateOf("")

    }


    // =====================================================
    // LOAD BOOKINGS
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel
            .loadProviderBookings()

    }


    // =====================================================
    // SUCCESS DIALOG
    // =====================================================

    if (
        dialogVisible
    ) {

        AlertDialog(

            onDismissRequest = {

                dialogVisible =
                    false

            },

            icon = {

                Icon(

                    imageVector =
                        when (
                            dialogTitle
                        ) {

                            "Booking Accepted" ->
                                Icons.Default.CheckCircle

                            "Job Completed" ->
                                Icons.Default.TaskAlt

                            "Quotation Sent" ->
                                Icons.Default.CheckCircle

                            else ->
                                Icons.Default.Cancel

                        },

                    contentDescription =
                        null,

                    tint =
                        when (
                            dialogTitle
                        ) {

                            "Booking Accepted" ->
                                Color(0xFF2E7D32)

                            "Job Completed" ->
                                Color(0xFF1565C0)

                            "Quotation Sent" ->
                                Color(0xFFFF8F00)

                            else ->
                                Color(0xFFC62828)

                        },

                    modifier =
                        Modifier.size(
                            56.dp
                        )

                )

            },

            title = {

                Text(
                    dialogTitle
                )

            },

            text = {

                Text(
                    dialogMessage
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        dialogVisible =
                            false

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF00897B)

                        ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "OK"
                    )

                }

            },

            shape =
                RoundedCornerShape(
                    24.dp
                )

        )

    }


    // =====================================================
    // SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(

                            text =
                                "Service Requests",

                            fontWeight =
                                FontWeight.Bold

                        )


                        Text(

                            text =
                                "Manage customer bookings",

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

                colors =
                    TopAppBarDefaults.topAppBarColors(

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
                                    "Loading service requests...",

                                color =
                                    Color(
                                        0xFF66706D
                                    )

                            )

                        }

                    }

                }


                // =================================================
                // EMPTY
                // =================================================

                bookings.isEmpty() -> {

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

                                Text(

                                    text =
                                        "📭",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .headlineLarge

                                )


                                Spacer(
                                    Modifier.height(
                                        10.dp
                                    )
                                )


                                Text(

                                    text =
                                        "No booking requests",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleLarge,

                                    fontWeight =
                                        FontWeight.Bold

                                )


                                Spacer(
                                    Modifier.height(
                                        5.dp
                                    )
                                )


                                Text(

                                    text =
                                        "New customer requests will appear here.",

                                    color =
                                        Color(
                                            0xFF66706D
                                        )

                                )

                            }

                        }

                    }

                }


                // =================================================
                // BOOKINGS
                // =================================================

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(

                                start =
                                    20.dp,

                                end =
                                    20.dp,

                                top =
                                    8.dp,

                                bottom =
                                    24.dp

                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                16.dp
                            )

                    ) {


                        items(

                            items =
                                bookings,

                            key = {

                                it.bookingId

                            }

                        ) { booking ->


                            ProviderBookingCard(

                                booking =
                                    booking,


                                // =================================
                                // ACCEPT
                                // =================================

                                onAccept = {

                                    viewModel
                                        .acceptBooking(
                                            booking
                                        )


                                    dialogTitle =
                                        "Booking Accepted"


                                    dialogMessage =
                                        "The booking has been accepted and an in-app notification was created for the customer."


                                    dialogVisible =
                                        true

                                },


                                // =================================
                                // REJECT
                                // =================================

                                onReject = {

                                    viewModel
                                        .rejectBooking(
                                            booking
                                        )


                                    dialogTitle =
                                        "Booking Rejected"


                                    dialogMessage =
                                        "The booking has been rejected and an in-app notification was created for the customer."


                                    dialogVisible =
                                        true

                                },


                                // =================================
                                // COMPLETE
                                // =================================

                                onComplete = {

                                    viewModel
                                        .completeBooking(
                                            booking
                                        )


                                    dialogTitle =
                                        "Job Completed"


                                    dialogMessage =
                                        "The service has been marked as completed. The customer received an in-app notification. For AC services, the next servicing reminder was also scheduled."


                                    dialogVisible =
                                        true

                                },


                                // =================================
                                // SEND QUOTATION
                                // =================================

                                onSendQuotation = {
                                        price,
                                        message ->


                                    viewModel
                                        .sendQuotation(

                                            booking =
                                                booking,

                                            quotedPrice =
                                                price,

                                            message =
                                                message

                                        )


                                    dialogTitle =
                                        "Quotation Sent"


                                    dialogMessage =
                                        "The quotation has been sent to the customer and an in-app notification was created."


                                    dialogVisible =
                                        true

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}