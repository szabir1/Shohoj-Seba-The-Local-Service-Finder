package com.example.shohojseba.ui.customer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.ui.customer.components.ServiceCard

import com.example.shohojseba.viewmodel.FavoriteViewModel
import com.example.shohojseba.viewmodel.ReviewViewModel
import com.example.shohojseba.viewmodel.ServiceViewModel


@Composable
fun ServiceScreen(

    categoryId: Long,

    areaId: Long,

    areaName: String,


    // =====================================================
    // PROMOTION
    // =====================================================

    isPromotion: Boolean = false,


    onBookServiceClick: (

        providerId: Long,

        serviceId: Long,

        serviceName: String,

        providerName: String,

        originalPrice: Double,

        discountPercent: Double,

        finalPrice: Double

    ) -> Unit,


    onReviewsClick: (

        providerId: Long,

        providerName: String

    ) -> Unit,


    viewModel: ServiceViewModel =
        viewModel(),

    reviewViewModel: ReviewViewModel =
        viewModel(),

    favoriteViewModel: FavoriteViewModel =
        viewModel()

) {


    val services by
    viewModel
        .services
        .collectAsState()


    val isLoading by
    viewModel
        .isLoading
        .collectAsState()


    val favoriteIds by
    favoriteViewModel
        .favoriteIds
        .collectAsState()


    // =====================================================
    // COLORS
    // =====================================================

    val primary =
        Color(
            0xFF00897B
        )


    val darkPrimary =
        Color(
            0xFF00695C
        )


    val textSecondary =
        Color(
            0xFF66706D
        )


    val background =
        Color(
            0xFFF7FBFA
        )


    // =====================================================
    // PROMOTION CONFIGURATION
    // =====================================================

    val promotionDiscount =

        if (
            isPromotion
        ) {

            20.0

        } else {

            0.0

        }


    // =====================================================
    // LOAD FAVORITES
    // =====================================================

    LaunchedEffect(Unit) {

        favoriteViewModel
            .loadFavoriteIds()

    }


    // =====================================================
    // LOAD SERVICES
    // =====================================================

    LaunchedEffect(

        categoryId,

        areaId

    ) {


        if (
            areaId > 0
        ) {

            viewModel
                .loadServicesByCategoryAndArea(

                    categoryId =
                        categoryId,

                    areaId =
                        areaId

                )

        } else {

            viewModel
                .loadServicesByCategory(
                    categoryId
                )

        }

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


    // =====================================================
    // SCREEN
    // =====================================================

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        listOf(

                            Color(
                                0xFFE8FAF6
                            ),

                            background,

                            Color.White

                        )

                    )

                )
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(

                    horizontal =
                        20.dp

                )

    ) {


        Spacer(
            Modifier.height(
                24.dp
            )
        )


        // =====================================================
        // HEADER
        // =====================================================

        Text(

            text =

                if (
                    isPromotion
                ) {

                    "Special Offer"

                } else {

                    "Find a Service"

                },

            style =
                MaterialTheme
                    .typography
                    .headlineMedium,

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

                if (
                    isPromotion
                ) {

                    "Choose a trusted professional and enjoy your exclusive discount."

                } else {

                    "Compare providers and choose the right professional for your home."

                },

            style =
                MaterialTheme
                    .typography
                    .bodyLarge,

            color =
                textSecondary

        )


        // =====================================================
        // AREA
        // =====================================================

        if (
            areaName.isNotBlank()
        ) {


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            Surface(

                shape =
                    RoundedCornerShape(
                        50.dp
                    ),

                color =
                    Color.White,

                shadowElevation =
                    2.dp

            ) {

                Row(

                    modifier =
                        Modifier.padding(

                            horizontal =
                                13.dp,

                            vertical =
                                9.dp

                        ),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Surface(

                        modifier =
                            Modifier.size(
                                28.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            Color(
                                0xFFE2F5F1
                            )

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.LocationOn,

                                contentDescription =
                                    null,

                                tint =
                                    primary,

                                modifier =
                                    Modifier.size(
                                        17.dp
                                    )

                            )

                        }

                    }


                    Spacer(
                        Modifier.width(
                            8.dp
                        )
                    )


                    Column {

                        Text(

                            text =
                                "Showing providers in",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelSmall,

                            color =
                                textSecondary

                        )


                        Text(

                            text =
                                areaName,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            fontWeight =
                                FontWeight.SemiBold,

                            color =
                                darkPrimary

                        )

                    }

                }

            }

        }


        // =====================================================
        // PROMOTION BANNER
        // =====================================================

        if (
            isPromotion
        ) {


            Spacer(
                Modifier.height(
                    18.dp
                )
            )


            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color.Transparent

                    )

            ) {


                Box(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(

                                Brush.horizontalGradient(

                                    listOf(

                                        Color(
                                            0xFF00695C
                                        ),

                                        Color(
                                            0xFF26A69A
                                        )

                                    )

                                )

                            )
                            .padding(
                                18.dp
                            )

                ) {


                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(
                                    52.dp
                                ),

                            shape =
                                RoundedCornerShape(
                                    17.dp
                                ),

                            color =
                                Color.White.copy(
                                    alpha =
                                        0.16f
                                )

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.LocalOffer,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color.White,

                                    modifier =
                                        Modifier.size(
                                            27.dp
                                        )

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

                            Text(

                                text =
                                    "20% OFF Cleaning",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.White

                            )


                            Spacer(
                                Modifier.height(
                                    3.dp
                                )
                            )


                            Text(

                                text =
                                    "The discounted price is already applied to every service below.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    Color.White.copy(
                                        alpha =
                                            0.88f
                                    )

                            )

                        }


                        Surface(

                            shape =
                                RoundedCornerShape(
                                    50.dp
                                ),

                            color =
                                Color.White

                        ) {

                            Text(

                                text =
                                    "SAVE 20%",

                                modifier =
                                    Modifier.padding(

                                        horizontal =
                                            10.dp,

                                        vertical =
                                            6.dp

                                    ),

                                color =
                                    darkPrimary,

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }

            }

        }


        Spacer(
            Modifier.height(
                24.dp
            )
        )


        // =====================================================
        // RESULTS HEADER
        // =====================================================

        if (
            !isLoading &&
            services.isNotEmpty()
        ) {

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Column {

                    Text(

                        text =
                            "Available Professionals",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        fontWeight =
                            FontWeight.Bold

                    )


                    Spacer(
                        Modifier.height(
                            2.dp
                        )
                    )


                    Text(

                        text =

                            if (
                                services.size == 1
                            ) {

                                "1 service found"

                            } else {

                                "${services.size} services found"

                            },

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            textSecondary

                    )

                }


                Surface(

                    shape =
                        RoundedCornerShape(
                            50.dp
                        ),

                    color =
                        Color(
                            0xFFE2F5F1
                        )

                ) {

                    Text(

                        text =
                            "${services.size}",

                        modifier =
                            Modifier.padding(

                                horizontal =
                                    13.dp,

                                vertical =
                                    7.dp

                            ),

                        color =
                            darkPrimary,

                        fontWeight =
                            FontWeight.Bold

                    )

                }

            }


            Spacer(
                Modifier.height(
                    8.dp
                )
            )

        }


        when {


            // =================================================
            // LOADING
            // =================================================

            isLoading -> {


                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            24.dp
                        ),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color.White

                        )

                ) {

                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    44.dp
                                ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {


                        CircularProgressIndicator(

                            color =
                                primary

                        )


                        Spacer(
                            Modifier.height(
                                14.dp
                            )
                        )


                        Text(

                            text =
                                "Finding available providers...",

                            color =
                                textSecondary

                        )

                    }

                }

            }


            // =================================================
            // EMPTY
            // =================================================

            services.isEmpty() -> {


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
                            2.dp
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
                                    72.dp
                                ),

                            shape =
                                RoundedCornerShape(
                                    24.dp
                                ),

                            color =
                                Color(
                                    0xFFE7F7F4
                                )

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Search,

                                    contentDescription =
                                        null,

                                    tint =
                                        primary,

                                    modifier =
                                        Modifier.size(
                                            34.dp
                                        )

                                )

                            }

                        }


                        Spacer(
                            Modifier.height(
                                16.dp
                            )
                        )


                        Text(

                            text =
                                "No services available",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge,

                            fontWeight =
                                FontWeight.Bold

                        )


                        Spacer(
                            Modifier.height(
                                7.dp
                            )
                        )


                        Text(

                            text =

                                if (
                                    areaName.isNotBlank()
                                ) {

                                    "We couldn't find a provider for this service in $areaName right now."

                                } else {

                                    "We couldn't find any services in this category right now."

                                },

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


            // =================================================
            // SERVICE LIST
            // =================================================

            else -> {


                services.forEach { service ->


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


                    val isFavorite =

                        favoriteIds
                            .contains(
                                service.service_id
                            )


                    // =================================================
                    // PRICE CALCULATION
                    // =================================================

                    val originalPrice =
                        service.price


                    val finalPrice =

                        if (
                            isPromotion
                        ) {

                            originalPrice *
                                    (
                                            1.0 -
                                                    promotionDiscount /
                                                    100.0
                                            )

                        } else {

                            originalPrice

                        }


                    // =================================================
                    // SERVICE CARD
                    // =================================================

                    ServiceCard(


                        title =
                            service.service_name,


                        description =
                            service.description
                                ?: "Professional service for your home",


                        price =

                            if (
                                isPromotion
                            ) {

                                "%.0f"
                                    .format(
                                        finalPrice
                                    )

                            } else {

                                service.price
                                    .toString()

                            },


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
                            isFavorite,


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

                                service.provider_name,

                                originalPrice,

                                promotionDiscount,

                                finalPrice

                            )

                        },


                        onReviewsClick = {


                            onReviewsClick(

                                service.provider_id,

                                service.provider_name

                            )

                        },


                        // =============================================
                        // PROMOTION DISPLAY
                        // =============================================

                        isPromotion =
                            isPromotion,


                        originalPrice =
                            originalPrice,


                        discountPercent =
                            promotionDiscount

                    )


                    Spacer(
                        Modifier.height(
                            4.dp
                        )
                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                30.dp
            )
        )

    }

}