package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel


@Composable
fun AdminDashboard(

    onCategoriesClick: () -> Unit,

    onAreasClick: () -> Unit = {},

    onCustomersClick: () -> Unit = {},

    onProvidersClick: () -> Unit = {},

    onServicesClick: () -> Unit = {},

    onReviewsClick: () -> Unit = {},

    onLogoutClick: () -> Unit,

    authViewModel: AuthViewModel =
        viewModel()

) {

    val primary =
        Color(0xFF00897B)

    val background =
        Color(0xFFF7FBFA)

    val textSecondary =
        Color(0xFF66706D)

    val isLoggingOut by
    authViewModel.isLoading

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }


    // =====================================================
    // LOGOUT DIALOG
    // =====================================================

    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {

                if (!isLoggingOut) {
                    showLogoutDialog = false
                }

            },

            icon = {

                Surface(

                    modifier =
                        Modifier.size(62.dp),

                    shape =
                        CircleShape,

                    color =
                        Color(0xFFFFEBEE)

                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Logout,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFFC62828),

                            modifier =
                                Modifier.size(30.dp)

                        )

                    }

                }

            },

            title = {

                Text(
                    text = "Log out?",
                    fontWeight = FontWeight.Bold
                )

            },

            text = {

                Text(
                    "Are you sure you want to log out of the admin account?"
                )

            },

            dismissButton = {

                TextButton(

                    enabled =
                        !isLoggingOut,

                    onClick = {
                        showLogoutDialog = false
                    }

                ) {

                    Text("Cancel")

                }

            },

            confirmButton = {

                Button(

                    enabled =
                        !isLoggingOut,

                    onClick = {

                        authViewModel.logout {

                            showLogoutDialog = false

                            onLogoutClick()

                        }

                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFC62828)
                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    if (isLoggingOut) {

                        CircularProgressIndicator(

                            modifier =
                                Modifier.size(20.dp),

                            color =
                                Color.White,

                            strokeWidth =
                                2.dp

                        )

                    } else {

                        Icon(
                            imageVector =
                                Icons.Default.Logout,
                            contentDescription =
                                null
                        )

                        Spacer(
                            Modifier.width(7.dp)
                        )

                        Text("Log Out")

                    }

                }

            },

            shape =
                RoundedCornerShape(24.dp)

        )

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
                            Color(0xFFE7F8F4),
                            background,
                            Color.White
                        )

                    )

                )
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 20.dp
                )

    ) {


        Spacer(
            Modifier.height(24.dp)
        )


        // =====================================================
        // HEADER
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically

        ) {


            Surface(

                modifier =
                    Modifier.size(58.dp),

                shape =
                    RoundedCornerShape(18.dp),

                color =
                    Color(0xFFDDF5F0)

            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Security,

                        contentDescription =
                            null,

                        tint =
                            primary,

                        modifier =
                            Modifier.size(30.dp)

                    )

                }

            }


            Spacer(
                Modifier.width(14.dp)
            )


            Column(

                modifier =
                    Modifier.weight(1f)

            ) {

                Text(

                    text =
                        "Admin Dashboard",

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium,

                    fontWeight =
                        FontWeight.Bold

                )

                Spacer(
                    Modifier.height(3.dp)
                )

                Text(

                    text =
                        "Manage the ShohojSeba platform",

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    color =
                        textSecondary

                )

            }


            Surface(

                modifier =
                    Modifier
                        .size(48.dp)
                        .clickable {

                            showLogoutDialog = true

                        },

                shape =
                    CircleShape,

                color =
                    Color.White,

                shadowElevation =
                    4.dp

            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Logout,

                        contentDescription =
                            "Log out",

                        tint =
                            Color(0xFFC62828)

                    )

                }

            }

        }


        Spacer(
            Modifier.height(24.dp)
        )


        // =====================================================
        // WELCOME CARD
        // =====================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(26.dp),

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
                                    Color(0xFF00695C),
                                    Color(0xFF26A69A)
                                )

                            )

                        )
                        .padding(20.dp)

            ) {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    Column(

                        modifier =
                            Modifier.weight(1f)

                    ) {

                        Text(

                            text =
                                "Platform Control Center",

                            color =
                                Color.White,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge,

                            fontWeight =
                                FontWeight.Bold

                        )

                        Spacer(
                            Modifier.height(6.dp)
                        )

                        Text(

                            text =
                                "Manage users, providers, services and platform content from one place.",

                            color =
                                Color.White.copy(
                                    alpha = 0.88f
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium

                        )

                    }


                    Spacer(
                        Modifier.width(12.dp)
                    )


                    Surface(

                        modifier =
                            Modifier.size(72.dp),

                        shape =
                            RoundedCornerShape(22.dp),

                        color =
                            Color.White.copy(
                                alpha = 0.16f
                            )

                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = "⚙️",
                                fontSize = 38.sp
                            )

                        }

                    }

                }

            }

        }


        Spacer(
            Modifier.height(28.dp)
        )


        Text(

            text =
                "Platform Management",

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold

        )


        Spacer(
            Modifier.height(5.dp)
        )


        Text(

            text =
                "Choose what you want to manage",

            style =
                MaterialTheme
                    .typography
                    .bodyMedium,

            color =
                textSecondary

        )


        Spacer(
            Modifier.height(16.dp)
        )


        // =====================================================
        // ROW 1
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Categories",

                subtitle =
                    "Service types",

                icon =
                    Icons.Default.Category,

                iconBackground =
                    Color(0xFFE3F5F1),

                iconColor =
                    primary,

                onClick =
                    onCategoriesClick

            )


            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Areas",

                subtitle =
                    "Service zones",

                icon =
                    Icons.Default.LocationOn,

                iconBackground =
                    Color(0xFFE3F2FD),

                iconColor =
                    Color(0xFF1565C0),

                onClick =
                    onAreasClick

            )

        }


        Spacer(
            Modifier.height(12.dp)
        )


        // =====================================================
        // ROW 2
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Customers",

                subtitle =
                    "Registered users",

                icon =
                    Icons.Default.People,

                iconBackground =
                    Color(0xFFF3E5F5),

                iconColor =
                    Color(0xFF7B1FA2),

                onClick =
                    onCustomersClick

            )


            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Providers",

                subtitle =
                    "Verify & control",

                icon =
                    Icons.Default.Person,

                iconBackground =
                    Color(0xFFFFF3E0),

                iconColor =
                    Color(0xFFE67E00),

                onClick =
                    onProvidersClick

            )

        }


        Spacer(
            Modifier.height(12.dp)
        )


        // =====================================================
        // ROW 3
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Services",

                subtitle =
                    "Platform listings",

                icon =
                    Icons.Default.HomeRepairService,

                iconBackground =
                    Color(0xFFE8F5E9),

                iconColor =
                    Color(0xFF2E7D32),

                onClick =
                    onServicesClick

            )


            AdminQuickCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Reviews",

                subtitle =
                    "Moderation",

                icon =
                    Icons.Default.RateReview,

                iconBackground =
                    Color(0xFFFFF8E1),

                iconColor =
                    Color(0xFFFF8F00),

                onClick =
                    onReviewsClick

            )

        }


        Spacer(
            Modifier.height(30.dp)
        )


        Text(

            text =
                "Admin Tools",

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold

        )


        Spacer(
            Modifier.height(12.dp)
        )


        AdminWideMenuCard(

            title =
                "Provider Management",

            subtitle =
                "Verify, suspend, reactivate or remove service providers",

            icon =
                Icons.Default.Person,

            iconColor =
                Color(0xFF1565C0),

            iconBackground =
                Color(0xFFE3F2FD),

            onClick =
                onProvidersClick

        )


        Spacer(
            Modifier.height(12.dp)
        )


        AdminWideMenuCard(

            title =
                "Service Moderation",

            subtitle =
                "Review provider services and remove inappropriate listings",

            icon =
                Icons.Default.HomeRepairService,

            iconColor =
                Color(0xFF2E7D32),

            iconBackground =
                Color(0xFFE8F5E9),

            onClick =
                onServicesClick

        )


        Spacer(
            Modifier.height(12.dp)
        )


        AdminWideMenuCard(

            title =
                "Review Moderation",

            subtitle =
                "Review customer feedback and delete inappropriate reviews",

            icon =
                Icons.Default.RateReview,

            iconColor =
                Color(0xFFE67E00),

            iconBackground =
                Color(0xFFFFF3E0),

            onClick =
                onReviewsClick

        )


        Spacer(
            Modifier.height(30.dp)
        )

    }

}


// =====================================================
// QUICK CARD
// =====================================================

@Composable
private fun AdminQuickCard(

    modifier: Modifier = Modifier,

    title: String,

    subtitle: String,

    icon: ImageVector,

    iconBackground: Color,

    iconColor: Color,

    onClick: () -> Unit

) {

    Card(

        modifier =
            modifier
                .height(150.dp)
                .clickable {
                    onClick()
                },

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White
            ),

        elevation =
            CardDefaults.cardElevation(3.dp)

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(15.dp),

            verticalArrangement =
                Arrangement.SpaceBetween

        ) {

            Surface(

                modifier =
                    Modifier.size(46.dp),

                shape =
                    RoundedCornerShape(15.dp),

                color =
                    iconBackground

            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = null,

                        tint = iconColor,

                        modifier =
                            Modifier.size(24.dp)

                    )

                }

            }


            Column {

                Text(

                    text = title,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold

                )

                Spacer(
                    Modifier.height(3.dp)
                )

                Text(

                    text = subtitle,

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,

                    color =
                        Color(0xFF6F7976)

                )

            }

        }

    }

}


// =====================================================
// WIDE CARD
// =====================================================

@Composable
private fun AdminWideMenuCard(

    title: String,

    subtitle: String,

    icon: ImageVector,

    iconColor: Color,

    iconBackground: Color,

    onClick: () -> Unit

) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White
            ),

        elevation =
            CardDefaults.cardElevation(2.dp)

    ) {

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Surface(

                modifier =
                    Modifier.size(50.dp),

                shape =
                    RoundedCornerShape(16.dp),

                color =
                    iconBackground

            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = null,

                        tint = iconColor,

                        modifier =
                            Modifier.size(25.dp)

                    )

                }

            }


            Spacer(
                Modifier.width(13.dp)
            )


            Column(

                modifier =
                    Modifier.weight(1f)

            ) {

                Text(

                    text = title,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold

                )

                Spacer(
                    Modifier.height(3.dp)
                )

                Text(

                    text = subtitle,

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,

                    color =
                        Color(0xFF66706D)

                )

            }


            Icon(

                imageVector =
                    Icons.Default.KeyboardArrowRight,

                contentDescription =
                    null,

                tint =
                    Color(0xFF89928F)

            )

        }

    }

}