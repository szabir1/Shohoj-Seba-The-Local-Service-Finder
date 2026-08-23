package com.example.shohojseba.ui.customer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.ui.customer.components.ServiceCard

import com.example.shohojseba.viewmodel.FavoriteViewModel
import com.example.shohojseba.viewmodel.ReviewViewModel


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun FavoritesScreen(

    onBookServiceClick: (
        providerId: Long,
        serviceId: Long,
        serviceName: String,
        providerName: String
    ) -> Unit,

    onReviewsClick: (
        providerId: Long,
        providerName: String
    ) -> Unit,

    favoriteViewModel: FavoriteViewModel =
        viewModel(),

    reviewViewModel: ReviewViewModel =
        viewModel()

) {


    val services by
    favoriteViewModel
        .favoriteServices
        .collectAsState()


    val favoriteIds by
    favoriteViewModel
        .favoriteIds
        .collectAsState()


    val isLoading by
    favoriteViewModel
        .isLoading
        .collectAsState()


    // =====================================================
    // LOAD FAVORITES
    // =====================================================

    LaunchedEffect(
        Unit
    ) {


        favoriteViewModel
            .loadFavoriteServices()

    }


    // =====================================================
    // LOAD RATINGS
    // =====================================================

    LaunchedEffect(
        services
    ) {


        services
            .map {

                it.provider_id

            }
            .distinct()
            .forEach { providerId ->


                reviewViewModel
                    .loadProviderRating(
                        providerId
                    )

            }

    }


    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {


            TopAppBar(

                title = {

                    Text(
                        "My Favorites"
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
                                    0xFFFFF0F2
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


                        CircularProgressIndicator(

                            color =
                                Color(
                                    0xFFE53935
                                )

                        )

                    }

                }


                services.isEmpty() -> {


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
                                        Icons.Default.Favorite,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(
                                            0xFFE53935
                                        ),

                                    modifier =
                                        Modifier.size(
                                            55.dp
                                        )

                                )


                                Spacer(
                                    Modifier.height(
                                        14.dp
                                    )
                                )


                                Text(

                                    text =
                                        "No Favorites Yet",

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
                                        "Tap the heart icon on a service to save it here.",

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
                                8.dp
                            )

                    ) {


                        items(

                            items =
                                services,

                            key = {

                                it.service_id

                            }

                        ) { service ->


                            val averageRating =

                                reviewViewModel
                                    .providerRatings[
                                    service.provider_id
                                ]
                                    ?: 0.0


                            val reviewCount =

                                reviewViewModel
                                    .providerReviewCounts[
                                    service.provider_id
                                ]
                                    ?: 0


                            ServiceCard(


                                title =
                                    service.service_name,


                                description =
                                    service.description
                                        ?: "Professional service for your home",


                                price =
                                    service.price
                                        .toString(),


                                duration =
                                    service.duration,


                                provider =
                                    service.provider_name,


                                phone =
                                    service.provider_phone,


                                experience =
                                    service.experience
                                        .toString(),


                                averageRating =
                                    averageRating,


                                reviewCount =
                                    reviewCount,


                                isVerified =
                                    service.is_verified,


                                availabilityStatus =
                                    service.availability_status,


                                isFavorite =
                                    favoriteIds.contains(
                                        service.service_id
                                    ),


                                onFavoriteClick = {


                                    favoriteViewModel
                                        .toggleFavorite(
                                            service.service_id
                                        )

                                },


                                onBookClick = {


                                    onBookServiceClick(

                                        service.provider_id,

                                        service.service_id,

                                        service.service_name,

                                        service.provider_name

                                    )

                                },


                                onReviewsClick = {


                                    onReviewsClick(

                                        service.provider_id,

                                        service.provider_name

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