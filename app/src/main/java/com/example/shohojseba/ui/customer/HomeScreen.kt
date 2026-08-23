package com.example.shohojseba.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.shohojseba.data.model.Area
import com.example.shohojseba.navigation.Screen
import com.example.shohojseba.ui.customer.components.CategoryChip
import com.example.shohojseba.viewmodel.AreaViewModel
import com.example.shohojseba.viewmodel.CategoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    navController: NavController,

    viewModel: CategoryViewModel =
        viewModel(),

    areaViewModel: AreaViewModel =
        viewModel()

) {


    val categories by
    viewModel.categories


    val areas by
    areaViewModel.areas


    var selectedArea by remember {
        mutableStateOf<Area?>(null)
    }


    var areaExpanded by remember {
        mutableStateOf(false)
    }


    var showSelectAreaDialog by remember {
        mutableStateOf(false)
    }


    // =====================================================
    // LOAD DATA
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel.loadCategories()

        areaViewModel.loadAreas()

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
                        Color(
                            0xFF007A7A
                        ),

                    modifier =
                        Modifier.size(
                            48.dp
                        )

                )

            },

            title = {

                Text(
                    "Select Your Area"
                )

            },

            text = {

                Text(
                    "Please select your area first so we can show providers who serve your location."
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
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    Color(
                                        0xFF007A7A
                                    )

                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "Select Area"
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
    // MAIN SCREEN
    // =====================================================

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        listOf(

                            Color(
                                0xFFE9FFFA
                            ),

                            Color.White

                        )

                    )

                )
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    24.dp
                )

    ) {


        Text(

            text =
                "Good Morning 👋",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium

        )


        Text(

            text =
                "Find services near you",

            style =
                MaterialTheme
                    .typography
                    .titleMedium

        )


        Spacer(
            Modifier.height(
                22.dp
            )
        )


        // =====================================================
        // AREA
        // =====================================================

        Text(

            text =
                "Your Area",

            style =
                MaterialTheme
                    .typography
                    .titleMedium

        )


        Spacer(
            Modifier.height(
                8.dp
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

                label = {

                    Text(
                        "Select Area"
                    )

                },

                placeholder = {

                    Text(
                        "Choose your location"
                    )

                },

                leadingIcon = {

                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null,

                        tint =
                            Color(
                                0xFF007A7A
                            )

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
                        22.dp
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


        // =====================================================
        // SELECTED AREA
        // =====================================================

        if (
            selectedArea != null
        ) {

            Spacer(
                Modifier.height(
                    10.dp
                )
            )


            Card(

                colors =
                    CardDefaults
                        .cardColors(

                            containerColor =
                                Color(
                                    0xFFDDF8F3
                                )

                        ),

                shape =
                    RoundedCornerShape(
                        18.dp
                    )

            ) {

                Row(

                    modifier =
                        Modifier.padding(

                            horizontal =
                                14.dp,

                            vertical =
                                10.dp

                        ),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null,

                        tint =
                            Color(
                                0xFF007A7A
                            )

                    )


                    Spacer(
                        Modifier.width(
                            6.dp
                        )
                    )


                    Text(

                        text =
                            "Showing services in ${selectedArea!!.area_name}",

                        color =
                            Color(
                                0xFF007A7A
                            )

                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                22.dp
            )
        )


        // =====================================================
        // SEARCH
        // =====================================================

        OutlinedTextField(

            value =
                "",

            onValueChange = {},

            placeholder = {

                Text(
                    "Search services..."
                )

            },

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    30.dp
                )

        )


        Spacer(
            Modifier.height(
                25.dp
            )
        )


        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )

        ) {


            Card(

                modifier =
                    Modifier.weight(
                        1f
                    ),

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color(
                                0xFF007A7A
                            )

                    ),

                onClick = {

                    navController.navigate(
                        Screen.CustomerBookings.route
                    )

                }

            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            18.dp
                        ),

                    horizontalAlignment =
                        Alignment.CenterHorizontally

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Book,

                        contentDescription =
                            null,

                        tint =
                            Color.White

                    )


                    Spacer(
                        Modifier.height(
                            8.dp
                        )
                    )


                    Text(

                        text =
                            "My Bookings",

                        color =
                            Color.White

                    )

                }

            }


            Card(

                modifier =
                    Modifier.weight(
                        1f
                    ),

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color(
                                0xFFDDF8F3
                            )

                    )

            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            18.dp
                        ),

                    horizontalAlignment =
                        Alignment.CenterHorizontally

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.HomeRepairService,

                        contentDescription =
                            null,

                        tint =
                            Color(
                                0xFF007A7A
                            )

                    )


                    Spacer(
                        Modifier.height(
                            8.dp
                        )
                    )


                    Text(

                        text =
                            "Services",

                        color =
                            Color(
                                0xFF007A7A
                            )

                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                14.dp
            )
        )


        // =====================================================
        // FAVORITES
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
                        Color(
                            0xFFFFEBEE
                        )

                ),

            onClick = {

                navController.navigate(
                    Screen.Favorites.route
                )

            }

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

                Icon(

                    imageVector =
                        Icons.Default.Favorite,

                    contentDescription =
                        null,

                    tint =
                        Color(
                            0xFFE53935
                        )

                )


                Spacer(
                    Modifier.width(
                        12.dp
                    )
                )


                Column {

                    Text(

                        text =
                            "My Favorites",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    Text(

                        text =
                            "View your saved services",

                        color =
                            Color.Gray

                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                14.dp
            )
        )


        // =====================================================
        // NOTIFICATIONS
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
                        Color(
                            0xFFE3F2FD
                        )

                ),

            onClick = {

                navController.navigate(
                    Screen.Notifications.route
                )

            }

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


                Spacer(
                    Modifier.width(
                        12.dp
                    )
                )


                Column {

                    Text(

                        text =
                            "Notifications",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    Text(

                        text =
                            "View booking and service updates",

                        color =
                            Color.Gray

                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                14.dp
            )
        )


        // =====================================================
        // SERVICE REMINDERS
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
                        Color(
                            0xFFFFF3E0
                        )

                ),

            onClick = {

                navController.navigate(
                    Screen.ServiceReminders.route
                )

            }

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


                Spacer(
                    Modifier.width(
                        12.dp
                    )
                )


                Column {

                    Text(

                        text =
                            "Service Reminders",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )


                    Text(

                        text =
                            "View upcoming servicing dates",

                        color =
                            Color.Gray

                    )

                }

            }

        }


        Spacer(
            Modifier.height(
                30.dp
            )
        )


        // =====================================================
        // CATEGORIES
        // =====================================================

        Text(

            text =
                "Categories",

            style =
                MaterialTheme
                    .typography
                    .titleLarge

        )


        Spacer(
            Modifier.height(
                15.dp
            )
        )


        Row(

            modifier =
                Modifier.horizontalScroll(

                    rememberScrollState()

                ),

            horizontalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )

        ) {


            categories.forEach { category ->


                CategoryChip(

                    icon =
                        when (
                            category.category_name
                        ) {

                            "Cleaning" ->
                                "🧹"

                            "AC Service" ->
                                "❄️"

                            "Plumbing" ->
                                "🚰"

                            "Electrician" ->
                                "⚡"

                            else ->
                                "🔧"

                        },

                    name =
                        category.category_name,

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
                                        }"

                            )

                        }

                    }

                )

            }

        }


        Spacer(
            Modifier.height(
                35.dp
            )
        )


        // =====================================================
        // PROMOTIONAL CARD
        // =====================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    30.dp
                ),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(
                            0xFFDDF8F3
                        )

                )

        ) {

            Column(

                modifier =
                    Modifier.padding(
                        25.dp
                    )

            ) {

                Text(

                    text =
                        "20% OFF",

                    color =
                        Color(
                            0xFF00897B
                        )

                )


                Text(

                    text =
                        "Home Cleaning",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall

                )


                Text(

                    text =
                        "Professional cleaning at your doorstep"

                )


                Spacer(
                    Modifier.height(
                        15.dp
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

                                    it.category_name ==
                                            "Cleaning"

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
                                                }"

                                    )

                                }

                        }

                    },

                    shape =
                        RoundedCornerShape(
                            30.dp
                        )

                ) {

                    Text(
                        "Book Now"
                    )

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