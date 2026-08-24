package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search

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

import androidx.navigation.NavController

import com.example.shohojseba.data.model.Area
import com.example.shohojseba.navigation.Screen

import com.example.shohojseba.viewmodel.AreaViewModel
import com.example.shohojseba.viewmodel.AuthViewModel
import com.example.shohojseba.viewmodel.CategoryViewModel
import com.example.shohojseba.viewmodel.NotificationViewModel


// =====================================================
// CUSTOMER HOME
// =====================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    navController: NavController,

    viewModel: CategoryViewModel =
        viewModel(),

    areaViewModel: AreaViewModel =
        viewModel(),

    notificationViewModel: NotificationViewModel =
        viewModel(),

    authViewModel: AuthViewModel =
        viewModel()

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
        Color(0xFFE2F5F1)

    val textSecondary =
        Color(0xFF66706D)


    // =====================================================
    // DATA
    // =====================================================

    val categories by
    viewModel.categories

    val areas by
    areaViewModel.areas

    val notifications by
    notificationViewModel.notifications

    val isLoggingOut by
    authViewModel.isLoading


    // =====================================================
    // UNREAD NOTIFICATIONS
    // =====================================================

    val unreadNotificationCount =
        notifications.count {
            !it.is_read
        }


    // =====================================================
    // STATES
    // =====================================================

    var selectedArea by remember {

        mutableStateOf<Area?>(
            null
        )

    }


    var areaExpanded by remember {

        mutableStateOf(
            false
        )

    }


    var showSelectAreaDialog by remember {

        mutableStateOf(
            false
        )

    }


    var searchText by remember {

        mutableStateOf(
            ""
        )

    }


    var showLogoutDialog by remember {

        mutableStateOf(
            false
        )

    }


    // =====================================================
    // FILTERED CATEGORIES
    // =====================================================

    val filteredCategories =

        if (
            searchText.isBlank()
        ) {

            categories

        } else {

            categories.filter { category ->

                category.category_name
                    .contains(
                        searchText,
                        ignoreCase = true
                    )

            }

        }


    // =====================================================
    // LOAD
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel
            .loadCategories()


        areaViewModel
            .loadAreas()


        notificationViewModel
            .loadNotifications()

    }


    // =====================================================
    // AREA REQUIRED DIALOG
    // =====================================================

    if (
        showSelectAreaDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                showSelectAreaDialog =
                    false

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.LocationOn,

                    contentDescription =
                        null,

                    tint =
                        primary,

                    modifier =
                        Modifier.size(
                            48.dp
                        )

                )

            },

            title = {

                Text(
                    "Choose your service area"
                )

            },

            text = {

                Text(
                    "Select your area first so ShohojSeba can show providers who actually serve your location."
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showSelectAreaDialog =
                            false

                        areaExpanded =
                            true

                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = primary
                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    Text(
                        "Select Area"
                    )

                }

            },

            shape =
                RoundedCornerShape(24.dp)

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
                            60.dp
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
                                    28.dp
                                )

                        )

                    }

                }

            },

            title = {

                Text(
                    "Log out?"
                )

            },

            text = {

                Text(
                    "Are you sure you want to log out of ShohojSeba?"
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


                            navController.navigate(
                                Screen.Login.route
                            ) {


                                popUpTo(
                                    0
                                ) {

                                    inclusive =
                                        true

                                }


                                launchSingleTop =
                                    true

                            }

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
    // SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            background,

        bottomBar = {

            NavigationBar(

                containerColor =
                    Color.White,

                tonalElevation =
                    8.dp

            ) {


                NavigationBarItem(

                    selected =
                        true,

                    onClick = {},

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.Home,

                            contentDescription =
                                "Home"

                        )

                    },

                    label = {

                        Text(
                            "Home"
                        )

                    }

                )


                NavigationBarItem(

                    selected =
                        false,

                    onClick = {

                        navController.navigate(
                            Screen.CustomerBookings.route
                        )

                    },

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.Book,

                            contentDescription =
                                "Bookings"

                        )

                    },

                    label = {

                        Text(
                            "Bookings"
                        )

                    }

                )


                NavigationBarItem(

                    selected =
                        false,

                    onClick = {

                        navController.navigate(
                            Screen.Favorites.route
                        )

                    },

                    icon = {

                        Icon(

                            imageVector =
                                Icons.Default.Favorite,

                            contentDescription =
                                "Saved"

                        )

                    },

                    label = {

                        Text(
                            "Saved"
                        )

                    }

                )


                NavigationBarItem(

                    selected =
                        false,

                    onClick = {

                        navController.navigate(
                            Screen.Notifications.route
                        )

                    },

                    icon = {

                        Box {

                            Icon(

                                imageVector =
                                    Icons.Default.Notifications,

                                contentDescription =
                                    "Notifications"

                            )


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
                                                x = 7.dp,
                                                y = (-5).dp
                                            )
                                            .size(
                                                17.dp
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

                    },

                    label = {

                        Text(
                            "Alerts"
                        )

                    }

                )

            }

        }

    ) { paddingValues ->


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
                        paddingValues
                    )
                    .padding(
                        horizontal = 20.dp
                    )

        ) {


            Spacer(
                Modifier.height(
                    22.dp
                )
            )


            // =================================================
            // HEADER
            // =================================================

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
                            "Good Morning 👋",

                        style =
                            MaterialTheme
                                .typography
                                .headlineMedium,

                        fontWeight =
                            FontWeight.Bold

                    )


                    Spacer(
                        Modifier.height(4.dp)
                    )


                    Text(

                        text =
                            "What service do you need today?",

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
                                    .size(48.dp)
                                    .clickable {

                                        navController.navigate(
                                            Screen.Notifications.route
                                        )

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
                                        darkPrimary

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
                                        10.sp,

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
                                    48.dp
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
                                    )

                            )

                        }

                    }

                }

            }


            Spacer(
                Modifier.height(
                    24.dp
                )
            )


            // =================================================
            // SEARCH
            // =================================================

            OutlinedTextField(

                value =
                    searchText,

                onValueChange = {

                    searchText =
                        it

                },

                leadingIcon = {

                    Icon(

                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            null,

                        tint =
                            primary

                    )

                },

                placeholder = {

                    Text(
                        "Search cleaning, AC, plumbing..."
                    )

                },

                singleLine =
                    true,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        20.dp
                    ),

                colors =
                    OutlinedTextFieldDefaults.colors(

                        focusedContainerColor =
                            Color.White,

                        unfocusedContainerColor =
                            Color.White,

                        focusedBorderColor =
                            primary,

                        unfocusedBorderColor =
                            Color(
                                0xFFD6E2DF
                            )

                    )

            )


            Spacer(
                Modifier.height(
                    18.dp
                )
            )


            // =================================================
            // LOCATION
            // =================================================

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

                        Surface(

                            modifier =
                                Modifier.size(
                                    40.dp
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


                        Column {

                            Text(

                                text =
                                    "Your service area",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                color =
                                    textSecondary

                            )


                            Text(

                                text =
                                    selectedArea
                                        ?.area_name
                                        ?: "Select where you need service",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.SemiBold

                            )

                        }

                    }


                    Spacer(
                        Modifier.height(
                            12.dp
                        )
                    )


                    ExposedDropdownMenuBox(

                        expanded =
                            areaExpanded,

                        onExpandedChange = {

                            areaExpanded =
                                !areaExpanded

                        }

                    ) {


                        OutlinedTextField(

                            value =
                                selectedArea
                                    ?.area_name
                                    ?: "",

                            onValueChange = {},

                            readOnly =
                                true,

                            placeholder = {

                                Text(
                                    "Choose area"
                                )

                            },

                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(

                                        expanded =
                                            areaExpanded

                                    )

                            },

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                )

                        )


                        ExposedDropdownMenu(

                            expanded =
                                areaExpanded,

                            onDismissRequest = {

                                areaExpanded =
                                    false

                            }

                        ) {


                            areas.forEach { area ->


                                DropdownMenuItem(

                                    text = {

                                        Text(
                                            area.area_name
                                        )

                                    },

                                    leadingIcon = {

                                        Icon(

                                            imageVector =
                                                Icons.Default.LocationOn,

                                            contentDescription =
                                                null

                                        )

                                    },

                                    onClick = {

                                        selectedArea =
                                            area


                                        areaExpanded =
                                            false

                                    }

                                )

                            }

                        }

                    }

                }

            }


            Spacer(
                Modifier.height(
                    28.dp
                )
            )


            // =================================================
            // SERVICES HEADER
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
                        "Services",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold

                )


                Text(

                    text =
                        "${filteredCategories.size} available",

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
                    14.dp
                )
            )


            // =================================================
            // CATEGORY SEARCH EMPTY
            // =================================================

            if (
                filteredCategories.isEmpty()
            ) {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        ),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color.White

                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                24.dp
                            ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(

                            text =
                                "🔎",

                            fontSize =
                                36.sp

                        )


                        Spacer(
                            Modifier.height(
                                8.dp
                            )
                        )


                        Text(

                            text =
                                "No service found",

                            fontWeight =
                                FontWeight.Bold

                        )


                        Text(

                            text =
                                "Try another search term.",

                            color =
                                textSecondary

                        )

                    }

                }

            } else {


                filteredCategories
                    .chunked(
                        2
                    )
                    .forEach { rowCategories ->


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    12.dp
                                )

                        ) {


                            rowCategories
                                .forEach { category ->


                                    ServiceCategoryCard(

                                        modifier =
                                            Modifier.weight(
                                                1f
                                            ),

                                        title =
                                            category.category_name,

                                        emoji =
                                            categoryEmoji(
                                                category.category_name
                                            ),

                                        primary =
                                            primary,

                                        onClick = {


                                            val area =
                                                selectedArea


                                            if (
                                                area == null
                                            ) {

                                                showSelectAreaDialog =
                                                    true

                                            } else {


                                                navController.navigate(

                                                    "services/" +
                                                            "${category.category_id}" +
                                                            "?areaId=${area.area_id}" +
                                                            "&areaName=${
                                                                android.net.Uri.encode(
                                                                    area.area_name
                                                                )
                                                            }" +
                                                            "&promo=false"

                                                )

                                            }

                                        }

                                    )

                                }


                            if (
                                rowCategories.size == 1
                            ) {

                                Spacer(

                                    modifier =
                                        Modifier.weight(
                                            1f
                                        )

                                )

                            }

                        }


                        Spacer(
                            Modifier.height(
                                12.dp
                            )
                        )

                    }

            }


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            // =================================================
            // SERVICE REMINDER
            // =================================================

            Text(

                text =
                    "Keep your home cared for",

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


            Card(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {

                            navController.navigate(
                                Screen.ServiceReminders.route
                            )

                        },

                shape =
                    RoundedCornerShape(
                        22.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color(
                                0xFFFFF4DE
                            )

                    )

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
                                46.dp
                            ),

                        shape =
                            CircleShape,

                        color =
                            Color(
                                0xFFFFE7B5
                            )

                    ) {

                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.NotificationsActive,

                                contentDescription =
                                    null,

                                tint =
                                    Color(
                                        0xFFFF8F00
                                    )

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
                                "Service Reminders",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold

                        )


                        Text(

                            text =
                                "Check your upcoming servicing dates",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                textSecondary

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
                    26.dp
                )
            )


            // =================================================
            // PROMOTION TITLE
            // =================================================

            Text(

                text =
                    "Special for you",

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


            // =================================================
            // PROMOTIONAL CARD
            // =================================================

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        28.dp
                    ),

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

                                        Color(
                                            0xFF00695C
                                        ),

                                        Color(
                                            0xFF26A69A
                                        )

                                    )

                                )

                            )
                            .padding(
                                22.dp
                            )

                ) {


                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Column(

                            modifier =
                                Modifier.weight(
                                    1f
                                )

                        ) {

                            Surface(

                                color =
                                    Color.White.copy(
                                        alpha = 0.18f
                                    ),

                                shape =
                                    RoundedCornerShape(
                                        50.dp
                                    )

                            ) {

                                Text(

                                    text =
                                        "20% OFF",

                                    modifier =
                                        Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 5.dp
                                        ),

                                    color =
                                        Color.White,

                                    fontWeight =
                                        FontWeight.Bold

                                )

                            }


                            Spacer(
                                Modifier.height(
                                    12.dp
                                )
                            )


                            Text(

                                text =
                                    "Home Cleaning",

                                fontSize =
                                    24.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.White

                            )


                            Text(

                                text =
                                    "Give your home a fresh start.",

                                color =
                                    Color.White.copy(
                                        alpha = 0.85f
                                    )

                            )


                            Spacer(
                                Modifier.height(
                                    16.dp
                                )
                            )


                            Button(

                                onClick = {


                                    val area =
                                        selectedArea


                                    if (
                                        area == null
                                    ) {

                                        showSelectAreaDialog =
                                            true

                                    } else {

                                        categories
                                            .firstOrNull {

                                                it.category_name
                                                    .equals(
                                                        "Cleaning",
                                                        ignoreCase = true
                                                    )

                                            }
                                            ?.let { category ->


                                                navController.navigate(

                                                    "services/" +
                                                            "${category.category_id}" +
                                                            "?areaId=${area.area_id}" +
                                                            "&areaName=${
                                                                android.net.Uri.encode(
                                                                    area.area_name
                                                                )
                                                            }" +
                                                            "&promo=true"

                                                )

                                            }

                                    }

                                },

                                colors =
                                    ButtonDefaults.buttonColors(

                                        containerColor =
                                            Color.White,

                                        contentColor =
                                            darkPrimary

                                    ),

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    )

                            ) {

                                Text(

                                    text =
                                        "Explore Cleaning",

                                    fontWeight =
                                        FontWeight.Bold

                                )

                            }

                        }


                        Spacer(
                            Modifier.width(
                                12.dp
                            )
                        )


                        Surface(

                            modifier =
                                Modifier.size(
                                    94.dp
                                ),

                            shape =
                                RoundedCornerShape(
                                    28.dp
                                ),

                            color =
                                Color.White.copy(
                                    alpha = 0.15f
                                )

                        ) {

                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {

                                Text(

                                    text =
                                        "🧹",

                                    fontSize =
                                        54.sp

                                )

                            }

                        }

                    }

                }

            }


            Spacer(
                Modifier.height(
                    30.dp
                )
            )

        }

    }

}


// =====================================================
// CATEGORY CARD
// =====================================================

@Composable
private fun ServiceCategoryCard(

    modifier: Modifier = Modifier,

    title: String,

    emoji: String,

    primary: Color,

    onClick: () -> Unit

) {


    Card(

        modifier =
            modifier
                .height(
                    150.dp
                )
                .clickable {

                    onClick()

                },

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
                3.dp
            )

    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        16.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center

        ) {


            Surface(

                modifier =
                    Modifier.size(
                        68.dp
                    ),

                shape =
                    RoundedCornerShape(
                        22.dp
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
                            emoji,

                        fontSize =
                            38.sp

                    )

                }

            }


            Spacer(
                Modifier.height(
                    10.dp
                )
            )


            Text(

                text =
                    title,

                style =
                    MaterialTheme
                        .typography
                        .titleSmall,

                fontWeight =
                    FontWeight.SemiBold,

                maxLines =
                    1,

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
                    "View services",

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color =
                    primary

            )

        }

    }

}


// =====================================================
// CATEGORY VISUAL
// =====================================================

private fun categoryEmoji(

    categoryName: String

): String {


    return when {


        categoryName.contains(
            "clean",
            ignoreCase = true
        ) -> {

            "🧹"

        }


        categoryName.contains(
            "ac",
            ignoreCase = true
        ) -> {

            "❄️"

        }


        categoryName.contains(
            "plumb",
            ignoreCase = true
        ) -> {

            "🚰"

        }


        categoryName.contains(
            "electric",
            ignoreCase = true
        ) -> {

            "⚡"

        }


        categoryName.contains(
            "paint",
            ignoreCase = true
        ) -> {

            "🎨"

        }


        categoryName.contains(
            "repair",
            ignoreCase = true
        ) -> {

            "🛠️"

        }


        categoryName.contains(
            "carpenter",
            ignoreCase = true
        ) -> {

            "🪚"

        }


        categoryName.contains(
            "beauty",
            ignoreCase = true
        ) -> {

            "✨"

        }


        categoryName.contains(
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