package com.example.shohojseba.ui.customer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shohojseba.data.model.Booking

@Composable
fun CustomerBookingCard(

    booking: Booking,

    onReviewClick: () -> Unit = {}

) {

    val statusColor =
        when (booking.status) {

            "Accepted" ->
                Color(0xFF2E7D32)

            "Rejected" ->
                Color(0xFFC62828)

            "Completed" ->
                Color(0xFF1565C0)

            else ->
                Color(0xFFFFA000)
        }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            6.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(18.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            // ================= HEADER =================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Column(

                    modifier =
                        Modifier.weight(1f)

                ) {

                    Text(

                        text =
                            booking.service
                                ?.serviceName
                                ?: "Service",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge

                    )

                    Spacer(
                        Modifier.height(3.dp)
                    )

                    Text(

                        text =
                            "Provider: ${
                                booking.provider
                                    ?.name
                                    ?: "Unknown"
                            }",

                        color = Color.Gray

                    )

                }

                Box(

                    modifier = Modifier

                        .background(

                            statusColor.copy(
                                alpha = 0.15f
                            ),

                            RoundedCornerShape(
                                30.dp
                            )

                        )

                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )

                ) {

                    Text(

                        text = booking.status,

                        color = statusColor

                    )

                }

            }

            HorizontalDivider()

            // ================= DATE =================

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(
                    Icons.Default.CalendarMonth,
                    null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(
                    booking.bookingDate
                )

            }

            // ================= TIME =================

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(
                    Icons.Default.Schedule,
                    null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(
                    booking.bookingTime
                )

            }

            // ================= ADDRESS =================

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(
                    Icons.Default.LocationOn,
                    null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(
                    booking.address
                )

            }

            // ================= PROVIDER PHONE =================

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(
                    Icons.Default.Person,
                    null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(

                    booking.provider
                        ?.phone
                        ?: "No phone available"

                )

            }

            // ================= PROBLEM =================

            Row(

                verticalAlignment =
                    Alignment.Top

            ) {

                Icon(
                    Icons.Default.Work,
                    null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(
                    booking.problemDescription
                )

            }

            // ================= COMPLETED =================

            if (booking.status == "Completed") {

                Spacer(
                    Modifier.height(4.dp)
                )

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFFE8F5E9)

                        )

                ) {

                    Text(

                        text =
                            "✅ Service completed successfully",

                        modifier =
                            Modifier.padding(14.dp),

                        color =
                            Color(0xFF2E7D32)

                    )

                }

                // ================= REVIEW BUTTON =================

                Button(

                    onClick = onReviewClick,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF007A7A)

                        )

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Star,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFFFFC107)

                    )

                    Spacer(
                        Modifier.width(8.dp)
                    )

                    Text(
                        "Leave Review"
                    )

                }

            }

            // ================= REJECTED =================

            if (booking.status == "Rejected") {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFFFFEBEE)

                        )

                ) {

                    Text(

                        text =
                            "❌ This booking was rejected",

                        modifier =
                            Modifier.padding(14.dp),

                        color =
                            Color(0xFFC62828)

                    )

                }

            }

        }

    }

}