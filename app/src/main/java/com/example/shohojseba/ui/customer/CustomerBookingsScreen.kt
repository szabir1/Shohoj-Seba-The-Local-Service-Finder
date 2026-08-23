package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.ui.customer.components.CustomerBookingCard
import com.example.shohojseba.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerBookingsScreen(

    onReviewClick: (
        bookingId: Long,
        providerId: Long,
        serviceName: String,
        providerName: String
    ) -> Unit,

    viewModel: BookingViewModel = viewModel()

) {

    val bookings by viewModel.bookings

    val isLoading by
    viewModel.isLoading

    LaunchedEffect(Unit) {

        viewModel.loadCustomerBookings()

    }

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "My Bookings"
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

                // ================= LOADING =================

                isLoading -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        CircularProgressIndicator(

                            color =
                                Color(0xFF007A7A)

                        )

                    }

                }

                // ================= EMPTY =================

                bookings.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Card(

                            shape =
                                RoundedCornerShape(
                                    24.dp
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
                                        28.dp
                                    ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Book,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(
                                            60.dp
                                        ),

                                    tint =
                                        Color(0xFF007A7A)

                                )

                                Spacer(
                                    Modifier.height(
                                        12.dp
                                    )
                                )

                                Text(

                                    text =
                                        "No bookings yet",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium

                                )

                                Spacer(
                                    Modifier.height(
                                        4.dp
                                    )
                                )

                                Text(

                                    text =
                                        "Book your first service from the Home screen.",

                                    color =
                                        Color.Gray

                                )

                            }

                        }

                    }

                }

                // ================= BOOKINGS =================

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(
                                20.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                16.dp
                            )

                    ) {

                        items(

                            items = bookings,

                            key = {
                                it.bookingId
                            }

                        ) { booking ->

                            CustomerBookingCard(

                                booking =
                                    booking,

                                onReviewClick = {

                                    onReviewClick(

                                        booking.bookingId,

                                        booking.providerId,

                                        booking.service
                                            ?.serviceName
                                            ?: "Service",

                                        booking.provider
                                            ?.name
                                            ?: "Provider"

                                    )

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}