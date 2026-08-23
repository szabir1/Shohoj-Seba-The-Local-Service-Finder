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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.ui.provider.components.ProviderBookingCard
import com.example.shohojseba.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderBookingsScreen(

    viewModel: BookingViewModel = viewModel()

) {

    val bookings by viewModel.bookings
    val isLoading by viewModel.isLoading

    var dialogVisible by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadProviderBookings()
    }

    // Success Dialog

    if (dialogVisible) {

        AlertDialog(

            onDismissRequest = {
                dialogVisible = false
            },

            icon = {

                Icon(

                    imageVector = when (dialogTitle) {
                        "Booking Accepted" -> Icons.Default.CheckCircle
                        "Job Completed" -> Icons.Default.TaskAlt
                        else -> Icons.Default.Cancel
                    },

                    contentDescription = null,

                    tint = when (dialogTitle) {
                        "Booking Accepted" -> Color(0xFF2E7D32)
                        "Job Completed" -> Color(0xFF1565C0)
                        else -> Color(0xFFC62828)
                    },

                    modifier = Modifier.size(56.dp)

                )

            },

            title = {
                Text(dialogTitle)
            },

            text = {
                Text(dialogMessage)
            },

            confirmButton = {

                Button(

                    onClick = {
                        dialogVisible = false
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007A7A)
                    ),

                    shape = RoundedCornerShape(14.dp)

                ) {

                    Text("OK")

                }

            },

            shape = RoundedCornerShape(24.dp)

        )

    }

    Scaffold(

        containerColor = Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text("Service Requests")

                },

                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = Color.Transparent

                )

            )

        }

    ) { padding ->

        Box(

            modifier = Modifier

                .fillMaxSize()

                .background(

                    Brush.verticalGradient(

                        listOf(

                            Color(0xFFEFFFFB),

                            Color.White

                        )

                    )

                )

                .padding(padding)

        ) {

            when {

                // Loading

                isLoading -> {

                    Box(

                        modifier = Modifier.fillMaxSize(),

                        contentAlignment = Alignment.Center

                    ) {

                        CircularProgressIndicator(

                            color = Color(0xFF007A7A)

                        )

                    }

                }

                // Empty State

                bookings.isEmpty() -> {

                    Box(

                        modifier = Modifier.fillMaxSize(),

                        contentAlignment = Alignment.Center

                    ) {

                        Card(

                            shape = RoundedCornerShape(24.dp),

                            elevation = CardDefaults.cardElevation(6.dp)

                        ) {

                            Column(

                                modifier = Modifier.padding(28.dp),

                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {

                                Text(

                                    "📭",

                                    style = MaterialTheme.typography.headlineLarge

                                )

                                Spacer(Modifier.height(10.dp))

                                Text(

                                    "No booking requests",

                                    style = MaterialTheme.typography.titleMedium

                                )

                                Spacer(Modifier.height(4.dp))

                                Text(

                                    "New customer requests will appear here.",

                                    color = Color.Gray

                                )

                            }

                        }

                    }

                }

                // Booking List

                else -> {

                    LazyColumn(

                        modifier = Modifier.fillMaxSize(),

                        contentPadding = PaddingValues(20.dp),

                        verticalArrangement = Arrangement.spacedBy(16.dp)

                    ) {

                        items(bookings) { booking ->

                            ProviderBookingCard(

                                booking = booking,

                                onAccept = {

                                    viewModel.acceptBooking(
                                        booking.bookingId
                                    )

                                    dialogTitle = "Booking Accepted"

                                    dialogMessage =
                                        "The customer has been notified that you accepted this booking."

                                    dialogVisible = true

                                },

                                onReject = {

                                    viewModel.rejectBooking(
                                        booking.bookingId
                                    )

                                    dialogTitle = "Booking Rejected"

                                    dialogMessage =
                                        "This booking request has been rejected."

                                    dialogVisible = true

                                },

                                onComplete = {

                                    viewModel.updateStatus(
                                        booking.bookingId,
                                        "Completed"
                                    )

                                    dialogTitle = "Job Completed"

                                    dialogMessage =
                                        "This service has been marked as completed."

                                    dialogVisible = true

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}