package com.example.shohojseba.ui.customer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.shohojseba.data.model.Booking


@Composable
fun CustomerBookingCard(

    booking: Booking,

    onReviewClick: () -> Unit = {},

    isFavorite: Boolean = false,

    onFavoriteClick: () -> Unit = {},

    onAcceptQuotation: () -> Unit = {},

    onRejectQuotation: () -> Unit = {}

) {

    // =====================================================
    // COLORS
    // =====================================================

    val primary =
        Color(0xFF00897B)

    val darkText =
        Color(0xFF17201E)

    val secondaryText =
        Color(0xFF6F7976)

    val softBorder =
        Color(0xFFE4ECEA)


    // =====================================================
    // STATUS COLOR
    // =====================================================

    val statusColor =
        when (booking.status) {

            "Accepted" ->
                Color(0xFF2E7D32)

            "Rejected" ->
                Color(0xFFC62828)

            "Completed" ->
                Color(0xFF1565C0)

            "Quotation Requested" ->
                Color(0xFFFF8F00)

            "Quotation Sent" ->
                Color(0xFF7B1FA2)

            else ->
                Color(0xFFE58A00)

        }


    // =====================================================
    // STATUS BACKGROUND
    // =====================================================

    val statusBackground =
        when (booking.status) {

            "Accepted" ->
                Color(0xFFE8F5E9)

            "Rejected" ->
                Color(0xFFFFEBEE)

            "Completed" ->
                Color(0xFFE3F2FD)

            "Quotation Requested" ->
                Color(0xFFFFF3E0)

            "Quotation Sent" ->
                Color(0xFFF3E5F5)

            else ->
                Color(0xFFFFF4D8)

        }


    // =====================================================
    // CARD
    // =====================================================

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )

    ) {


        Column(

            modifier =
                Modifier.padding(18.dp),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)

        ) {


            // =================================================
            // TOP AREA
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.Top

            ) {


                // =================================================
                // SERVICE ICON
                // =================================================

                Surface(

                    modifier =
                        Modifier.size(50.dp),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFE3F6F2)

                ) {


                    Box(

                        contentAlignment =
                            Alignment.Center

                    ) {


                        Icon(

                            imageVector =
                                Icons.Default.Work,

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
                    Modifier.width(12.dp)
                )


                // =================================================
                // SERVICE + PROVIDER
                // =================================================

                Column(

                    modifier =
                        Modifier.weight(1f)

                ) {


                    Text(

                        text =
                            booking.service
                                ?.serviceName
                                ?: "Service",

                        fontSize =
                            19.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            darkText

                    )


                    Spacer(
                        Modifier.height(4.dp)
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

                            modifier =
                                Modifier.size(16.dp),

                            tint =
                                secondaryText

                        )


                        Spacer(
                            Modifier.width(5.dp)
                        )


                        Text(

                            text =
                                booking.provider
                                    ?.name
                                    ?: "Unknown provider",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                secondaryText

                        )

                    }

                }


                Spacer(
                    Modifier.width(8.dp)
                )


                // =================================================
                // STATUS
                // =================================================

                Box(

                    modifier =
                        Modifier
                            .background(
                                statusBackground,
                                RoundedCornerShape(50.dp)
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 6.dp
                            )

                ) {


                    Text(

                        text =
                            when (booking.status) {

                                "Quotation Requested" ->
                                    "Requested"

                                "Quotation Sent" ->
                                    "Quotation"

                                else ->
                                    booking.status

                            },

                        color =
                            statusColor,

                        style =
                            MaterialTheme
                                .typography
                                .labelMedium,

                        fontWeight =
                            FontWeight.Bold

                    )

                }

            }


            HorizontalDivider(

                color =
                    softBorder

            )


            // =================================================
            // DATE AND TIME
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)

            ) {


                BookingInfoBox(

                    modifier =
                        Modifier.weight(1f),

                    icon =
                        Icons.Default.CalendarMonth,

                    title =
                        "Date",

                    value =
                        booking.bookingDate,

                    iconColor =
                        primary

                )


                BookingInfoBox(

                    modifier =
                        Modifier.weight(1f),

                    icon =
                        Icons.Default.Schedule,

                    title =
                        "Time",

                    value =
                        booking.bookingTime,

                    iconColor =
                        primary

                )

            }


            // =================================================
            // LOCATION
            // =================================================

            BookingDetailRow(

                icon =
                    Icons.Default.LocationOn,

                label =
                    "Service location",

                value =
                    booking.address,

                iconColor =
                    Color(0xFFE53935)

            )


            // =================================================
            // PHONE
            // =================================================

            BookingDetailRow(

                icon =
                    Icons.Default.Phone,

                label =
                    "Provider contact",

                value =
                    booking.provider
                        ?.phone
                        ?: "No phone available",

                iconColor =
                    primary

            )


            // =================================================
            // PROBLEM DESCRIPTION
            // =================================================

            if (
                booking.problemDescription
                    .isNotBlank()
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFF6F9F8)

                ) {


                    Row(

                        modifier =
                            Modifier.padding(13.dp),

                        verticalAlignment =
                            Alignment.Top

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(34.dp),

                            shape =
                                CircleShape,

                            color =
                                Color(0xFFE3F6F2)

                        ) {


                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {


                                Icon(

                                    imageVector =
                                        Icons.Default.Work,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(18.dp),

                                    tint =
                                        primary

                                )

                            }

                        }


                        Spacer(
                            Modifier.width(10.dp)
                        )


                        Column {


                            Text(

                                text =
                                    "Service note",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color =
                                    secondaryText

                            )


                            Spacer(
                                Modifier.height(3.dp)
                            )


                            Text(

                                text =
                                    booking.problemDescription,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    darkText

                            )

                        }

                    }

                }

            }


            // =================================================
            // QUOTATION REQUESTED
            // =================================================

            if (
                booking.status ==
                "Quotation Requested"
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFFFF6E5)

                ) {


                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Text(

                            text =
                                "⏳",

                            fontSize =
                                20.sp

                        )


                        Spacer(
                            Modifier.width(10.dp)
                        )


                        Column {


                            Text(

                                text =
                                    "Quotation requested",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFFB66A00)

                            )


                            Spacer(
                                Modifier.height(2.dp)
                            )


                            Text(

                                text =
                                    "Waiting for the provider to send a price.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText

                            )

                        }

                    }

                }

            }


            // =================================================
            // QUOTATION SENT
            // =================================================

            if (
                booking.status ==
                "Quotation Sent"
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(20.dp),

                    color =
                        Color(0xFFF8F0FA)

                ) {


                    Column(

                        modifier =
                            Modifier.padding(16.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)

                    ) {


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            verticalAlignment =
                                Alignment.CenterVertically

                        ) {


                            Text(

                                text =
                                    "Quotation received",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF6A1B9A)

                            )


                            Spacer(
                                Modifier.weight(1f)
                            )


                            Text(

                                text =
                                    "৳${formatPrice(booking.quotedPrice)}",

                                fontSize =
                                    22.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF6A1B9A)

                            )

                        }


                        if (
                            !booking
                                .quotationMessage
                                .isNullOrBlank()
                        ) {


                            Text(

                                text =
                                    booking.quotationMessage
                                        ?: "",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    darkText

                            )

                        }


                        Text(

                            text =
                                "Review the provider's price before accepting.",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                secondaryText

                        )

                    }

                }


                // =================================================
                // ACCEPT / REJECT
                // =================================================

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)

                ) {


                    OutlinedButton(

                        modifier =
                            Modifier
                                .weight(1f)
                                .height(50.dp),

                        onClick =
                            onRejectQuotation,

                        shape =
                            RoundedCornerShape(15.dp),

                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(

                                    contentColor =
                                        Color(0xFFC62828)

                                )

                    ) {


                        Text(

                            text =
                                "Reject",

                            fontWeight =
                                FontWeight.Bold

                        )

                    }


                    Button(

                        modifier =
                            Modifier
                                .weight(1f)
                                .height(50.dp),

                        onClick =
                            onAcceptQuotation,

                        shape =
                            RoundedCornerShape(15.dp),

                        colors =
                            ButtonDefaults
                                .buttonColors(

                                    containerColor =
                                        primary

                                )

                    ) {


                        Text(

                            text =
                                "Accept",

                            fontWeight =
                                FontWeight.Bold

                        )

                    }

                }

            }


            // =================================================
            // ACCEPTED
            // =================================================

            if (
                booking.status ==
                "Accepted"
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFEAF7EC)

                ) {


                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFF2E7D32)

                        )


                        Spacer(
                            Modifier.width(10.dp)
                        )


                        Column {


                            Text(

                                text =
                                    "Booking confirmed",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF2E7D32)

                            )


                            Text(

                                text =
                                    "Your provider has been confirmed for this service.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText

                            )

                        }

                    }

                }

            }


            // =================================================
            // COMPLETED
            // =================================================

            if (
                booking.status ==
                "Completed"
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFEAF7EC)

                ) {


                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFF2E7D32)

                        )


                        Spacer(
                            Modifier.width(10.dp)
                        )


                        Column {


                            Text(

                                text =
                                    "Service completed",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF2E7D32)

                            )


                            Text(

                                text =
                                    "We hope everything went smoothly.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText

                            )

                        }

                    }

                }


                // =================================================
                // FAVORITE
                // =================================================

                OutlinedButton(

                    onClick =
                        onFavoriteClick,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                    shape =
                        RoundedCornerShape(15.dp),

                    colors =
                        ButtonDefaults
                            .outlinedButtonColors(

                                contentColor =
                                    if (isFavorite) {
                                        Color(0xFFE53935)
                                    } else {
                                        primary
                                    }

                            )

                ) {


                    Icon(

                        imageVector =
                            if (isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },

                        contentDescription =
                            null

                    )


                    Spacer(
                        Modifier.width(8.dp)
                    )


                    Text(

                        text =
                            if (isFavorite) {
                                "Saved to Favorites"
                            } else {
                                "Save to Favorites"
                            },

                        fontWeight =
                            FontWeight.SemiBold

                    )

                }


                // =================================================
                // REVIEW
                // =================================================

                Button(

                    onClick =
                        onReviewClick,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                    shape =
                        RoundedCornerShape(15.dp),

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    primary

                            )

                ) {


                    Icon(

                        imageVector =
                            Icons.Default.Star,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFFFFD54F)

                    )


                    Spacer(
                        Modifier.width(8.dp)
                    )


                    Text(

                        text =
                            "Leave Review",

                        fontWeight =
                            FontWeight.Bold

                    )

                }

            }


            // =================================================
            // REJECTED
            // =================================================

            if (
                booking.status ==
                "Rejected"
            ) {


                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    color =
                        Color(0xFFFFEBEE)

                ) {


                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Text(

                            text =
                                "✕",

                            fontSize =
                                22.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color(0xFFC62828)

                        )


                        Spacer(
                            Modifier.width(10.dp)
                        )


                        Column {


                            Text(

                                text =
                                    if (
                                        booking.quotationRequested
                                    ) {
                                        "Quotation rejected"
                                    } else {
                                        "Booking rejected"
                                    },

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFFC62828)

                            )


                            Text(

                                text =
                                    if (
                                        booking.quotationRequested
                                    ) {
                                        "This quotation request was not accepted."
                                    } else {
                                        "This service request was not accepted."
                                    },

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText

                            )

                        }

                    }

                }

            }

        }

    }

}


// =====================================================
// DATE / TIME INFO BOX
// =====================================================

@Composable
private fun BookingInfoBox(

    modifier: Modifier = Modifier,

    icon: ImageVector,

    title: String,

    value: String,

    iconColor: Color

) {


    Surface(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(16.dp),

        color =
            Color(0xFFF6F9F8)

    ) {


        Row(

            modifier =
                Modifier.padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically

        ) {


            Icon(

                imageVector =
                    icon,

                contentDescription =
                    null,

                modifier =
                    Modifier.size(21.dp),

                tint =
                    iconColor

            )


            Spacer(
                Modifier.width(9.dp)
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
                        Color(0xFF7A8582)

                )


                Text(

                    text =
                        value,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        Color(0xFF17201E)

                )

            }

        }

    }

}


// =====================================================
// DETAIL ROW
// =====================================================

@Composable
private fun BookingDetailRow(

    icon: ImageVector,

    label: String,

    value: String,

    iconColor: Color

) {


    Row(

        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically

    ) {


        Surface(

            modifier =
                Modifier.size(38.dp),

            shape =
                CircleShape,

            color =
                iconColor.copy(
                    alpha = 0.10f
                )

        ) {


            Box(

                contentAlignment =
                    Alignment.Center

            ) {


                Icon(

                    imageVector =
                        icon,

                    contentDescription =
                        null,

                    modifier =
                        Modifier.size(20.dp),

                    tint =
                        iconColor

                )

            }

        }


        Spacer(
            Modifier.width(11.dp)
        )


        Column(

            modifier =
                Modifier.weight(1f)

        ) {


            Text(

                text =
                    label,

                style =
                    MaterialTheme
                        .typography
                        .labelSmall,

                color =
                    Color(0xFF7A8582)

            )


            Spacer(
                Modifier.height(1.dp)
            )


            Text(

                text =
                    value,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                fontWeight =
                    FontWeight.Medium,

                color =
                    Color(0xFF17201E)

            )

        }

    }

}


// =====================================================
// SAFE PRICE DISPLAY
// =====================================================

private fun formatPrice(

    price: Double?

): String {


    if (
        price == null
    ) {

        return "0"

    }


    return if (
        price % 1.0 == 0.0
    ) {

        price
            .toLong()
            .toString()

    } else {

        String.format(
            "%.2f",
            price
        )

    }

}