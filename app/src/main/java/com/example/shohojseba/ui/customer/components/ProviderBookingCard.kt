package com.example.shohojseba.ui.provider.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.example.shohojseba.data.model.Booking


@Composable
fun ProviderBookingCard(

    booking: Booking,

    onAccept: () -> Unit,

    onReject: () -> Unit,

    onComplete: () -> Unit,

    onSendQuotation: (
        price: Double,
        message: String
    ) -> Unit = { _, _ -> }

) {

    // =====================================================
    // QUOTATION INPUT
    // =====================================================

    var quotationPrice by remember(
        booking.bookingId
    ) {

        mutableStateOf("")

    }


    var quotationMessage by remember(
        booking.bookingId
    ) {

        mutableStateOf("")

    }


    // =====================================================
    // PROMOTION CHECK
    // =====================================================

    val hasPromotion =

        booking.discountPercent > 0.0 &&
                booking.originalPrice != null &&
                booking.finalPrice != null &&
                booking.finalPrice < booking.originalPrice


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
                Color(0xFF6A1B9A)

            else ->
                Color(0xFFE58A00)

        }


    val statusBackground =
        when (booking.status) {

            "Accepted" ->
                Color(0xFFE4F3E7)

            "Rejected" ->
                Color(0xFFFFEBEE)

            "Completed" ->
                Color(0xFFE3F2FD)

            "Quotation Requested" ->
                Color(0xFFFFF3D6)

            "Quotation Sent" ->
                Color(0xFFF3E5F5)

            else ->
                Color(0xFFFFF4D6)

        }


    // =====================================================
    // SERVICE ICON
    // =====================================================

    val serviceName =
        booking.service
            ?.serviceName
            ?: "Service"


    val serviceIcon =
        when {

            serviceName.contains(
                "clean",
                ignoreCase = true
            ) ->
                "🧹"


            serviceName.contains(
                "ac",
                ignoreCase = true
            ) ->
                "❄️"


            serviceName.contains(
                "plumb",
                ignoreCase = true
            ) ->
                "🔧"


            serviceName.contains(
                "electric",
                ignoreCase = true
            ) ->
                "⚡"


            serviceName.contains(
                "medicine",
                ignoreCase = true
            ) ->
                "💊"


            else ->
                "🛠️"

        }


    // =====================================================
    // MAIN CARD
    // =====================================================

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

            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        18.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )

        ) {


            // =================================================
            // HEADER
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.Top

            ) {


                // SERVICE ICON

                Surface(

                    modifier =
                        Modifier.size(
                            52.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    color =
                        Color(
                            0xFFE1F5F1
                        )

                ) {

                    Box(

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(

                            text =
                                serviceIcon,

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineSmall

                        )

                    }

                }


                Spacer(
                    Modifier.width(
                        12.dp
                    )
                )


                // SERVICE + CUSTOMER

                Column(

                    modifier =
                        Modifier.weight(
                            1f
                        )

                ) {

                    Text(

                        text =
                            serviceName,

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
                            3.dp
                        )
                    )


                    Text(

                        text =
                            "Customer: ${
                                booking.customer
                                    ?.name
                                    ?: "Unknown"
                            }",

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,

                        color =
                            Color(
                                0xFF747C79
                            )

                    )

                }


                Spacer(
                    Modifier.width(
                        8.dp
                    )
                )


                // STATUS CHIP

                Surface(

                    shape =
                        RoundedCornerShape(
                            50.dp
                        ),

                    color =
                        statusBackground

                ) {

                    Text(

                        text =
                            booking.status,

                        modifier =
                            Modifier.padding(

                                horizontal =
                                    12.dp,

                                vertical =
                                    7.dp

                            ),

                        color =
                            statusColor,

                        style =
                            MaterialTheme
                                .typography
                                .labelLarge,

                        fontWeight =
                            FontWeight.SemiBold

                    )

                }

            }


            HorizontalDivider(

                color =
                    Color(
                        0xFFE7ECEA
                    )

            )


            // =================================================
            // DATE + TIME ON SAME ROW
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        12.dp
                    )

            ) {


                BookingInfoBox(

                    modifier =
                        Modifier.weight(
                            1f
                        ),

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.CalendarMonth,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFF00796B
                                ),

                            modifier =
                                Modifier.size(
                                    19.dp
                                )

                        )

                    },

                    label =
                        "Date",

                    value =
                        booking.bookingDate

                )


                BookingInfoBox(

                    modifier =
                        Modifier.weight(
                            1f
                        ),

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.Schedule,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFF00796B
                                ),

                            modifier =
                                Modifier.size(
                                    19.dp
                                )

                        )

                    },

                    label =
                        "Time",

                    value =
                        booking.bookingTime

                )

            }


            // =================================================
            // LOCATION
            // =================================================

            CompactInfoRow(

                icon = {

                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null,

                        tint =
                            Color(
                                0xFF00897B
                            )

                    )

                },

                title =
                    "Service location",

                value =
                    booking.address

            )


            // =================================================
            // PHONE
            // =================================================

            val customerPhone =
                booking.customer
                    ?.phone
                    .orEmpty()


            if (
                customerPhone.isNotBlank()
            ) {

                CompactInfoRow(

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.Phone,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFF00897B
                                )

                        )

                    },

                    title =
                        "Customer phone",

                    value =
                        customerPhone

                )

            }


            // =================================================
            // CUSTOMER REQUEST
            // =================================================

            if (
                booking.problemDescription
                    .isNotBlank()
            ) {

                Surface(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    color =
                        Color(
                            0xFFF6F8F7
                        )

                ) {

                    Row(

                        modifier =
                            Modifier.padding(
                                14.dp
                            ),

                        verticalAlignment =
                            Alignment.Top

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Work,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFF60706C
                                ),

                            modifier =
                                Modifier.size(
                                    20.dp
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
                                    "Customer's request",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF4B5552
                                    )

                            )


                            Spacer(
                                Modifier.height(
                                    3.dp
                                )
                            )


                            Text(

                                text =
                                    booking.problemDescription,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    Color(
                                        0xFF606865
                                    )

                            )

                        }

                    }

                }

            }


            // =================================================
            // PROMOTIONAL BOOKING
            // =================================================

            if (
                hasPromotion &&
                !booking.quotationRequested
            ) {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(
                                    0xFFE5F7F3
                                )

                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                15.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                8.dp
                            )

                    ) {


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
                                    Color(
                                        0xFF00897B
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
                                    "Special Offer",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF00695C
                                    )

                            )

                        }


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Text(

                                text =
                                    "Regular price",

                                color =
                                    Color(
                                        0xFF68726F
                                    )

                            )


                            Text(

                                text =
                                    "৳%.0f".format(
                                        booking.originalPrice
                                            ?: 0.0
                                    )

                            )

                        }


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Text(

                                text =
                                    "Discount",

                                color =
                                    Color(
                                        0xFF00897B
                                    )

                            )


                            Text(

                                text =
                                    "-%.0f%%".format(
                                        booking.discountPercent
                                    ),

                                color =
                                    Color(
                                        0xFF00897B
                                    ),

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }


                        HorizontalDivider()


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
                                    "Agreed price",

                                fontWeight =
                                    FontWeight.Bold

                            )


                            Text(

                                text =
                                    "৳%.0f".format(
                                        booking.finalPrice
                                            ?: 0.0
                                    ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF00796B
                                    )

                            )

                        }

                    }

                }

            }


            // =================================================
            // STATUS ACTIONS
            // =================================================

            when (
                booking.status
            ) {


                // =============================================
                // PENDING
                // =============================================

                "Pending" -> {

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.spacedBy(
                                10.dp
                            )

                    ) {


                        OutlinedButton(

                            modifier =
                                Modifier
                                    .weight(
                                        1f
                                    )
                                    .height(
                                        50.dp
                                    ),

                            onClick =
                                onReject,

                            shape =
                                RoundedCornerShape(
                                    15.dp
                                ),

                            colors =
                                ButtonDefaults
                                    .outlinedButtonColors(

                                        contentColor =
                                            Color(
                                                0xFFC62828
                                            )

                                    )

                        ) {

                            Text(

                                text =
                                    "Reject",

                                fontWeight =
                                    FontWeight.SemiBold

                            )

                        }


                        Button(

                            modifier =
                                Modifier
                                    .weight(
                                        1f
                                    )
                                    .height(
                                        50.dp
                                    ),

                            onClick =
                                onAccept,

                            shape =
                                RoundedCornerShape(
                                    15.dp
                                ),

                            colors =
                                ButtonDefaults
                                    .buttonColors(

                                        containerColor =
                                            Color(
                                                0xFF00897B
                                            )

                                    )

                        ) {

                            Text(

                                text =
                                    "Accept",

                                fontWeight =
                                    FontWeight.SemiBold

                            )

                        }

                    }

                }


                // =============================================
                // QUOTATION REQUESTED
                // =============================================

                "Quotation Requested" -> {

                    Card(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            ),

                        colors =
                            CardDefaults.cardColors(

                                containerColor =
                                    Color(
                                        0xFFFFF8E1
                                    )

                            )

                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    16.dp
                                ),

                            verticalArrangement =
                                Arrangement.spacedBy(
                                    12.dp
                                )

                        ) {


                            Text(

                                text =
                                    "💰 Custom Quotation Requested",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold

                            )


                            Text(

                                text =
                                    "The customer requested a custom price for this service.",

                                color =
                                    Color(
                                        0xFF6F7472
                                    ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium

                            )


                            OutlinedTextField(

                                value =
                                    quotationPrice,

                                onValueChange = {

                                    quotationPrice =
                                        it

                                },

                                label = {

                                    Text(
                                        "Quoted Price"
                                    )

                                },

                                prefix = {

                                    Text(
                                        "৳ "
                                    )

                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                singleLine =
                                    true,

                                shape =
                                    RoundedCornerShape(
                                        14.dp
                                    )

                            )


                            OutlinedTextField(

                                value =
                                    quotationMessage,

                                onValueChange = {

                                    quotationMessage =
                                        it

                                },

                                label = {

                                    Text(
                                        "Quotation Message"
                                    )

                                },

                                placeholder = {

                                    Text(
                                        "Explain the proposed price..."
                                    )

                                },

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(
                                            110.dp
                                        ),

                                shape =
                                    RoundedCornerShape(
                                        14.dp
                                    )

                            )


                            Button(

                                onClick = {

                                    val price =
                                        quotationPrice
                                            .toDoubleOrNull()


                                    if (
                                        price != null &&
                                        price > 0 &&
                                        quotationMessage
                                            .isNotBlank()
                                    ) {

                                        onSendQuotation(

                                            price,

                                            quotationMessage

                                        )

                                    }

                                },

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(
                                            50.dp
                                        ),

                                shape =
                                    RoundedCornerShape(
                                        15.dp
                                    ),

                                colors =
                                    ButtonDefaults
                                        .buttonColors(

                                            containerColor =
                                                Color(
                                                    0xFFFF8F00
                                                )

                                        )

                            ) {

                                Text(

                                    text =
                                        "Send Quotation",

                                    fontWeight =
                                        FontWeight.Bold

                                )

                            }


                            OutlinedButton(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                onClick =
                                    onReject,

                                shape =
                                    RoundedCornerShape(
                                        15.dp
                                    )

                            ) {

                                Text(
                                    "Reject Request"
                                )

                            }

                        }

                    }

                }


                // =============================================
                // QUOTATION SENT
                // =============================================

                "Quotation Sent" -> {

                    Surface(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        color =
                            Color(
                                0xFFF5EAF7
                            )

                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    14.dp
                                ),

                            verticalArrangement =
                                Arrangement.spacedBy(
                                    5.dp
                                )

                        ) {

                            Text(

                                text =
                                    "📨 Quotation sent",

                                color =
                                    Color(
                                        0xFF6A1B9A
                                    ),

                                fontWeight =
                                    FontWeight.Bold

                            )


                            booking.quotedPrice
                                ?.let { price ->

                                    Text(

                                        text =
                                            "Quoted price: ৳%.0f"
                                                .format(
                                                    price
                                                ),

                                        fontWeight =
                                            FontWeight.SemiBold

                                    )

                                }


                            booking.quotationMessage
                                ?.takeIf {

                                    it.isNotBlank()

                                }
                                ?.let { message ->

                                    Text(

                                        text =
                                            message,

                                        color =
                                            Color(
                                                0xFF696E6C
                                            )

                                    )

                                }


                            Text(

                                text =
                                    "Waiting for the customer to respond.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    Color(
                                        0xFF7A807E
                                    )

                            )

                        }

                    }

                }


                // =============================================
                // ACCEPTED
                // =============================================

                "Accepted" -> {

                    Button(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    52.dp
                                ),

                        onClick =
                            onComplete,

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            ButtonDefaults
                                .buttonColors(

                                    containerColor =
                                        Color(
                                            0xFF1565C0
                                        )

                                )

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null

                        )


                        Spacer(
                            Modifier.width(
                                8.dp
                            )
                        )


                        Text(

                            text =
                                "Complete Job",

                            fontWeight =
                                FontWeight.Bold

                        )

                    }

                }


                // =============================================
                // COMPLETED
                // =============================================

                "Completed" -> {

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(
                                    34.dp
                                ),

                            shape =
                                CircleShape,

                            color =
                                Color(
                                    0xFFE3F2FD
                                )

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.CheckCircle,

                                    contentDescription =
                                        null,

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


                        Spacer(
                            Modifier.width(
                                10.dp
                            )
                        )


                        Column {

                            Text(

                                text =
                                    "Service completed",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF1565C0
                                    )

                            )


                            Text(

                                text =
                                    "This job has been successfully completed.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    Color(
                                        0xFF707775
                                    )

                            )

                        }

                    }

                }


                // =============================================
                // REJECTED
                // =============================================

                "Rejected" -> {

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(
                                    34.dp
                                ),

                            shape =
                                CircleShape,

                            color =
                                Color(
                                    0xFFFFEBEE
                                )

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Text(
                                    "✕",
                                    color =
                                        Color(
                                            0xFFC62828
                                        ),
                                    fontWeight =
                                        FontWeight.Bold
                                )

                            }

                        }


                        Spacer(
                            Modifier.width(
                                10.dp
                            )
                        )


                        Column {

                            Text(

                                text =
                                    "Booking rejected",

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFFC62828
                                    )

                            )


                            Text(

                                text =
                                    "This request is no longer active.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    Color(
                                        0xFF707775
                                    )

                            )

                        }

                    }

                }

            }

        }

    }

}


// =========================================================
// DATE / TIME BOX
// =========================================================

@Composable
private fun BookingInfoBox(

    modifier: Modifier =
        Modifier,

    icon: @Composable () -> Unit,

    label: String,

    value: String

) {

    Surface(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                15.dp
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
                        label,

                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,

                    color =
                        Color(
                            0xFF808784
                        )

                )


                Text(

                    text =
                        value,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    fontWeight =
                        FontWeight.SemiBold

                )

            }

        }

    }

}


// =========================================================
// COMPACT INFORMATION ROW
// =========================================================

@Composable
private fun CompactInfoRow(

    icon: @Composable () -> Unit,

    title: String,

    value: String

) {

    Row(

        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically

    ) {


        Box(

            modifier =
                Modifier.size(
                    30.dp
                ),

            contentAlignment =
                Alignment.Center

        ) {

            icon()

        }


        Spacer(
            Modifier.width(
                8.dp
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
                    title,

                style =
                    MaterialTheme
                        .typography
                        .labelSmall,

                color =
                    Color(
                        0xFF848B88
                    )

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
                    Color(
                        0xFF313735
                    )

            )

        }

    }

}