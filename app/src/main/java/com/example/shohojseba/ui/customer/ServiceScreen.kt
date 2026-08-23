package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.ui.customer.components.ServiceCard
import com.example.shohojseba.viewmodel.ReviewViewModel
import com.example.shohojseba.viewmodel.ServiceViewModel

@Composable
fun ServiceScreen(

    categoryId: Long,

    areaId: Long,

    areaName: String,

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

    viewModel: ServiceViewModel =
        viewModel(),

    reviewViewModel: ReviewViewModel =
        viewModel()

) {

    val services by
    viewModel.services
        .collectAsState()

    val isLoading by
    viewModel.isLoading
        .collectAsState()


    // =====================================================
    // LOAD FILTERED SERVICES
    // =====================================================

    LaunchedEffect(
        categoryId,
        areaId
    ) {

        if (areaId > 0L) {

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
    // RATINGS
    // =====================================================

    LaunchedEffect(services) {

        services

            .map {
                it.provider_id
            }

            .distinct()

            .forEach {

                reviewViewModel
                    .loadProviderRating(
                        it
                    )

            }

    }


    Column(

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
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)

    ) {

        Text(

            text =
                "Services",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium

        )


        if (areaName.isNotBlank()) {

            Spacer(
                Modifier.height(8.dp)
            )

            Row {

                Icon(

                    imageVector =
                        Icons.Default.LocationOn,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF007A7A)

                )

                Spacer(
                    Modifier.width(5.dp)
                )

                Text(

                    text =
                        "Available in $areaName",

                    color =
                        Color(0xFF007A7A)

                )

            }

        }


        Spacer(
            Modifier.height(20.dp)
        )


        when {

            isLoading -> {

                Box(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                50.dp
                            )

                ) {

                    CircularProgressIndicator(

                        color =
                            Color(0xFF007A7A)

                    )

                }

            }


            services.isEmpty() -> {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            25.dp
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
                                "No services available",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium

                        )

                        Spacer(
                            Modifier.height(
                                6.dp
                            )
                        )

                        Text(

                            text =
                                if (
                                    areaName.isNotBlank()
                                ) {

                                    "No providers currently offer this service in $areaName."

                                } else {

                                    "No services found for this category."

                                },

                            color =
                                Color.Gray

                        )

                    }

                }

            }


            else -> {

                services.forEach {
                        service ->


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


        Spacer(
            Modifier.height(
                20.dp
            )
        )

    }

}