package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.notification.BookingNotificationHelper
import com.example.shohojseba.ui.customer.components.CustomerBookingCard
import com.example.shohojseba.viewmodel.BookingViewModel
import com.example.shohojseba.viewmodel.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerBookingsScreen(

    onReviewClick: (
        bookingId: Long,
        providerId: Long,
        serviceName: String,
        providerName: String
    ) -> Unit,

    viewModel: BookingViewModel = viewModel(),

    favoriteViewModel: FavoriteViewModel = viewModel()

) {

    // =====================================================
    // CONTEXT
    // =====================================================

    val context =
        LocalContext.current


    // =====================================================
    // COLORS
    // =====================================================

    val primary =
        Color(0xFF00897B)

    val darkPrimary =
        Color(0xFF00695C)

    val textSecondary =
        Color(0xFF6F7976)


    // =====================================================
    // BOOKING STATE
    // =====================================================

    val bookings by
    viewModel.bookings

    val isLoading by
    viewModel.isLoading


    // =====================================================
    // FAVORITE STATE
    // =====================================================

    val favoriteIds by
    favoriteViewModel
        .favoriteIds
        .collectAsState()


    // =====================================================
    // FILTER STATE
    // =====================================================

    var selectedFilter by
    remember {
        mutableStateOf("All")
    }


    val filters =
        listOf(
            "All",
            "Pending",
            "Quotation Sent",
            "Accepted",
            "Completed",
            "Rejected"
        )


    // =====================================================
    // FILTER BOOKINGS
    // =====================================================

    val filteredBookings =

        if (
            selectedFilter == "All"
        ) {

            bookings

        } else {

            bookings.filter {

                it.status.equals(
                    selectedFilter,
                    ignoreCase = true
                )

            }

        }


    // =====================================================
    // LOAD DATA
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel
            .loadCustomerBookings()

        favoriteViewModel
            .loadFavoriteIds()

    }


    // =====================================================
    // CHECK BOOKING STATUS CHANGES
    // =====================================================

    LaunchedEffect(
        bookings
    ) {

        if (
            bookings.isNotEmpty()
        ) {

            BookingNotificationHelper
                .checkBookingStatusChanges(
                    context = context,
                    bookings = bookings
                )

        }

    }


    // =====================================================
    // SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            Color.Transparent

    ) { padding ->


        Box(

            modifier =
                Modifier
                    .fillMaxSize()
                    .background(

                        Brush.verticalGradient(

                            listOf(
                                Color(0xFFE9FAF6),
                                Color(0xFFF8FCFB),
                                Color.White
                            )

                        )

                    )
                    .padding(padding)

        ) {


            Column(

                modifier =
                    Modifier.fillMaxSize()

            ) {


                // =================================================
                // HEADER AREA
                // =================================================

                Column(

                    modifier =
                        Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 20.dp
                        )

                ) {


                    // =================================================
                    // TITLE
                    // =================================================

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(48.dp),

                            shape =
                                RoundedCornerShape(16.dp),

                            color =
                                Color(0xFFDDF5F0)

                        ) {


                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {


                                Icon(

                                    imageVector =
                                        Icons.Default.CalendarMonth,

                                    contentDescription =
                                        null,

                                    tint =
                                        primary,

                                    modifier =
                                        Modifier.size(25.dp)

                                )

                            }

                        }


                        Spacer(
                            Modifier.width(13.dp)
                        )


                        Column {


                            Text(

                                text =
                                    "My Bookings",

                                fontSize =
                                    27.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF17201E)

                            )


                            Spacer(
                                Modifier.height(2.dp)
                            )


                            Text(

                                text =
                                    "Track and manage your services",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    textSecondary

                            )

                        }

                    }


                    // =================================================
                    // SUMMARY
                    // =================================================

                    if (
                        !isLoading &&
                        bookings.isNotEmpty()
                    ) {


                        Spacer(
                            Modifier.height(20.dp)
                        )


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)

                        ) {


                            // =========================================
                            // TOTAL
                            // =========================================

                            BookingSummaryCard(

                                modifier =
                                    Modifier.weight(1f),

                                number =
                                    bookings
                                        .size
                                        .toString(),

                                label =
                                    "Total",

                                backgroundColor =
                                    Color(0xFFE4F5F2),

                                textColor =
                                    darkPrimary

                            )


                            // =========================================
                            // WAITING
                            // =========================================

                            BookingSummaryCard(

                                modifier =
                                    Modifier.weight(1f),

                                number =
                                    bookings
                                        .count {

                                            it.status.equals(
                                                "Pending",
                                                ignoreCase = true
                                            ) ||
                                                    it.status.equals(
                                                        "Quotation Sent",
                                                        ignoreCase = true
                                                    )

                                        }
                                        .toString(),

                                label =
                                    "Waiting",

                                backgroundColor =
                                    Color(0xFFFFF3DC),

                                textColor =
                                    Color(0xFFB66A00)

                            )


                            // =========================================
                            // COMPLETED
                            // =========================================

                            BookingSummaryCard(

                                modifier =
                                    Modifier.weight(1f),

                                number =
                                    bookings
                                        .count {

                                            it.status.equals(
                                                "Completed",
                                                ignoreCase = true
                                            )

                                        }
                                        .toString(),

                                label =
                                    "Completed",

                                backgroundColor =
                                    Color(0xFFE7F4FF),

                                textColor =
                                    Color(0xFF1565C0)

                            )

                        }

                    }


                    // =================================================
                    // FILTERS
                    // =================================================

                    if (
                        !isLoading &&
                        bookings.isNotEmpty()
                    ) {


                        Spacer(
                            Modifier.height(22.dp)
                        )


                        Text(

                            text =
                                "Your requests",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.Bold

                        )


                        Spacer(
                            Modifier.height(10.dp)
                        )


                        Row(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(
                                        rememberScrollState()
                                    ),

                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)

                        ) {


                            filters.forEach { filter ->


                                val selected =
                                    selectedFilter == filter


                                FilterChip(

                                    selected =
                                        selected,

                                    onClick = {

                                        selectedFilter =
                                            filter

                                    },

                                    label = {


                                        Text(

                                            text =

                                                when (filter) {

                                                    "Quotation Sent" ->
                                                        "Quotation"

                                                    else ->
                                                        filter

                                                },

                                            fontWeight =

                                                if (selected) {

                                                    FontWeight.Bold

                                                } else {

                                                    FontWeight.Medium

                                                }

                                        )

                                    },

                                    shape =
                                        RoundedCornerShape(50.dp),

                                    colors =
                                        FilterChipDefaults
                                            .filterChipColors(

                                                containerColor =
                                                    Color.White,

                                                labelColor =
                                                    textSecondary,

                                                selectedContainerColor =
                                                    primary,

                                                selectedLabelColor =
                                                    Color.White

                                            ),

                                    border =
                                        FilterChipDefaults
                                            .filterChipBorder(

                                                enabled =
                                                    true,

                                                selected =
                                                    selected,

                                                borderColor =
                                                    Color(0xFFD8E5E2),

                                                selectedBorderColor =
                                                    primary

                                            )

                                )

                            }

                        }


                        Spacer(
                            Modifier.height(12.dp)
                        )

                    }

                }


                // =====================================================
                // MAIN CONTENT
                // =====================================================

                when {


                    // =================================================
                    // LOADING
                    // =================================================

                    isLoading -> {


                        Box(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f),

                            contentAlignment =
                                Alignment.Center

                        ) {


                            Column(

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {


                                CircularProgressIndicator(

                                    color =
                                        primary

                                )


                                Spacer(
                                    Modifier.height(14.dp)
                                )


                                Text(

                                    text =
                                        "Loading your bookings...",

                                    color =
                                        textSecondary

                                )

                            }

                        }

                    }


                    // =================================================
                    // NO BOOKINGS
                    // =================================================

                    bookings.isEmpty() -> {


                        Box(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(20.dp),

                            contentAlignment =
                                Alignment.Center

                        ) {


                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(28.dp),

                                colors =
                                    CardDefaults
                                        .cardColors(

                                            containerColor =
                                                Color.White

                                        ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(3.dp)

                            ) {


                                Column(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(32.dp),

                                    horizontalAlignment =
                                        Alignment.CenterHorizontally

                                ) {


                                    Surface(

                                        modifier =
                                            Modifier.size(78.dp),

                                        shape =
                                            CircleShape,

                                        color =
                                            Color(0xFFE4F5F2)

                                    ) {


                                        Box(

                                            contentAlignment =
                                                Alignment.Center

                                        ) {


                                            Icon(

                                                imageVector =
                                                    Icons.Default.Book,

                                                contentDescription =
                                                    null,

                                                modifier =
                                                    Modifier.size(38.dp),

                                                tint =
                                                    primary

                                            )

                                        }

                                    }


                                    Spacer(
                                        Modifier.height(18.dp)
                                    )


                                    Text(

                                        text =
                                            "No bookings yet",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge,

                                        fontWeight =
                                            FontWeight.Bold

                                    )


                                    Spacer(
                                        Modifier.height(7.dp)
                                    )


                                    Text(

                                        text =
                                            "Your booked services will appear here so you can easily track their progress.",

                                        color =
                                            textSecondary,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodyMedium

                                    )

                                }

                            }

                        }

                    }


                    // =================================================
                    // FILTER EMPTY
                    // =================================================

                    filteredBookings.isEmpty() -> {


                        Box(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(20.dp),

                            contentAlignment =
                                Alignment.Center

                        ) {


                            Column(

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {


                                Surface(

                                    modifier =
                                        Modifier.size(70.dp),

                                    shape =
                                        CircleShape,

                                    color =
                                        Color(0xFFE4F5F2)

                                ) {


                                    Box(

                                        contentAlignment =
                                            Alignment.Center

                                    ) {


                                        Icon(

                                            imageVector =
                                                Icons.Default.Book,

                                            contentDescription =
                                                null,

                                            tint =
                                                primary,

                                            modifier =
                                                Modifier.size(32.dp)

                                        )

                                    }

                                }


                                Spacer(
                                    Modifier.height(15.dp)
                                )


                                Text(

                                    text =
                                        "No $selectedFilter bookings",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold

                                )


                                Spacer(
                                    Modifier.height(5.dp)
                                )


                                Text(

                                    text =
                                        "Bookings with this status will appear here.",

                                    color =
                                        textSecondary

                                )

                            }

                        }

                    }


                    // =================================================
                    // BOOKING LIST
                    // =================================================

                    else -> {


                        LazyColumn(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f),

                            contentPadding =
                                PaddingValues(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 4.dp,
                                    bottom = 24.dp
                                ),

                            verticalArrangement =
                                Arrangement.spacedBy(14.dp)

                        ) {


                            items(

                                items =
                                    filteredBookings,

                                key = {

                                    it.bookingId

                                }

                            ) { booking ->


                                val isFavorite =
                                    favoriteIds.contains(
                                        booking.serviceId
                                    )


                                CustomerBookingCard(

                                    booking =
                                        booking,


                                    // =================================
                                    // REVIEW
                                    // =================================

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

                                    },


                                    // =================================
                                    // FAVORITE
                                    // =================================

                                    isFavorite =
                                        isFavorite,


                                    onFavoriteClick = {


                                        favoriteViewModel
                                            .toggleFavorite(
                                                booking.serviceId
                                            )

                                    },


                                    // =================================
                                    // ACCEPT QUOTATION
                                    // =================================

                                    onAcceptQuotation = {


                                        viewModel
                                            .acceptQuotation(
                                                booking
                                            )

                                    },


                                    // =================================
                                    // REJECT QUOTATION
                                    // =================================

                                    onRejectQuotation = {


                                        viewModel
                                            .rejectQuotation(
                                                booking
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

}


// =====================================================
// SUMMARY CARD
// =====================================================

@Composable
private fun BookingSummaryCard(

    modifier: Modifier = Modifier,

    number: String,

    label: String,

    backgroundColor: Color,

    textColor: Color

) {


    Surface(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(18.dp),

        color =
            backgroundColor

    ) {


        Column(

            modifier =
                Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 13.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {


            Text(

                text =
                    number,

                fontSize =
                    21.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    textColor

            )


            Spacer(
                Modifier.height(2.dp)
            )


            Text(

                text =
                    label,

                style =
                    MaterialTheme
                        .typography
                        .labelMedium,

                color =
                    textColor

            )

        }

    }

}