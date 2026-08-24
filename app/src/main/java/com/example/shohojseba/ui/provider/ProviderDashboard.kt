package com.example.shohojseba.ui.provider

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel
import com.example.shohojseba.viewmodel.ProviderNotificationViewModel
import com.example.shohojseba.viewmodel.ProviderViewModel


@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun ProviderDashboard(

    viewModel: ProviderViewModel =
        viewModel(),

    notificationViewModel: ProviderNotificationViewModel =
        viewModel(),

    authViewModel: AuthViewModel =
        viewModel(),

    onAddServiceClick: () -> Unit,

    onBookingRequestsClick: () -> Unit,

    onNotificationsClick: () -> Unit,

    onLogoutClick: () -> Unit

) {


    // =====================================================
    // COLORS
    // =====================================================

    val primary =
        Color(0xFF00897B)

    val darkPrimary =
        Color(0xFF00695C)

    val background =
        Color(0xFFF7FBFA)

    val softMint =
        Color(0xFFE3F5F1)

    val textSecondary =
        Color(0xFF66706D)


    // =====================================================
    // PROVIDER DATA
    // =====================================================

    val provider by
    viewModel.provider

    val services by
    viewModel.services

    val areas by
    viewModel.areas

    val selectedAreaIds by
    viewModel.selectedAreaIds

    val message by
    viewModel.message


    // =====================================================
    // PROVIDER NOTIFICATIONS
    // =====================================================

    val notifications by
    notificationViewModel.notifications


    val unreadNotificationCount =

        notifications.count {

            !it.is_read

        }


    // =====================================================
    // LOGOUT
    // =====================================================

    val isLoggingOut by
    authViewModel.isLoading


    // =====================================================
    // STATES
    // =====================================================

    var showAreaSavedDialog by remember {

        mutableStateOf(false)

    }


    var showAvailabilityDialog by remember {

        mutableStateOf(false)

    }


    var showServiceAreas by remember {

        mutableStateOf(false)

    }


    var showLogoutDialog by remember {

        mutableStateOf(false)

    }


    // =====================================================
    // LOAD
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel
            .loadProviderProfile()


        notificationViewModel
            .loadNotifications()

    }


    // =====================================================
    // SUCCESS LISTENER
    // =====================================================

    LaunchedEffect(message) {

        if (
            message ==
            "Service areas updated successfully"
        ) {

            showAreaSavedDialog =
                true

        }


        if (
            message.startsWith(
                "Availability set to"
            )
        ) {

            showAvailabilityDialog =
                true

        }

    }


    // =====================================================
    // AREA SUCCESS DIALOG
    // =====================================================

    if (
        showAreaSavedDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                showAreaSavedDialog =
                    false

                viewModel.clearMessage()

            },

            icon = {

                Surface(

                    modifier =
                        Modifier.size(
                            62.dp
                        ),

                    shape =
                        CircleShape,

                    color =
                        softMint

                ) {

                    Box(

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.LocationOn,

                            contentDescription =
                                null,

                            tint =
                                primary,

                            modifier =
                                Modifier.size(
                                    30.dp
                                )

                        )

                    }

                }

            },

            title = {

                Text(

                    text =
                        "Service Areas Updated",

                    fontWeight =
                        FontWeight.Bold

                )

            },

            text = {

                Text(
                    "Your selected service areas have been saved successfully."
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showAreaSavedDialog =
                            false

                        showServiceAreas =
                            false

                        viewModel.clearMessage()

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                primary

                        ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "Done"
                    )

                }

            },

            shape =
                RoundedCornerShape(
                    24.dp
                )

        )

    }


    // =====================================================
    // AVAILABILITY SUCCESS DIALOG
    // =====================================================

    if (
        showAvailabilityDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                showAvailabilityDialog =
                    false

                viewModel.clearMessage()

            },

            icon = {

                Surface(

                    modifier =
                        Modifier.size(
                            62.dp
                        ),

                    shape =
                        CircleShape,

                    color =
                        Color(
                            0xFFE8F5E9
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
                                    0xFF2E7D32
                                ),

                            modifier =
                                Modifier.size(
                                    32.dp
                                )

                        )

                    }

                }

            },

            title = {

                Text(

                    text =
                        "Availability Updated",

                    fontWeight =
                        FontWeight.Bold

                )

            },

            text = {

                Text(
                    message
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showAvailabilityDialog =
                            false

                        viewModel.clearMessage()

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                primary

                        ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "OK"
                    )

                }

            },

            shape =
                RoundedCornerShape(
                    24.dp
                )

        )

    }


    // =====================================================
    // LOGOUT DIALOG
    // =====================================================

    if (
        showLogoutDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                if (
                    !isLoggingOut
                ) {

                    showLogoutDialog =
                        false

                }

            },

            icon = {

                Surface(

                    modifier =
                        Modifier.size(
                            62.dp
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

                        Icon(

                            imageVector =
                                Icons.Default.Logout,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFFC62828
                                ),

                            modifier =
                                Modifier.size(
                                    30.dp
                                )

                        )

                    }

                }

            },

            title = {

                Text(

                    text =
                        "Log out?",

                    fontWeight =
                        FontWeight.Bold

                )

            },

            text = {

                Text(
                    "Are you sure you want to log out of your provider account?"
                )

            },

            dismissButton = {

                TextButton(

                    enabled =
                        !isLoggingOut,

                    onClick = {

                        showLogoutDialog =
                            false

                    }

                ) {

                    Text(
                        "Cancel"
                    )

                }

            },

            confirmButton = {

                Button(

                    enabled =
                        !isLoggingOut,

                    onClick = {

                        authViewModel.logout {


                            showLogoutDialog =
                                false


                            onLogoutClick()

                        }

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(
                                    0xFFC62828
                                )

                        ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {


                    if (
                        isLoggingOut
                    ) {

                        CircularProgressIndicator(

                            modifier =
                                Modifier.size(
                                    20.dp
                                ),

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
                            Modifier.width(
                                7.dp
                            )
                        )


                        Text(
                            "Log Out"
                        )

                    }

                }

            },

            shape =
                RoundedCornerShape(
                    24.dp
                )

        )

    }


    // =====================================================
    // CURRENT STATUS
    // =====================================================

    val currentStatus =

        provider
            ?.availability_status
            ?.uppercase()
            ?: "AVAILABLE"


    // =====================================================
    // SCREEN
    // =====================================================

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        colors =
                            listOf(

                                Color(
                                    0xFFE8FAF6
                                ),

                                background,

                                Color.White

                            )

                    )

                )
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(

                    horizontal =
                        20.dp

                )

    ) {


        Spacer(
            Modifier.height(
                22.dp
            )
        )


        // =====================================================
        // HEADER
        // =====================================================

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
                    Modifier.weight(
                        1f
                    )

            ) {

                Text(

                    text =
                        "Good Morning 👋",

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium,

                    fontWeight =
                        FontWeight.Bold

                )


                Spacer(
                    Modifier.height(
                        4.dp
                    )
                )


                Text(

                    text =
                        "Manage your business",

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    color =
                        textSecondary

                )

            }


            // =================================================
            // HEADER ACTIONS
            // =================================================

            Row(

                horizontalArrangement =
                    Arrangement.spacedBy(
                        8.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                // =============================================
                // NOTIFICATION
                // =============================================

                Box {


                    Surface(

                        modifier =
                            Modifier
                                .size(
                                    50.dp
                                )
                                .clickable {

                                    onNotificationsClick()

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
                                    Icons.Default.Notifications,

                                contentDescription =
                                    "Notifications",

                                tint =
                                    darkPrimary,

                                modifier =
                                    Modifier.size(
                                        25.dp
                                    )

                            )

                        }

                    }


                    if (
                        unreadNotificationCount > 0
                    ) {

                        Box(

                            modifier =
                                Modifier
                                    .align(
                                        Alignment.TopEnd
                                    )
                                    .offset(
                                        x = 3.dp,
                                        y = (-3).dp
                                    )
                                    .defaultMinSize(

                                        minWidth =
                                            20.dp,

                                        minHeight =
                                            20.dp

                                    )
                                    .background(

                                        color =
                                            Color(
                                                0xFFD32F2F
                                            ),

                                        shape =
                                            CircleShape

                                    )
                                    .padding(

                                        horizontal =
                                            5.dp,

                                        vertical =
                                            2.dp

                                    ),

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Text(

                                text =

                                    if (
                                        unreadNotificationCount > 9
                                    ) {

                                        "9+"

                                    } else {

                                        unreadNotificationCount
                                            .toString()

                                    },

                                color =
                                    Color.White,

                                fontSize =
                                    9.sp,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }


                // =============================================
                // LOGOUT
                // =============================================

                Surface(

                    modifier =
                        Modifier
                            .size(
                                50.dp
                            )
                            .clickable {

                                showLogoutDialog =
                                    true

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
                                Color(
                                    0xFFC62828
                                ),

                            modifier =
                                Modifier.size(
                                    24.dp
                                )

                        )

                    }

                }

            }

        }


        Spacer(
            Modifier.height(
                22.dp
            )
        )


        // =====================================================
        // PROFILE CARD
        // =====================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

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
                    3.dp
                )

        ) {


            if (
                provider != null
            ) {


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                18.dp
                            ),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Surface(

                        modifier =
                            Modifier.size(
                                62.dp
                            ),

                        shape =
                            RoundedCornerShape(
                                20.dp
                            ),

                        color =
                            softMint

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Text(

                                text =
                                    "👨‍🔧",

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


                    Column(

                        modifier =
                            Modifier.weight(
                                1f
                            )

                    ) {


                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically

                        ) {

                            Text(

                                text =
                                    provider!!.name,

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                maxLines =
                                    1,

                                overflow =
                                    TextOverflow.Ellipsis

                            )


                            if (
                                provider!!.is_verified
                            ) {

                                Spacer(
                                    Modifier.width(
                                        6.dp
                                    )
                                )


                                Text(

                                    text =
                                        "✓",

                                    color =
                                        Color(
                                            0xFF1565C0
                                        ),

                                    fontWeight =
                                        FontWeight.Bold

                                )

                            }

                        }


                        Spacer(
                            Modifier.height(
                                3.dp
                            )
                        )


                        Text(

                            text =
                                "⭐ ${provider!!.experience} years experience",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                textSecondary

                        )


                        Text(

                            text =
                                provider!!.email,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary,

                            maxLines =
                                1,

                            overflow =
                                TextOverflow.Ellipsis

                        )

                    }


                    Spacer(
                        Modifier.width(
                            8.dp
                        )
                    )


                    Surface(

                        shape =
                            RoundedCornerShape(
                                50.dp
                            ),

                        color =
                            availabilityBackground(
                                currentStatus
                            )

                    ) {

                        Text(

                            text =
                                when (
                                    currentStatus
                                ) {

                                    "AVAILABLE" ->
                                        "● Online"

                                    "BUSY" ->
                                        "● Busy"

                                    else ->
                                        "● Offline"

                                },

                            modifier =
                                Modifier.padding(

                                    horizontal =
                                        10.dp,

                                    vertical =
                                        6.dp

                                ),

                            color =
                                availabilityColor(
                                    currentStatus
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .labelMedium,

                            fontWeight =
                                FontWeight.Bold

                        )

                    }

                }

            } else {


                Row(

                    modifier =
                        Modifier.padding(
                            22.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(
                                24.dp
                            ),

                        strokeWidth =
                            2.dp,

                        color =
                            primary

                    )


                    Spacer(
                        Modifier.width(
                            14.dp
                        )
                    )


                    Text(
                        "Loading provider profile..."
                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                18.dp
            )
        )


        // =====================================================
        // QUICK STATS
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    10.dp
                )

        ) {


            ProviderStatCard(

                modifier =
                    Modifier.weight(
                        1f
                    ),

                value =
                    services.size
                        .toString(),

                label =
                    "Services",

                emoji =
                    "🛠"

            )


            ProviderStatCard(

                modifier =
                    Modifier.weight(
                        1f
                    ),

                value =
                    selectedAreaIds.size
                        .toString(),

                label =
                    "Areas",

                emoji =
                    "📍"

            )


            ProviderStatCard(

                modifier =
                    Modifier.weight(
                        1f
                    ),

                value =
                    unreadNotificationCount
                        .toString(),

                label =
                    "Alerts",

                emoji =
                    "🔔"

            )

        }


        Spacer(
            Modifier.height(
                26.dp
            )
        )


        // =====================================================
        // AVAILABILITY
        // =====================================================

        Text(

            text =
                "Availability",

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold

        )


        Spacer(
            Modifier.height(
                10.dp
            )
        )


        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    22.dp
                ),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color.White

                ),

            elevation =
                CardDefaults.cardElevation(
                    2.dp
                )

        ) {


            Column(

                modifier =
                    Modifier.padding(
                        16.dp
                    )

            ) {


                Row(

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Schedule,

                        contentDescription =
                            null,

                        tint =
                            primary

                    )


                    Spacer(
                        Modifier.width(
                            8.dp
                        )
                    )


                    Text(

                        text =
                            "Set your current status",

                        fontWeight =
                            FontWeight.SemiBold

                    )

                }


                Spacer(
                    Modifier.height(
                        12.dp
                    )
                )


                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            7.dp
                        )

                ) {


                    CompactStatusChip(

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        text =
                            "Available",

                        emoji =
                            "🟢",

                        selected =
                            currentStatus ==
                                    "AVAILABLE",

                        onClick = {

                            viewModel
                                .updateAvailabilityStatus(
                                    "AVAILABLE"
                                )

                        }

                    )


                    CompactStatusChip(

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        text =
                            "Busy",

                        emoji =
                            "🟡",

                        selected =
                            currentStatus ==
                                    "BUSY",

                        onClick = {

                            viewModel
                                .updateAvailabilityStatus(
                                    "BUSY"
                                )

                        }

                    )


                    CompactStatusChip(

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        text =
                            "Offline",

                        emoji =
                            "🔴",

                        selected =
                            currentStatus ==
                                    "UNAVAILABLE",

                        onClick = {

                            viewModel
                                .updateAvailabilityStatus(
                                    "UNAVAILABLE"
                                )

                        }

                    )

                }


                Spacer(
                    Modifier.height(
                        10.dp
                    )
                )


                Text(

                    text =
                        when (
                            currentStatus
                        ) {

                            "AVAILABLE" ->
                                "Customers can currently send you service requests."

                            "BUSY" ->
                                "Customers will see that you are currently busy."

                            "UNAVAILABLE" ->
                                "Customers will see that you are unavailable."

                            else ->
                                currentStatus

                        },

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,

                    color =
                        availabilityColor(
                            currentStatus
                        )

                )

            }

        }


        Spacer(
            Modifier.height(
                26.dp
            )
        )


        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        Text(

            text =
                "Quick Actions",

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold

        )


        Spacer(
            Modifier.height(
                12.dp
            )
        )


        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    12.dp
                )

        ) {


            Card(

                modifier =
                    Modifier
                        .weight(
                            1f
                        )
                        .height(
                            125.dp
                        )
                        .clickable {

                            onBookingRequestsClick()

                        },

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color(
                                0xFFE3F5F1
                            )

                    )

            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                16.dp
                            ),

                    verticalArrangement =
                        Arrangement.SpaceBetween

                ) {


                    Surface(

                        modifier =
                            Modifier.size(
                                42.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            Color.White

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.CalendarMonth,

                                contentDescription =
                                    null,

                                tint =
                                    primary

                            )

                        }

                    }


                    Column {

                        Text(

                            text =
                                "Booking",

                            fontWeight =
                                FontWeight.Bold

                        )


                        Text(

                            text =
                                "Requests",

                            color =
                                textSecondary,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium

                        )

                    }

                }

            }


            Card(

                modifier =
                    Modifier
                        .weight(
                            1f
                        )
                        .height(
                            125.dp
                        )
                        .clickable {

                            onAddServiceClick()

                        },

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            primary

                    )

            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                16.dp
                            ),

                    verticalArrangement =
                        Arrangement.SpaceBetween

                ) {


                    Surface(

                        modifier =
                            Modifier.size(
                                42.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            Color.White.copy(
                                alpha = 0.18f
                            )

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Add,

                                contentDescription =
                                    null,

                                tint =
                                    Color.White

                            )

                        }

                    }


                    Column {

                        Text(

                            text =
                                "Add New",

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White

                        )


                        Text(

                            text =
                                "Service",

                            color =
                                Color.White.copy(
                                    alpha = 0.85f
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium

                        )

                    }

                }

            }

        }


        Spacer(
            Modifier.height(
                26.dp
            )
        )


        // =====================================================
        // SERVICE AREAS
        // =====================================================

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
                    "Service Areas",

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.Bold

            )


            Text(

                text =
                    "${selectedAreaIds.size} selected",

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color =
                    primary

            )

        }


        Spacer(
            Modifier.height(
                10.dp
            )
        )


        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    22.dp
                ),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color.White

                ),

            elevation =
                CardDefaults.cardElevation(
                    2.dp
                )

        ) {

            Column(

                modifier =
                    Modifier.padding(
                        16.dp
                    )

            ) {


                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Surface(

                        modifier =
                            Modifier.size(
                                44.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            softMint

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.LocationOn,

                                contentDescription =
                                    null,

                                tint =
                                    primary

                            )

                        }

                    }


                    Spacer(
                        Modifier.width(
                            12.dp
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
                                "Where you provide services",

                            fontWeight =
                                FontWeight.SemiBold

                        )


                        Text(

                            text =

                                if (
                                    selectedAreaIds.isEmpty()
                                ) {

                                    "No service areas selected"

                                } else {

                                    val selectedNames =

                                        areas
                                            .filter { area ->

                                                selectedAreaIds
                                                    .contains(
                                                        area.area_id
                                                    )

                                            }
                                            .map {

                                                it.area_name

                                            }


                                    when {


                                        selectedNames.isEmpty() ->

                                            "${selectedAreaIds.size} area(s) selected"


                                        selectedNames.size <= 3 ->

                                            selectedNames
                                                .joinToString(
                                                    " • "
                                                )


                                        else ->

                                            selectedNames
                                                .take(
                                                    3
                                                )
                                                .joinToString(
                                                    " • "
                                                ) +
                                                    " +${selectedNames.size - 3} more"

                                    }

                                },

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary,

                            maxLines =
                                2,

                            overflow =
                                TextOverflow.Ellipsis

                        )

                    }

                }


                Spacer(
                    Modifier.height(
                        12.dp
                    )
                )


                OutlinedButton(

                    onClick = {

                        showServiceAreas =
                            !showServiceAreas

                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    colors =
                        ButtonDefaults.outlinedButtonColors(

                            contentColor =
                                primary

                        )

                ) {


                    Text(

                        text =

                            if (
                                showServiceAreas
                            ) {

                                "Hide Service Areas"

                            } else {

                                "Manage Service Areas"

                            },

                        modifier =
                            Modifier.weight(
                                1f
                            )

                    )


                    Icon(

                        imageVector =

                            if (
                                showServiceAreas
                            ) {

                                Icons.Default.ExpandLess

                            } else {

                                Icons.Default.ExpandMore

                            },

                        contentDescription =
                            null

                    )

                }


                AnimatedVisibility(

                    visible =
                        showServiceAreas

                ) {


                    Column {


                        Spacer(
                            Modifier.height(
                                14.dp
                            )
                        )


                        HorizontalDivider(

                            color =
                                Color(
                                    0xFFE2E9E7
                                )

                        )


                        Spacer(
                            Modifier.height(
                                14.dp
                            )
                        )


                        Text(

                            text =
                                "Select all areas where you provide services.",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                textSecondary

                        )


                        Spacer(
                            Modifier.height(
                                12.dp
                            )
                        )


                        if (
                            areas.isEmpty()
                        ) {

                            Text(

                                text =
                                    "No areas available",

                                color =
                                    textSecondary

                            )

                        } else {


                            FlowRow(

                                horizontalArrangement =
                                    Arrangement.spacedBy(
                                        8.dp
                                    ),

                                verticalArrangement =
                                    Arrangement.spacedBy(
                                        8.dp
                                    )

                            ) {


                                areas.forEach { area ->


                                    val selected =

                                        selectedAreaIds
                                            .contains(
                                                area.area_id
                                            )


                                    FilterChip(

                                        selected =
                                            selected,

                                        onClick = {

                                            viewModel
                                                .toggleArea(
                                                    area.area_id
                                                )

                                        },

                                        label = {

                                            Text(
                                                area.area_name
                                            )

                                        },

                                        leadingIcon =

                                            if (
                                                selected
                                            ) {

                                                {

                                                    Text(
                                                        "✓"
                                                    )

                                                }

                                            } else {

                                                null

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


                        Button(

                            onClick = {

                                viewModel
                                    .saveProviderAreas()

                            },

                            enabled =
                                areas.isNotEmpty(),

                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                ButtonDefaults.buttonColors(

                                    containerColor =
                                        primary

                                ),

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                )

                        ) {


                            Icon(

                                imageVector =
                                    Icons.Default.LocationOn,

                                contentDescription =
                                    null

                            )


                            Spacer(
                                Modifier.width(
                                    7.dp
                                )
                            )


                            Text(
                                "Save Service Areas"
                            )

                        }

                    }

                }

            }

        }


        Spacer(
            Modifier.height(
                20.dp
            )
        )


        // =====================================================
        // NOTIFICATION CARD
        // =====================================================

        Card(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {

                        onNotificationsClick()

                    },

            shape =
                RoundedCornerShape(
                    22.dp
                ),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(
                            0xFFE5F1FC
                        )

                )

        ) {


            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            16.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Box {


                    Surface(

                        modifier =
                            Modifier.size(
                                48.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            Color.White.copy(
                                alpha = 0.75f
                            )

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Notifications,

                                contentDescription =
                                    null,

                                tint =
                                    Color(
                                        0xFF1565C0
                                    )

                            )

                        }

                    }


                    if (
                        unreadNotificationCount > 0
                    ) {

                        Box(

                            modifier =
                                Modifier
                                    .align(
                                        Alignment.TopEnd
                                    )
                                    .offset(
                                        x = 5.dp,
                                        y = (-4).dp
                                    )
                                    .size(
                                        20.dp
                                    )
                                    .background(

                                        Color(
                                            0xFFD32F2F
                                        ),

                                        CircleShape

                                    ),

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Text(

                                text =

                                    if (
                                        unreadNotificationCount > 9
                                    ) {

                                        "9+"

                                    } else {

                                        unreadNotificationCount
                                            .toString()

                                    },

                                color =
                                    Color.White,

                                fontSize =
                                    9.sp,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }


                Spacer(
                    Modifier.width(
                        14.dp
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
                            "Notifications",

                        fontWeight =
                            FontWeight.Bold,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    Text(

                        text =

                            if (
                                unreadNotificationCount > 0
                            ) {

                                "$unreadNotificationCount new update${
                                    if (
                                        unreadNotificationCount == 1
                                    ) {
                                        ""
                                    } else {
                                        "s"
                                    }
                                } waiting for you"

                            } else {

                                "You're all caught up"

                            },

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            if (
                                unreadNotificationCount > 0
                            ) {

                                Color(
                                    0xFF1565C0
                                )

                            } else {

                                textSecondary

                            }

                    )

                }


                Icon(

                    imageVector =
                        Icons.Default.KeyboardArrowRight,

                    contentDescription =
                        null,

                    tint =
                        textSecondary

                )

            }

        }


        Spacer(
            Modifier.height(
                28.dp
            )
        )


        // =====================================================
        // MY SERVICES
        // =====================================================

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
                    "My Services",

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.Bold

            )


            Text(

                text =
                    "${services.size} service${
                        if (
                            services.size == 1
                        ) {
                            ""
                        } else {
                            "s"
                        }
                    }",

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color =
                    primary

            )

        }


        Spacer(
            Modifier.height(
                12.dp
            )
        )


        if (
            services.isEmpty()
        ) {

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

                    )

            ) {


                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                26.dp
                            ),

                    horizontalAlignment =
                        Alignment.CenterHorizontally

                ) {


                    Text(

                        text =
                            "🛠️",

                        fontSize =
                            42.sp

                    )


                    Spacer(
                        Modifier.height(
                            8.dp
                        )
                    )


                    Text(

                        text =
                            "No services yet",

                        fontWeight =
                            FontWeight.Bold,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    Text(

                        text =
                            "Add your first service to start receiving bookings.",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            textSecondary

                    )


                    Spacer(
                        Modifier.height(
                            14.dp
                        )
                    )


                    Button(

                        onClick =
                            onAddServiceClick,

                        colors =
                            ButtonDefaults.buttonColors(

                                containerColor =
                                    primary

                            ),

                        shape =
                            RoundedCornerShape(
                                14.dp
                            )

                    ) {


                        Icon(

                            imageVector =
                                Icons.Default.Add,

                            contentDescription =
                                null

                        )


                        Spacer(
                            Modifier.width(
                                6.dp
                            )
                        )


                        Text(
                            "Add Service"
                        )

                    }

                }

            }

        } else {


            services.forEach { service ->


                Card(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom =
                                    12.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            22.dp
                        ),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color.White

                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            2.dp
                        )

                ) {


                    Row(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    16.dp
                                ),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(
                                    58.dp
                                ),

                            shape =
                                RoundedCornerShape(
                                    18.dp
                                ),

                            color =
                                softMint

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Text(

                                    text =
                                        serviceEmoji(
                                            service.service_name
                                        ),

                                    fontSize =
                                        30.sp

                                )

                            }

                        }


                        Spacer(
                            Modifier.width(
                                14.dp
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
                                    service.service_name,

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                maxLines =
                                    1,

                                overflow =
                                    TextOverflow.Ellipsis

                            )


                            Spacer(
                                Modifier.height(
                                    6.dp
                                )
                            )


                            Text(

                                text =
                                    "৳${formatProviderPrice(service.price)}",

                                color =
                                    primary,

                                fontWeight =
                                    FontWeight.Bold,

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium

                            )


                            Spacer(
                                Modifier.height(
                                    2.dp
                                )
                            )


                            Text(

                                text =
                                    "⏱ ${service.duration}",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    textSecondary

                            )

                        }


                        Surface(

                            shape =
                                RoundedCornerShape(
                                    50.dp
                                ),

                            color =
                                Color(
                                    0xFFE8F5E9
                                )

                        ) {

                            Text(

                                text =
                                    "Active",

                                modifier =
                                    Modifier.padding(
                                        horizontal = 9.dp,
                                        vertical = 5.dp
                                    ),

                                color =
                                    Color(
                                        0xFF2E7D32
                                    ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelSmall,

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }

            }

        }


        // =====================================================
        // OTHER MESSAGE
        // =====================================================

        if (
            message.isNotEmpty() &&
            message !=
            "Service areas updated successfully" &&
            !message.startsWith(
                "Availability set to"
            )
        ) {

            Spacer(
                Modifier.height(
                    10.dp
                )
            )


            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            softMint

                    ),

                shape =
                    RoundedCornerShape(
                        16.dp
                    )

            ) {

                Text(

                    text =
                        message,

                    modifier =
                        Modifier.padding(
                            14.dp
                        ),

                    color =
                        darkPrimary

                )

            }

        }


        Spacer(
            Modifier.height(
                32.dp
            )
        )

    }

}


// =====================================================
// PROVIDER STAT CARD
// =====================================================

@Composable
private fun ProviderStatCard(

    modifier: Modifier = Modifier,

    value: String,

    label: String,

    emoji: String

) {


    Card(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                20.dp
            ),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    Color.White

            ),

        elevation =
            CardDefaults.cardElevation(
                2.dp
            )

    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal =
                            10.dp,
                        vertical =
                            14.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {


            Text(

                text =
                    emoji,

                fontSize =
                    22.sp

            )


            Spacer(
                Modifier.height(
                    5.dp
                )
            )


            Text(

                text =
                    value,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.Bold

            )


            Text(

                text =
                    label,

                style =
                    MaterialTheme
                        .typography
                        .labelMedium,

                color =
                    Color(
                        0xFF66706D
                    )

            )

        }

    }

}


// =====================================================
// COMPACT STATUS CHIP
// =====================================================

@Composable
private fun CompactStatusChip(

    modifier: Modifier = Modifier,

    text: String,

    emoji: String,

    selected: Boolean,

    onClick: () -> Unit

) {


    Surface(

        modifier =
            modifier
                .clickable {

                    onClick()

                },

        shape =
            RoundedCornerShape(
                14.dp
            ),

        color =

            if (
                selected
            ) {

                Color(
                    0xFFE0F2EF
                )

            } else {

                Color(
                    0xFFF4F6F5
                )

            },

        border =

            if (
                selected
            ) {

                androidx.compose.foundation.BorderStroke(

                    width =
                        1.5.dp,

                    color =
                        Color(
                            0xFF00897B
                        )

                )

            } else {

                null

            }

    ) {


        Column(

            modifier =
                Modifier.padding(
                    horizontal =
                        6.dp,
                    vertical =
                        10.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {


            Text(

                text =
                    emoji,

                fontSize =
                    16.sp

            )


            Spacer(
                Modifier.height(
                    3.dp
                )
            )


            Text(

                text =
                    text,

                fontSize =
                    11.sp,

                fontWeight =

                    if (
                        selected
                    ) {

                        FontWeight.Bold

                    } else {

                        FontWeight.Medium

                    },

                color =

                    if (
                        selected
                    ) {

                        Color(
                            0xFF00695C
                        )

                    } else {

                        Color(
                            0xFF4D5754
                        )

                    }

            )

        }

    }

}


// =====================================================
// AVAILABILITY COLORS
// =====================================================

private fun availabilityColor(

    status: String

): Color {


    return when (
        status
    ) {

        "AVAILABLE" ->

            Color(
                0xFF2E7D32
            )


        "BUSY" ->

            Color(
                0xFFE68A00
            )


        "UNAVAILABLE" ->

            Color(
                0xFFC62828
            )


        else ->

            Color.Gray

    }

}


private fun availabilityBackground(

    status: String

): Color {


    return when (
        status
    ) {

        "AVAILABLE" ->

            Color(
                0xFFE8F5E9
            )


        "BUSY" ->

            Color(
                0xFFFFF3E0
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

}


// =====================================================
// SERVICE EMOJI
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

            "🔧"

        }


        else -> {

            "🛠️"

        }

    }

}


// =====================================================
// PRICE FORMATTER
// =====================================================

private fun formatProviderPrice(

    price: Double

): String {


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