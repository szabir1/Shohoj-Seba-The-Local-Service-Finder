package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.Review
import com.example.shohojseba.viewmodel.AdminViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReviewsScreen(

    viewModel: AdminViewModel =
        viewModel()

) {

    val reviews by
    viewModel.reviews

    val customers by
    viewModel.customers

    val providers by
    viewModel.providers

    val isLoading by
    viewModel.isLoading

    val message by
    viewModel.message


    var reviewToDelete by remember {

        mutableStateOf<Review?>(
            null
        )
    }


    var showMessageDialog by remember {

        mutableStateOf(false)
    }


    // =====================================================
    // LOAD DATA
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel.loadReviews()

        viewModel.loadCustomers()

        viewModel.loadProviders()
    }


    // =====================================================
    // MESSAGE POPUP
    // =====================================================

    LaunchedEffect(message) {

        if (message.isNotBlank()) {

            showMessageDialog =
                true
        }
    }


    // =====================================================
    // SUCCESS / ERROR DIALOG
    // =====================================================

    if (showMessageDialog) {

        val success =
            message.contains(
                "successfully",
                ignoreCase = true
            )

        AlertDialog(

            onDismissRequest = {

                showMessageDialog =
                    false

                viewModel.clearMessage()
            },

            title = {

                Text(

                    if (success)
                        "Success"
                    else
                        "Notice"
                )
            },

            text = {

                Text(
                    message
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        showMessageDialog =
                            false

                        viewModel.clearMessage()
                    },

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    Color(
                                        0xFF007A7A
                                    )
                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text("OK")
                }
            },

            shape =
                RoundedCornerShape(
                    24.dp
                )
        )
    }


    // =====================================================
    // DELETE CONFIRMATION DIALOG
    // =====================================================

    if (reviewToDelete != null) {

        AlertDialog(

            onDismissRequest = {

                reviewToDelete =
                    null
            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        null,

                    tint =
                        Color(
                            0xFFC62828
                        ),

                    modifier =
                        Modifier.size(
                            45.dp
                        )
                )
            },

            title = {

                Text(
                    "Delete Review?"
                )
            },

            text = {

                Column {

                    Text(
                        "Are you sure you want to permanently delete this review?"
                    )

                    Spacer(
                        Modifier.height(
                            10.dp
                        )
                    )

                    Text(
                        text =
                            "Rating: ${reviewToDelete?.rating ?: 0}/5"
                    )

                    if (
                        !reviewToDelete
                            ?.comment
                            .isNullOrBlank()
                    ) {

                        Spacer(
                            Modifier.height(
                                5.dp
                            )
                        )

                        Text(
                            text =
                                "\"${reviewToDelete?.comment}\""
                        )
                    }

                    Spacer(
                        Modifier.height(
                            10.dp
                        )
                    )

                    Text(
                        text =
                            "This action cannot be undone.",
                        color =
                            Color(
                                0xFFC62828
                            )
                    )
                }
            },

            confirmButton = {

                Button(

                    onClick = {

                        reviewToDelete
                            ?.reviewId
                            ?.let { reviewId ->

                                viewModel
                                    .deleteReview(
                                        reviewId
                                    )
                            }

                        reviewToDelete =
                            null
                    },

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    Color(
                                        0xFFC62828
                                    )
                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "Delete"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        reviewToDelete =
                            null
                    }

                ) {

                    Text(
                        "Cancel"
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
    // MAIN SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Manage Reviews"
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

                        Brush
                            .verticalGradient(

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

                isLoading &&
                        reviews.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize(),

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

                reviews.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize(),

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
                                        30.dp
                                    ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.RateReview,

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
                                        10.dp
                                    )
                                )

                                Text(

                                    text =
                                        "No reviews found",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium
                                )
                            }
                        }
                    }
                }


                // =================================================
                // REVIEW LIST
                // =================================================

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier
                                .fillMaxSize(),

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


                        // =================================================
                        // SUMMARY CARD
                        // =================================================

                        item {

                            Card(

                                modifier =
                                    Modifier
                                        .fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        24.dp
                                    ),

                                colors =
                                    CardDefaults
                                        .cardColors(

                                            containerColor =
                                                Color(
                                                    0xFFDDF8F3
                                                )
                                        ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
                                            4.dp
                                        )

                            ) {

                                Row(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                20.dp
                                            ),

                                    verticalAlignment =
                                        Alignment
                                            .CenterVertically

                                ) {

                                    Icon(

                                        imageVector =
                                            Icons.Default.RateReview,

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
                                                "Total Reviews",

                                            color =
                                                Color.Gray
                                        )

                                        Text(

                                            text =
                                                reviews
                                                    .size
                                                    .toString(),

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .headlineSmall,

                                            color =
                                                Color(
                                                    0xFF007A7A
                                                )
                                        )
                                    }
                                }
                            }
                        }


                        // =================================================
                        // REVIEW CARDS
                        // =================================================

                        items(

                            items =
                                reviews,

                            key = {
                                it.reviewId
                            }

                        ) { review ->


                            val customerName =

                                customers
                                    .firstOrNull {

                                        it.customer_id ==
                                                review.customerId

                                    }
                                    ?.name
                                    ?: "Customer #${review.customerId}"


                            val providerName =

                                providers
                                    .firstOrNull {

                                        it.provider_id ==
                                                review.providerId

                                    }
                                    ?.name
                                    ?: "Provider #${review.providerId}"


                            Card(

                                modifier =
                                    Modifier
                                        .fillMaxWidth(),

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
                                            20.dp
                                        )

                                ) {


                                    // =================================================
                                    // REVIEW ID
                                    // =================================================

                                    Row(

                                        modifier =
                                            Modifier
                                                .fillMaxWidth(),

                                        horizontalArrangement =
                                            Arrangement
                                                .SpaceBetween,

                                        verticalAlignment =
                                            Alignment
                                                .CenterVertically

                                    ) {

                                        Text(

                                            text =
                                                "Review #${review.reviewId}",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleLarge
                                        )


                                        Row(

                                            verticalAlignment =
                                                Alignment
                                                    .CenterVertically

                                        ) {

                                            Icon(

                                                imageVector =
                                                    Icons.Default.Star,

                                                contentDescription =
                                                    null,

                                                tint =
                                                    Color(
                                                        0xFFFFA000
                                                    ),

                                                modifier =
                                                    Modifier.size(
                                                        22.dp
                                                    )
                                            )

                                            Spacer(
                                                Modifier.width(
                                                    4.dp
                                                )
                                            )

                                            Text(

                                                text =
                                                    "${review.rating}/5",

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .titleMedium
                                            )
                                        }
                                    }


                                    Spacer(
                                        Modifier.height(
                                            12.dp
                                        )
                                    )


                                    HorizontalDivider()


                                    Spacer(
                                        Modifier.height(
                                            12.dp
                                        )
                                    )


                                    // =================================================
                                    // CUSTOMER
                                    // =================================================

                                    Row(

                                        verticalAlignment =
                                            Alignment
                                                .CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Person,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(
                                                    0xFF007A7A
                                                ),

                                            modifier =
                                                Modifier.size(
                                                    20.dp
                                                )
                                        )

                                        Spacer(
                                            Modifier.width(
                                                7.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                "Customer: $customerName"
                                        )
                                    }


                                    Spacer(
                                        Modifier.height(
                                            7.dp
                                        )
                                    )


                                    // =================================================
                                    // PROVIDER
                                    // =================================================

                                    Row(

                                        verticalAlignment =
                                            Alignment
                                                .CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Person,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(
                                                    0xFF007A7A
                                                ),

                                            modifier =
                                                Modifier.size(
                                                    20.dp
                                                )
                                        )

                                        Spacer(
                                            Modifier.width(
                                                7.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                "Provider: $providerName"
                                        )
                                    }


                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )


                                    Text(

                                        text =
                                            "Booking ID: ${review.bookingId}",

                                        color =
                                            Color.Gray
                                    )


                                    // =================================================
                                    // COMMENT
                                    // =================================================

                                    Spacer(
                                        Modifier.height(
                                            15.dp
                                        )
                                    )


                                    Text(

                                        text =
                                            "Comment",

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
                                            review.comment
                                                ?.takeIf {
                                                    it.isNotBlank()
                                                }
                                                ?: "No comment provided"
                                    )


                                    // =================================================
                                    // CREATED DATE
                                    // =================================================

                                    if (
                                        !review.createdAt
                                            .isNullOrBlank()
                                    ) {

                                        Spacer(
                                            Modifier.height(
                                                10.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                "Created: ${review.createdAt}",

                                            color =
                                                Color.Gray,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall
                                        )
                                    }


                                    Spacer(
                                        Modifier.height(
                                            18.dp
                                        )
                                    )


                                    // =================================================
                                    // DELETE BUTTON
                                    // =================================================

                                    Button(

                                        onClick = {

                                            reviewToDelete =
                                                review
                                        },

                                        modifier =
                                            Modifier
                                                .fillMaxWidth(),

                                        colors =
                                            ButtonDefaults
                                                .buttonColors(

                                                    containerColor =
                                                        Color(
                                                            0xFFC62828
                                                        )
                                                ),

                                        shape =
                                            RoundedCornerShape(
                                                16.dp
                                            )

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Delete,

                                            contentDescription =
                                                null
                                        )

                                        Spacer(
                                            Modifier.width(
                                                7.dp
                                            )
                                        )

                                        Text(
                                            "Delete Review"
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
}