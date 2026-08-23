package com.example.shohojseba.ui.customer.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Verified

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ServiceCard(

    title: String,

    description: String,

    price: String,

    duration: String,

    provider: String,

    phone: String,

    experience: String,

    averageRating: Double,

    reviewCount: Int,

    isVerified: Boolean,

    availabilityStatus: String,

    isFavorite: Boolean,

    onFavoriteClick: () -> Unit,

    onBookClick: () -> Unit,

    onReviewsClick: () -> Unit

) {


    val normalizedStatus =
        availabilityStatus
            .uppercase()


    val availabilityText =
        when (
            normalizedStatus
        ) {

            "AVAILABLE" ->
                "🟢 Available"

            "BUSY" ->
                "🟡 Busy"

            "UNAVAILABLE" ->
                "🔴 Unavailable"

            else ->
                "Availability unknown"

        }


    val availabilityColor =
        when (
            normalizedStatus
        ) {

            "AVAILABLE" ->
                Color(
                    0xFF2E7D32
                )

            "BUSY" ->
                Color(
                    0xFFFFA000
                )

            "UNAVAILABLE" ->
                Color(
                    0xFFC62828
                )

            else ->
                Color.Gray

        }


    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical =
                        10.dp
                ),

        shape =
            RoundedCornerShape(
                28.dp
            ),

        elevation =
            CardDefaults
                .cardElevation(
                    8.dp
                )

    ) {


        Column(

            modifier =
                Modifier.padding(
                    20.dp
                )

        ) {


            // =================================================
            // ICON + FAVORITE
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Text(

                    text =
                        "🛠",

                    fontSize =
                        42.sp

                )


                IconButton(

                    onClick =
                        onFavoriteClick

                ) {


                    Icon(

                        imageVector =

                            if (
                                isFavorite
                            ) {

                                Icons.Default.Favorite

                            } else {

                                Icons.Default.FavoriteBorder

                            },

                        contentDescription =

                            if (
                                isFavorite
                            ) {

                                "Remove Favorite"

                            } else {

                                "Add Favorite"

                            },

                        tint =

                            if (
                                isFavorite
                            ) {

                                Color(
                                    0xFFE53935
                                )

                            } else {

                                Color.Gray

                            },

                        modifier =
                            Modifier.size(
                                30.dp
                            )

                    )

                }

            }


            Text(

                text =
                    title,

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


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                TextButton(

                    onClick =
                        onReviewsClick

                ) {

                    Text(

                        text =

                            if (
                                reviewCount > 0
                            ) {

                                "⭐ %.1f ($reviewCount)"
                                    .format(
                                        averageRating
                                    )

                            } else {

                                "⭐ No reviews"

                            }

                    )

                }


                Text(

                    text =
                        "৳$price",

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium

                )

            }


            Spacer(
                Modifier.height(
                    10.dp
                )
            )


            Text(
                text =
                    description
            )


            Spacer(
                Modifier.height(
                    12.dp
                )
            )


            Text(

                text =
                    "⏱ Duration: $duration"

            )


            HorizontalDivider(

                modifier =
                    Modifier.padding(
                        vertical =
                            10.dp
                    )

            )


            Text(

                text =
                    "👤 Provider",

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


            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Text(
                    text =
                        provider
                )


                if (
                    isVerified
                ) {

                    Spacer(
                        Modifier.width(
                            6.dp
                        )
                    )


                    Icon(

                        imageVector =
                            Icons.Default.Verified,

                        contentDescription =
                            "Verified Provider",

                        tint =
                            Color(
                                0xFF1565C0
                            ),

                        modifier =
                            Modifier.size(
                                20.dp
                            )

                    )

                }

            }


            if (
                isVerified
            ) {


                Text(

                    text =
                        "Verified Provider",

                    color =
                        Color(
                            0xFF1565C0
                        ),

                    style =
                        MaterialTheme
                            .typography
                            .labelMedium

                )

            }


            Spacer(
                Modifier.height(
                    6.dp
                )
            )


            Text(
                text =
                    phone
            )


            Text(

                text =
                    "Experience: $experience years"

            )


            Spacer(
                Modifier.height(
                    12.dp
                )
            )


            Text(

                text =
                    availabilityText,

                color =
                    availabilityColor,

                style =
                    MaterialTheme
                        .typography
                        .titleSmall

            )


            if (
                normalizedStatus ==
                "BUSY"
            ) {


                Spacer(
                    Modifier.height(
                        3.dp
                    )
                )


                Text(

                    text =
                        "Provider is currently busy and may take longer to respond.",

                    color =
                        Color.Gray,

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall

                )

            }


            if (
                normalizedStatus ==
                "UNAVAILABLE"
            ) {


                Spacer(
                    Modifier.height(
                        3.dp
                    )
                )


                Text(

                    text =
                        "Provider is not accepting new bookings right now.",

                    color =
                        Color(
                            0xFFC62828
                        ),

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall

                )

            }


            Spacer(
                Modifier.height(
                    15.dp
                )
            )


            Button(

                onClick =
                    onBookClick,

                enabled =
                    normalizedStatus !=
                            "UNAVAILABLE",

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    )

            ) {


                Text(

                    text =

                        if (
                            normalizedStatus ==
                            "UNAVAILABLE"
                        ) {

                            "Currently Unavailable"

                        } else {

                            "Book Service"

                        }

                )

            }

        }

    }

}