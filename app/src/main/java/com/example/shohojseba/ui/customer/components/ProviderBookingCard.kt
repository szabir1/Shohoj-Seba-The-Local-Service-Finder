package com.example.shohojseba.ui.provider.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shohojseba.data.model.Booking

@Composable
fun ProviderBookingCard(

    booking: Booking,

    onAccept: () -> Unit,

    onReject: () -> Unit,

    onComplete: () -> Unit

) {

    val statusColor = when (booking.status) {
        "Accepted" -> Color(0xFF2E7D32)
        "Rejected" -> Color(0xFFC62828)
        "Completed" -> Color(0xFF1565C0)
        else -> Color(0xFFFFA000)
    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(6.dp)

    ) {

        Column(

            modifier = Modifier.padding(18.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Column {

                    Text(

                        booking.service?.serviceName ?: "Service",

                        style = MaterialTheme.typography.titleLarge

                    )

                    Text(

                        "Customer: ${booking.customer?.name ?: "Unknown"}",

                        color = Color.Gray

                    )

                }

                Box(

                    modifier = Modifier

                        .background(

                            statusColor.copy(alpha = .15f),

                            RoundedCornerShape(30.dp)

                        )

                        .padding(horizontal = 12.dp, vertical = 6.dp)

                ) {

                    Text(

                        booking.status,

                        color = statusColor

                    )

                }

            }

            HorizontalDivider()

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Default.CalendarMonth, null)

                Spacer(Modifier.width(8.dp))

                Text(booking.bookingDate)

            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Default.Schedule, null)

                Spacer(Modifier.width(8.dp))

                Text(booking.bookingTime)

            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Default.LocationOn, null)

                Spacer(Modifier.width(8.dp))

                Text(booking.address)

            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Default.Person, null)

                Spacer(Modifier.width(8.dp))

                Text(booking.customer?.phone ?: "")

            }

            Row(verticalAlignment = Alignment.Top) {

                Icon(Icons.Default.Work, null)

                Spacer(Modifier.width(8.dp))

                Text(booking.problemDescription)

            }

            Spacer(Modifier.height(6.dp))

            when (booking.status) {

                "Pending" -> {

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.spacedBy(12.dp)

                    ) {

                        OutlinedButton(

                            modifier = Modifier.weight(1f),

                            onClick = onReject

                        ) {

                            Text("Reject")

                        }

                        Button(

                            modifier = Modifier.weight(1f),

                            onClick = onAccept,

                            colors = ButtonDefaults.buttonColors(

                                containerColor = Color(0xFF007A7A)

                            )

                        ) {

                            Text("Accept")

                        }

                    }

                }

                "Accepted" -> {

                    Button(

                        modifier = Modifier.fillMaxWidth(),

                        onClick = onComplete,

                        colors = ButtonDefaults.buttonColors(

                            containerColor = Color(0xFF1565C0)

                        )

                    ) {

                        Text("Complete Job")

                    }

                }

                "Completed" -> {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(

                            containerColor = Color(0xFFE3F2FD)

                        )

                    ) {

                        Row(

                            modifier = Modifier.padding(16.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text("✅ Job completed successfully")

                        }

                    }

                }

                "Rejected" -> {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(

                            containerColor = Color(0xFFFFEBEE)

                        )

                    ) {

                        Row(

                            modifier = Modifier.padding(16.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text("❌ Booking rejected")

                        }

                    }

                }

            }

        }

    }

}