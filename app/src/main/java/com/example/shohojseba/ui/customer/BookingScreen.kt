package com.example.shohojseba.ui.customer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.BookingRequest
import com.example.shohojseba.viewmodel.BookingViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(

    providerId: Long,

    serviceId: Long,

    serviceName: String,

    providerName: String,

    viewModel: BookingViewModel = viewModel()

) {

    val context = LocalContext.current

    var bookingDate by remember { mutableStateOf("") }
    var bookingTime by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var problem by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading
    val bookingSuccess by viewModel.bookingSuccess

    val background = Color(0xFFEAF7F5)
    val primary = Color(0xFF007A7A)

    // ================= SUCCESS POPUP =================

    if (bookingSuccess) {

        AlertDialog(

            onDismissRequest = {
                viewModel.resetBookingState()
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(60.dp)
                )
            },

            title = {
                Text(
                    text = "Booking Confirmed!",
                    style = MaterialTheme.typography.headlineSmall
                )
            },

            text = {
                Text(
                    text = "Your booking has been sent to the provider successfully.\n\nCurrent Status: Pending"
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        viewModel.resetBookingState()

                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    ),

                    shape = RoundedCornerShape(14.dp)

                ) {

                    Text("Awesome!")

                }

            },

            shape = RoundedCornerShape(24.dp)

        )

    }

    // ================= SCREEN =================

    Scaffold(

        containerColor = background,

        topBar = {

            TopAppBar(

                title = {
                    Text("Book Service")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background
                )

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {

            // Service Card

            Card(

                shape = RoundedCornerShape(22.dp),

                elevation = CardDefaults.cardElevation(6.dp)

            ) {

                Column(

                    modifier = Modifier.padding(20.dp)

                ) {

                    Text(
                        text = "🛠",
                        fontSize = 36.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        serviceName,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "Provider: $providerName",
                        color = Color.Gray
                    )

                }

            }

            // Date

            OutlinedTextField(

                value = bookingDate,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Booking Date")
                },

                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, null)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

                trailingIcon = {

                    TextButton(

                        onClick = {

                            val calendar = Calendar.getInstance()

                            DatePickerDialog(

                                context,

                                { _, year, month, day ->

                                    bookingDate = String.format(
                                        "%04d-%02d-%02d",
                                        year,
                                        month + 1,
                                        day
                                    )

                                },

                                calendar.get(Calendar.YEAR),

                                calendar.get(Calendar.MONTH),

                                calendar.get(Calendar.DAY_OF_MONTH)

                            ).show()

                        }

                    ) {

                        Text("Pick")

                    }

                }

            )

            // Time

            OutlinedTextField(

                value = bookingTime,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Booking Time")
                },

                leadingIcon = {
                    Icon(Icons.Default.Schedule, null)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

                trailingIcon = {

                    TextButton(

                        onClick = {

                            val calendar = Calendar.getInstance()

                            TimePickerDialog(

                                context,

                                { _, hour, minute ->

                                    bookingTime = String.format(
                                        "%02d:%02d",
                                        hour,
                                        minute
                                    )

                                },

                                calendar.get(Calendar.HOUR_OF_DAY),

                                calendar.get(Calendar.MINUTE),

                                true

                            ).show()

                        }

                    ) {

                        Text("Pick")

                    }

                }

            )

            // Address

            OutlinedTextField(

                value = address,

                onValueChange = {
                    address = it
                },

                label = {
                    Text("Service Address")
                },

                leadingIcon = {
                    Icon(Icons.Default.LocationOn, null)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)

            )

            // Problem

            OutlinedTextField(

                value = problem,

                onValueChange = {
                    problem = it
                },

                label = {
                    Text("Describe your problem")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),

                shape = RoundedCornerShape(16.dp)

            )

            Spacer(Modifier.height(8.dp))

            // Button

            Button(

                onClick = {

                    val customerId = UserSession.customerId

                    if (
                        customerId != null &&
                        bookingDate.isNotBlank() &&
                        bookingTime.isNotBlank() &&
                        address.isNotBlank() &&
                        problem.isNotBlank()
                    ) {

                        viewModel.createBooking(

                            BookingRequest(

                                booking_date = bookingDate,

                                booking_time = bookingTime,

                                address = address,

                                problem_description = problem,

                                customer_id = customerId,

                                provider_id = providerId,

                                service_id = serviceId

                            )

                        )

                    }

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )

            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        "Confirm Booking",
                        fontSize = 16.sp
                    )

                }

            }

            Spacer(Modifier.height(20.dp))

        }

    }

}