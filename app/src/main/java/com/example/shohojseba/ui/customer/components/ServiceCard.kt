package com.example.shohojseba.ui.customer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkHistory

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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

    onReviewsClick: () -> Unit,


    // =====================================================
    // PROMOTION
    // =====================================================

    isPromotion: Boolean = false,

    originalPrice: Double = 0.0,

    discountPercent: Double = 0.0

) {


    val primary =
        Color(0xFF00897B)


    val darkPrimary =
        Color(0xFF00695C)


    val textSecondary =
        Color(0xFF66706D)


    val normalizedStatus =
        availabilityStatus
            .uppercase()


    val availabilityText =
        when (
            normalizedStatus
        ) {

            "AVAILABLE" ->
                "Available now"

            "BUSY" ->
                "Currently busy"

            "UNAVAILABLE" ->
                "Unavailable"

            else ->
                "Status unknown"

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
                    0xFFE58A00
                )

            "UNAVAILABLE" ->
                Color(
                    0xFFC62828
                )

            else ->
                Color.Gray

        }


    val availabilityBackground =
        when (
            normalizedStatus
        ) {

            "AVAILABLE" ->
                Color(
                    0xFFE8F5E9
                )

            "BUSY" ->
                Color(
                    0xFFFFF4E0
                )

            "UNAVAILABLE" ->
                Color(
                    0xFFFFEBEE
                )

            else ->
                Color(
                    0xFFF1F3F2
                )

        }


    val availabilityDot =
        when (
            normalizedStatus
        ) {

            "AVAILABLE" ->
                Color(
                    0xFF43A047
                )

            "BUSY" ->
                Color(
                    0xFFFFA000
                )

            "UNAVAILABLE" ->
                Color(
                    0xFFE53935
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
                        7.dp
                ),

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
                4.dp
            )

    ) {


        Column(

            modifier =
                Modifier.padding(
                    18.dp
                )

        ) {


            // =================================================
            // TOP SECTION
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.Top

            ) {


                // =============================================
                // SERVICE VISUAL
                // =============================================

                Surface(

                    modifier =
                        Modifier.size(
                            66.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            20.dp
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

                        Text(

                            text =
                                serviceEmoji(
                                    title
                                ),

                            fontSize =
                                34.sp

                        )

                    }

                }


                Spacer(
                    Modifier.width(
                        14.dp
                    )
                )


                // =============================================
                // TITLE / PROVIDER
                // =============================================

                Column(

                    modifier =
                        Modifier.weight(
                            1f
                        )

                ) {


                    Text(

                        text =
                            title,

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        fontWeight =
                            FontWeight.Bold,

                        maxLines =
                            2,

                        overflow =
                            TextOverflow.Ellipsis

                    )


                    Spacer(
                        Modifier.height(
                            5.dp
                        )
                    )


                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Icon(

                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                null,

                            tint =
                                textSecondary,

                            modifier =
                                Modifier.size(
                                    16.dp
                                )

                        )


                        Spacer(
                            Modifier.width(
                                4.dp
                            )
                        )


                        Text(

                            text =
                                provider,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                textSecondary,

                            maxLines =
                                1,

                            overflow =
                                TextOverflow.Ellipsis

                        )


                        if (
                            isVerified
                        ) {

                            Spacer(
                                Modifier.width(
                                    5.dp
                                )
                            )


                            Icon(

                                imageVector =
                                    Icons.Default.Verified,

                                contentDescription =
                                    "Verified Provider",

                                tint =
                                    Color(
                                        0xFF1976D2
                                    ),

                                modifier =
                                    Modifier.size(
                                        18.dp
                                    )

                            )

                        }

                    }


                    if (
                        isVerified
                    ) {

                        Spacer(
                            Modifier.height(
                                3.dp
                            )
                        )


                        Text(

                            text =
                                "Verified Provider",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelSmall,

                            color =
                                Color(
                                    0xFF1976D2
                                ),

                            fontWeight =
                                FontWeight.SemiBold

                        )

                    }

                }


                // =============================================
                // FAVORITE
                // =============================================

                Surface(

                    modifier =
                        Modifier.size(
                            44.dp
                        ),

                    shape =
                        CircleShape,

                    color =

                        if (
                            isFavorite
                        ) {

                            Color(
                                0xFFFFEBEE
                            )

                        } else {

                            Color(
                                0xFFF4F7F6
                            )

                        }

                ) {

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

                                    Color(
                                        0xFF66706D
                                    )

                                }

                        )

                    }

                }

            }


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            // =================================================
            // RATING + AVAILABILITY
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Surface(

                    onClick =
                        onReviewsClick,

                    shape =
                        RoundedCornerShape(
                            50.dp
                        ),

                    color =
                        Color(
                            0xFFFFF7E5
                        )

                ) {

                    Row(

                        modifier =
                            Modifier.padding(

                                horizontal =
                                    10.dp,

                                vertical =
                                    7.dp

                            ),

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
                                    0xFFFFA000
                                ),

                            modifier =
                                Modifier.size(
                                    18.dp
                                )

                        )


                        Spacer(
                            Modifier.width(
                                4.dp
                            )
                        )


                        Text(

                            text =

                                if (
                                    reviewCount > 0
                                ) {

                                    "%.1f"
                                        .format(
                                            averageRating
                                        )

                                } else {

                                    "New"

                                },

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color(
                                    0xFF5D4037
                                )

                        )


                        if (
                            reviewCount > 0
                        ) {

                            Text(

                                text =
                                    " ($reviewCount)",

                                color =
                                    textSecondary,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall

                            )

                        }

                    }

                }


                Surface(

                    shape =
                        RoundedCornerShape(
                            50.dp
                        ),

                    color =
                        availabilityBackground

                ) {

                    Row(

                        modifier =
                            Modifier.padding(

                                horizontal =
                                    10.dp,

                                vertical =
                                    7.dp

                            ),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Box(

                            modifier =
                                Modifier
                                    .size(
                                        8.dp
                                    )
                                    .background(

                                        availabilityDot,

                                        CircleShape

                                    )

                        )


                        Spacer(
                            Modifier.width(
                                6.dp
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
                                    .labelMedium,

                            fontWeight =
                                FontWeight.SemiBold

                        )

                    }

                }

            }


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            // =================================================
            // DESCRIPTION
            // =================================================

            Text(

                text =
                    description,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    textSecondary,

                lineHeight =
                    21.sp,

                maxLines =
                    3,

                overflow =
                    TextOverflow.Ellipsis

            )


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            // =================================================
            // DETAILS
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        10.dp
                    )

            ) {


                ServiceInfoChip(

                    modifier =
                        Modifier.weight(
                            1f
                        ),

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.AccessTime,

                            contentDescription =
                                null,

                            tint =
                                primary,

                            modifier =
                                Modifier.size(
                                    18.dp
                                )

                        )

                    },

                    title =
                        "Duration",

                    value =
                        duration

                )


                ServiceInfoChip(

                    modifier =
                        Modifier.weight(
                            1f
                        ),

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.WorkHistory,

                            contentDescription =
                                null,

                            tint =
                                primary,

                            modifier =
                                Modifier.size(
                                    18.dp
                                )

                        )

                    },

                    title =
                        "Experience",

                    value =
                        "$experience yrs"

                )

            }


            Spacer(
                Modifier.height(
                    12.dp
                )
            )


            // =================================================
            // PHONE
            // =================================================

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(

                    imageVector =
                        Icons.Default.Phone,

                    contentDescription =
                        null,

                    tint =
                        textSecondary,

                    modifier =
                        Modifier.size(
                            17.dp
                        )

                )


                Spacer(
                    Modifier.width(
                        7.dp
                    )
                )


                Text(

                    text =
                        phone,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        textSecondary

                )

            }


            // =================================================
            // STATUS MESSAGE
            // =================================================

            if (
                normalizedStatus ==
                "BUSY"
            ) {

                Spacer(
                    Modifier.height(
                        10.dp
                    )
                )


                Text(

                    text =
                        "This provider may take a little longer to respond.",

                    color =
                        Color(
                            0xFFE58A00
                        ),

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
                        10.dp
                    )
                )


                Text(

                    text =
                        "This provider is not accepting new bookings right now.",

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
                    16.dp
                )
            )


            HorizontalDivider(

                color =
                    Color(
                        0xFFEDF1F0
                    )

            )


            Spacer(
                Modifier.height(
                    15.dp
                )
            )


            // =================================================
            // PRICE
            // =================================================

            if (
                isPromotion &&
                originalPrice > 0.0
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


                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.LocalOffer,

                                contentDescription =
                                    null,

                                tint =
                                    primary,

                                modifier =
                                    Modifier.size(
                                        17.dp
                                    )

                            )


                            Spacer(
                                Modifier.width(
                                    5.dp
                                )
                            )


                            Text(

                                text =
                                    "Special offer",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                color =
                                    primary,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }


                        Spacer(
                            Modifier.height(
                                5.dp
                            )
                        )


                        Row(

                            verticalAlignment =
                                Alignment.Bottom

                        ) {


                            Text(

                                text =
                                    "৳%.0f"
                                        .format(
                                            originalPrice
                                        ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    Color.Gray,

                                textDecoration =
                                    TextDecoration.LineThrough

                            )


                            Spacer(
                                Modifier.width(
                                    8.dp
                                )
                            )


                            Text(

                                text =
                                    "৳$price",

                                style =
                                    MaterialTheme
                                        .typography
                                        .headlineSmall,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkPrimary

                            )

                        }

                    }


                    Surface(

                        shape =
                            RoundedCornerShape(
                                50.dp
                            ),

                        color =
                            Color(
                                0xFFE0F2F1
                            )

                    ) {

                        Text(

                            text =
                                "%.0f%% OFF"
                                    .format(
                                        discountPercent
                                    ),

                            modifier =
                                Modifier.padding(

                                    horizontal =
                                        12.dp,

                                    vertical =
                                        7.dp

                                ),

                            color =
                                darkPrimary,

                            fontWeight =
                                FontWeight.Bold,

                            style =
                                MaterialTheme
                                    .typography
                                    .labelLarge

                        )

                    }

                }

            } else {


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
                                "Service price",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary

                        )


                        Text(

                            text =
                                "৳$price",

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineSmall,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                darkPrimary

                        )

                    }

                }

            }


            Spacer(
                Modifier.height(
                    15.dp
                )
            )


            // =================================================
            // BOOK
            // =================================================

            Button(

                onClick =
                    onBookClick,

                enabled =
                    normalizedStatus !=
                            "UNAVAILABLE",

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            54.dp
                        ),

                shape =
                    RoundedCornerShape(
                        17.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            primary,

                        disabledContainerColor =
                            Color(
                                0xFFD8DEDC
                            ),

                        disabledContentColor =
                            Color(
                                0xFF7A8280
                            )

                    )

            ) {


                Text(

                    text =

                        if (
                            normalizedStatus ==
                            "UNAVAILABLE"
                        ) {

                            "Currently Unavailable"

                        } else if (
                            isPromotion
                        ) {

                            "Book Offer • ৳$price"

                        } else {

                            "Book Service • ৳$price"

                        },

                    fontWeight =
                        FontWeight.Bold

                )

            }

        }

    }

}


// =====================================================
// SERVICE INFORMATION CHIP
// =====================================================

@Composable
private fun ServiceInfoChip(

    modifier: Modifier = Modifier,

    icon: @Composable () -> Unit,

    title: String,

    value: String

) {


    Surface(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                16.dp
            ),

        color =
            Color(
                0xFFF4F8F7
            )

    ) {

        Row(

            modifier =
                Modifier.padding(
                    11.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically

        ) {


            icon()


            Spacer(
                Modifier.width(
                    8.dp
                )
            )


            Column {

                Text(

                    text =
                        title,

                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,

                    color =
                        Color(
                            0xFF7B8582
                        )

                )


                Text(

                    text =
                        value,

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,

                    fontWeight =
                        FontWeight.SemiBold,

                    maxLines =
                        1,

                    overflow =
                        TextOverflow.Ellipsis

                )

            }

        }

    }

}


// =====================================================
// SERVICE VISUAL
// =====================================================

private fun serviceEmoji(

    serviceName: String

): String {


    return when {


        serviceName.contains(
            "clean",
            ignoreCase = true
        ) -> {

            "🧹"

        }


        serviceName.contains(
            "ac",
            ignoreCase = true
        ) -> {

            "❄️"

        }


        serviceName.contains(
            "plumb",
            ignoreCase = true
        ) -> {

            "🚰"

        }


        serviceName.contains(
            "electric",
            ignoreCase = true
        ) -> {

            "⚡"

        }


        serviceName.contains(
            "paint",
            ignoreCase = true
        ) -> {

            "🎨"

        }


        serviceName.contains(
            "repair",
            ignoreCase = true
        ) -> {

            "🛠️"

        }


        serviceName.contains(
            "carpenter",
            ignoreCase = true
        ) -> {

            "🪚"

        }


        serviceName.contains(
            "beauty",
            ignoreCase = true
        ) -> {

            "✨"

        }


        serviceName.contains(
            "pest",
            ignoreCase = true
        ) -> {

            "🐜"

        }


        else -> {

            "🔧"

        }

    }

}