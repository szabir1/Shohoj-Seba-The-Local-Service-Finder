package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AdminDashboard(

    onCategoriesClick: () -> Unit,

    onAreasClick: () -> Unit = {},

    onCustomersClick: () -> Unit = {},

    onProvidersClick: () -> Unit = {},

    onServicesClick: () -> Unit = {},

    onReviewsClick: () -> Unit = {}

) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFE8FFFA),
                        Color.White
                    )
                )
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp)

    ) {

        // =====================================================
        // HEADER
        // =====================================================

        Text(
            text = "Admin Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            Modifier.height(6.dp)
        )

        Text(
            text = "Manage the ShohojSeba platform",
            color = Color.Gray
        )

        Spacer(
            Modifier.height(28.dp)
        )


        // =====================================================
        // PLATFORM MANAGEMENT
        // =====================================================

        Text(
            text = "Platform Management",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            Modifier.height(16.dp)
        )


        // =====================================================
        // MANAGE CATEGORIES
        // =====================================================

        AdminMenuCard(

            title = "Manage Categories",

            subtitle =
                "Add, edit or remove service categories",

            icon = {

                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onCategoriesClick

        )

        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // MANAGE AREAS
        // =====================================================

        AdminMenuCard(

            title = "Manage Areas",

            subtitle =
                "Add, edit or remove service areas",

            icon = {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onAreasClick

        )

        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // CUSTOMERS
        // =====================================================

        AdminMenuCard(

            title = "Customers",

            subtitle =
                "View registered customers",

            icon = {

                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null,
                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onCustomersClick

        )

        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // PROVIDERS
        // =====================================================

        AdminMenuCard(

            title = "Providers",

            subtitle =
                "View registered service providers",

            icon = {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onProvidersClick

        )

        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // SERVICES
        // =====================================================

        AdminMenuCard(

            title = "Services",

            subtitle =
                "Review and manage provider services",

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.HomeRepairService,

                    contentDescription = null,

                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onServicesClick

        )

        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // REVIEWS
        // =====================================================

        AdminMenuCard(

            title = "Reviews",

            subtitle =
                "View and moderate customer reviews",

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.RateReview,

                    contentDescription = null,

                    tint = Color(0xFF007A7A)
                )

            },

            onClick = onReviewsClick

        )

        Spacer(
            Modifier.height(30.dp)
        )

    }

}


@Composable
private fun AdminMenuCard(

    title: String,

    subtitle: String,

    icon: @Composable () -> Unit,

    onClick: () -> Unit

) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        onClick =
            onClick,

        shape =
            RoundedCornerShape(24.dp),

        elevation =
            CardDefaults.cardElevation(
                5.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White
            )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            horizontalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {

            Card(

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color(0xFFDDF8F3)
                    )

            ) {

                Box(

                    modifier =
                        Modifier.padding(13.dp)

                ) {

                    icon()

                }

            }


            Column(

                modifier =
                    Modifier.weight(1f)

            ) {

                Text(

                    text = title,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium

                )

                Spacer(
                    Modifier.height(4.dp)
                )

                Text(

                    text = subtitle,

                    color = Color.Gray,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium

                )

            }

        }

    }

}