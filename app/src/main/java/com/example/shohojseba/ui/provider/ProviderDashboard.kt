package com.example.shohojseba.ui.provider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.ProviderViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProviderDashboard(

    viewModel: ProviderViewModel = viewModel(),

    onAddServiceClick: () -> Unit,

    onBookingRequestsClick: () -> Unit

) {

    // =====================================================
    // VIEWMODEL STATES
    // =====================================================

    val provider by viewModel.provider

    val services by viewModel.services

    val areas by viewModel.areas

    val selectedAreaIds by viewModel.selectedAreaIds

    val message by viewModel.message


    // =====================================================
    // AREA SUCCESS DIALOG STATE
    // =====================================================

    var showAreaSavedDialog by remember {
        mutableStateOf(false)
    }


    // =====================================================
    // LOAD PROVIDER DATA
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel.loadProviderProfile()

    }


    // =====================================================
    // SHOW AREA SUCCESS DIALOG
    // =====================================================

    LaunchedEffect(message) {

        if (message == "Service areas updated successfully") {

            showAreaSavedDialog = true

        }

    }


    // =====================================================
    // AREA SAVED SUCCESS DIALOG
    // =====================================================

    if (showAreaSavedDialog) {

        AlertDialog(

            onDismissRequest = {

                showAreaSavedDialog = false

            },

            icon = {

                Icon(

                    imageVector = Icons.Default.LocationOn,

                    contentDescription = null,

                    tint = Color(0xFF007A7A),

                    modifier = Modifier.size(50.dp)

                )

            },

            title = {

                Text(
                    text = "Service Areas Updated"
                )

            },

            text = {

                Text(
                    text =
                        "Your selected service areas have been saved successfully."
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showAreaSavedDialog = false

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF007A7A)

                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    Text("OK")

                }

            },

            shape =
                RoundedCornerShape(24.dp)

        )

    }


    // =====================================================
    // MAIN SCREEN
    // =====================================================

    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(

                Brush.verticalGradient(

                    colors = listOf(

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

            text = "Provider Dashboard",

            style =
                MaterialTheme.typography.headlineMedium

        )


        Spacer(
            Modifier.height(8.dp)
        )


        Text(

            text =
                "Manage your services easily",

            style =
                MaterialTheme.typography.bodyLarge

        )


        Spacer(
            Modifier.height(25.dp)
        )


        // =====================================================
        // PROFILE CARD
        // =====================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(28.dp),

            elevation =
                CardDefaults.cardElevation(
                    6.dp
                ),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFFDDF7F1)

                )

        ) {

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ) {

                Text(

                    text =
                        "👤 Provider Profile",

                    style =
                        MaterialTheme.typography.titleLarge

                )


                Spacer(
                    Modifier.height(15.dp)
                )


                if (provider != null) {

                    Text(
                        text =
                            "Name: ${provider!!.name}"
                    )

                    Text(
                        text =
                            "Email: ${provider!!.email}"
                    )

                    Text(
                        text =
                            "Phone: ${provider!!.phone}"
                    )

                    Text(
                        text =
                            "⭐ Experience: ${provider!!.experience} years"
                    )

                } else {

                    Text(
                        "Loading provider..."
                    )

                }

            }

        }


        Spacer(
            Modifier.height(22.dp)
        )


        // =====================================================
        // SERVICE AREA CARD
        // =====================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(28.dp),

            elevation =
                CardDefaults.cardElevation(
                    5.dp
                )

        ) {

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ) {


                // ---------------- Header ----------------

                Row {

                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF007A7A)

                    )


                    Spacer(
                        Modifier.width(8.dp)
                    )


                    Text(

                        text =
                            "Service Areas",

                        style =
                            MaterialTheme.typography.titleLarge

                    )

                }


                Spacer(
                    Modifier.height(8.dp)
                )


                Text(

                    text =
                        "Select all areas where you provide services.",

                    color =
                        Color.Gray

                )


                Spacer(
                    Modifier.height(16.dp)
                )


                // =====================================================
                // AREA CHIPS
                // =====================================================

                if (areas.isEmpty()) {

                    Text(
                        text =
                            "No areas available"
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

                                selectedAreaIds.contains(
                                    area.area_id
                                )


                            FilterChip(

                                selected =
                                    selected,

                                onClick = {

                                    viewModel.toggleArea(
                                        area.area_id
                                    )

                                },

                                label = {

                                    Text(
                                        text =
                                            area.area_name
                                    )

                                },

                                leadingIcon =

                                    if (selected) {

                                        {

                                            Text("✓")

                                        }

                                    } else {

                                        null

                                    }

                            )

                        }

                    }

                }


                Spacer(
                    Modifier.height(18.dp)
                )


                // =====================================================
                // SAVE SERVICE AREAS BUTTON
                // =====================================================

                Button(

                    onClick = {

                        viewModel
                            .saveProviderAreas()

                    },

                    enabled =
                        areas.isNotEmpty(),

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF007A7A)

                        )

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null

                    )


                    Spacer(
                        Modifier.width(7.dp)
                    )


                    Text(
                        "Save Service Areas"
                    )

                }

            }

        }


        Spacer(
            Modifier.height(25.dp)
        )


        // =====================================================
        // ADD SERVICE BUTTON
        // =====================================================

        Button(

            onClick =
                onAddServiceClick,

            modifier =
                Modifier

                    .fillMaxWidth()

                    .height(55.dp),

            shape =
                RoundedCornerShape(30.dp)

        ) {

            Text(
                "＋ Add New Service"
            )

        }


        Spacer(
            Modifier.height(14.dp)
        )


        // =====================================================
        // BOOKING REQUEST BUTTON
        // =====================================================

        OutlinedButton(

            onClick =
                onBookingRequestsClick,

            modifier =
                Modifier

                    .fillMaxWidth()

                    .height(55.dp),

            shape =
                RoundedCornerShape(30.dp),

            colors =
                ButtonDefaults.outlinedButtonColors(

                    contentColor =
                        Color(0xFF007A7A)

                )

        ) {

            Icon(

                imageVector =
                    Icons.Default.CalendarMonth,

                contentDescription =
                    null

            )


            Spacer(
                Modifier.width(8.dp)
            )


            Text(
                "Booking Requests"
            )

        }


        Spacer(
            Modifier.height(30.dp)
        )


        // =====================================================
        // MY SERVICES
        // =====================================================

        Text(

            text =
                "My Services",

            style =
                MaterialTheme.typography.headlineSmall

        )


        Spacer(
            Modifier.height(15.dp)
        )


        // =====================================================
        // NO SERVICES
        // =====================================================

        if (services.isEmpty()) {

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(25.dp)

            ) {

                Text(

                    text =
                        "No services added yet",

                    modifier =
                        Modifier.padding(20.dp)

                )

            }

        } else {


            // =====================================================
            // SERVICE LIST
            // =====================================================

            services.forEach { service ->

                Card(

                    modifier =
                        Modifier

                            .fillMaxWidth()

                            .padding(
                                vertical = 8.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            25.dp
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            5.dp
                        )

                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                20.dp
                            )

                    ) {

                        Text(

                            text =
                                "🛠 ${service.service_name}",

                            style =
                                MaterialTheme.typography.titleLarge

                        )


                        Spacer(
                            Modifier.height(10.dp)
                        )


                        Text(

                            text =
                                "💰 Price: ${service.price} taka"

                        )


                        Text(

                            text =
                                "⏱ Duration: ${service.duration}"

                        )

                    }

                }

            }

        }


        Spacer(
            Modifier.height(20.dp)
        )


        // =====================================================
        // MESSAGE
        // =====================================================

        if (message.isNotEmpty() &&
            message != "Service areas updated successfully"
        ) {

            Text(

                text =
                    message,

                color =
                    MaterialTheme.colorScheme.primary

            )

        }


        Spacer(
            Modifier.height(30.dp)
        )

    }

}