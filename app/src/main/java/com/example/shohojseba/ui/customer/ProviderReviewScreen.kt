package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.viewmodel.ReviewViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderReviewsScreen(

    providerId: Long,

    providerName: String,

    viewModel: ReviewViewModel =
        viewModel()

) {

    val reviews by
    viewModel.providerReviews

    val isLoading by
    viewModel.isLoading

    LaunchedEffect(providerId) {

        viewModel.loadProviderReviews(
            providerId
        )

    }

    val averageRating =

        if (reviews.isNotEmpty()) {

            reviews
                .map {
                    it.rating
                }
                .average()

        } else {

            0.0

        }

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Provider Reviews"
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

                reviews.isEmpty() -> {

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

                                Text(

                                    text = "⭐",

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
                                        "No reviews yet",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleLarge

                                )

                                Spacer(
                                    Modifier.height(
                                        5.dp
                                    )
                                )

                                Text(

                                    text =
                                        "This provider has not received any reviews yet.",

                                    color =
                                        Color.Gray

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
                                20.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                16.dp
                            )

                    ) {

                        // PROVIDER SUMMARY

                        item {

                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        26.dp
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
                                            5.dp
                                        )

                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            22.dp
                                        )

                                ) {

                                    Text(

                                        text =
                                            "👤 $providerName",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge

                                    )

                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )

                                    Row(

                                        verticalAlignment =
                                            Alignment.CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Star,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(
                                                    0xFFFFC107
                                                )

                                        )

                                        Spacer(
                                            Modifier.width(
                                                7.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                String.format(

                                                    Locale.US,

                                                    "%.1f",

                                                    averageRating

                                                ),

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .headlineSmall

                                        )

                                        Spacer(
                                            Modifier.width(
                                                8.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                "(${reviews.size} ${
                                                    if (
                                                        reviews.size == 1
                                                    )
                                                        "review"
                                                    else
                                                        "reviews"
                                                })",

                                            color =
                                                Color.Gray

                                        )

                                    }

                                }

                            }

                        }

                        // REVIEW CARDS

                        items(

                            items = reviews,

                            key = {
                                it.reviewId
                            }

                        ) { review ->

                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        22.dp
                                    ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
                                            4.dp
                                        )

                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            18.dp
                                        )

                                ) {

                                    Row {

                                        for (
                                        star in 1..5
                                        ) {

                                            Icon(

                                                imageVector =
                                                    Icons.Default.Star,

                                                contentDescription =
                                                    null,

                                                tint =
                                                    if (
                                                        star <=
                                                        review.rating
                                                    ) {

                                                        Color(
                                                            0xFFFFC107
                                                        )

                                                    } else {

                                                        Color.LightGray

                                                    },

                                                modifier =
                                                    Modifier.size(
                                                        22.dp
                                                    )

                                            )

                                        }

                                    }

                                    Spacer(
                                        Modifier.height(
                                            12.dp
                                        )
                                    )

                                    Text(

                                        text =
                                            review.comment
                                                ?: "No written comment.",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodyLarge

                                    )

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
                                                "Reviewed on ${
                                                    review.createdAt
                                                        .take(10)
                                                }",

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

}