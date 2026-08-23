package com.example.shohojseba.ui.customer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

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

    onBookClick: () -> Unit,

    onReviewsClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        shape =
            RoundedCornerShape(28.dp),

        elevation =
            CardDefaults.cardElevation(8.dp)

    ) {

        Column(

            modifier =
                Modifier.padding(20.dp)

        ) {

            Text(

                text = "🧹",

                fontSize = 42.sp

            )

            Text(

                text = title,

                style =
                    MaterialTheme.typography.titleLarge

            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {

                // REAL PROVIDER RATING

                if (reviewCount > 0) {

                    Text(

                        text =
                            "⭐ ${
                                String.format(
                                    Locale.US,
                                    "%.1f",
                                    averageRating
                                )
                            } ($reviewCount)",

                        color =
                            Color(0xFF007A7A),

                        modifier =
                            Modifier.clickable {

                                onReviewsClick()

                            }

                    )

                } else {

                    Text(

                        text =
                            "☆ No reviews yet",

                        color =
                            Color.Gray

                    )

                }

                Text(

                    text =
                        "৳$price",

                    style =
                        MaterialTheme.typography.titleMedium

                )

            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = description
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(

                text =
                    "⏱ Duration: $duration"

            )

            HorizontalDivider(

                modifier =
                    Modifier.padding(
                        vertical = 10.dp
                    )

            )

            Text(

                text =
                    "👤 Provider",

                style =
                    MaterialTheme.typography.titleMedium

            )

            Text(
                text = provider
            )

            Text(
                text = phone
            )

            Text(

                text =
                    "Experience: $experience years"

            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Button(

                onClick =
                    onBookClick,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Color(0xFF007A7A)

                    )

            ) {

                Text(
                    "Book Service"
                )

            }

        }

    }

}