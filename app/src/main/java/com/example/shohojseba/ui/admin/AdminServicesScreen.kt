package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.Service
import com.example.shohojseba.viewmodel.AdminViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminServicesScreen(

    viewModel: AdminViewModel =
        viewModel()

) {

    val services by
    viewModel.services

    val providers by
    viewModel.providers

    val categories by
    viewModel.categories

    val isLoading by
    viewModel.isLoading

    val message by
    viewModel.message


    var serviceToRemove by remember {
        mutableStateOf<Service?>(null)
    }

    var serviceToRestore by remember {
        mutableStateOf<Service?>(null)
    }

    var showMessageDialog by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {

        viewModel.loadServices()

        viewModel.loadProviders()

        viewModel.loadCategories()

    }


    LaunchedEffect(message) {

        if (message.isNotBlank()) {

            showMessageDialog =
                true

        }

    }


    // =====================================================
    // SUCCESS / ERROR
    // =====================================================

    if (showMessageDialog) {

        val success =
            message.contains(
                "successfully",
                ignoreCase = true
            )

        AlertDialog(

            onDismissRequest = {

                showMessageDialog =
                    false

                viewModel.clearMessage()

            },

            title = {

                Text(

                    if (success)
                        "Success"
                    else
                        "Notice"

                )

            },

            text = {

                Text(message)

            },

            confirmButton = {

                Button(

                    onClick = {

                        showMessageDialog =
                            false

                        viewModel.clearMessage()

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF007A7A)

                        )

                ) {

                    Text("OK")

                }

            },

            shape =
                RoundedCornerShape(24.dp)

        )

    }


    // =====================================================
    // REMOVE CONFIRMATION
    // =====================================================

    if (serviceToRemove != null) {

        AlertDialog(

            onDismissRequest = {

                serviceToRemove =
                    null

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFFC62828)

                )

            },

            title = {

                Text(
                    "Remove Service?"
                )

            },

            text = {

                Text(

                    "\"${serviceToRemove?.service_name}\" will no longer be available to customers. Existing booking history will remain."

                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        serviceToRemove
                            ?.service_id
                            ?.let {

                                viewModel.removeService(
                                    it
                                )

                            }

                        serviceToRemove =
                            null

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFFC62828)

                        )

                ) {

                    Text(
                        "Remove"
                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        serviceToRemove =
                            null

                    }

                ) {

                    Text(
                        "Cancel"
                    )

                }

            },

            shape =
                RoundedCornerShape(24.dp)

        )

    }


    // =====================================================
    // RESTORE CONFIRMATION
    // =====================================================

    if (serviceToRestore != null) {

        AlertDialog(

            onDismissRequest = {

                serviceToRestore =
                    null

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.CheckCircle,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF2E7D32)

                )

            },

            title = {

                Text(
                    "Restore Service?"
                )

            },

            text = {

                Text(

                    "\"${serviceToRestore?.service_name}\" will become active again."

                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        serviceToRestore
                            ?.service_id
                            ?.let {

                                viewModel.restoreService(
                                    it
                                )

                            }

                        serviceToRestore =
                            null

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF2E7D32)

                        )

                ) {

                    Text(
                        "Restore"
                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        serviceToRestore =
                            null

                    }

                ) {

                    Text("Cancel")

                }

            },

            shape =
                RoundedCornerShape(24.dp)

        )

    }


    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Manage Services"
                    )

                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                Color.Transparent

                        )

            )

        }

    ) { padding ->


        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        listOf(

                            Color(0xFFEFFFFB),

                            Color.White

                        )

                    )

                )
                .padding(padding)

        ) {


            when {

                isLoading &&
                        services.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        CircularProgressIndicator(

                            color =
                                Color(0xFF007A7A)

                        )

                    }

                }


                services.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(
                            "No services found"
                        )

                    }

                }


                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(
                                20.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                16.dp
                            )

                    ) {


                        // =================================================
                        // SUMMARY
                        // =================================================

                        item {

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
                                            Color(0xFFDDF8F3)

                                    )

                            ) {

                                Row(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                20.dp
                                            ),

                                    verticalAlignment =
                                        Alignment.CenterVertically

                                ) {

                                    Icon(

                                        imageVector =
                                            Icons.Default.HomeRepairService,

                                        contentDescription =
                                            null,

                                        tint =
                                            Color(0xFF007A7A)

                                    )

                                    Spacer(
                                        Modifier.width(
                                            10.dp
                                        )
                                    )

                                    Column {

                                        Text(

                                            text =
                                                "Total Services",

                                            color =
                                                Color.Gray

                                        )

                                        Text(

                                            text =
                                                services.size
                                                    .toString(),

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .headlineSmall,

                                            color =
                                                Color(0xFF007A7A)

                                        )

                                    }

                                }

                            }

                        }


                        // =================================================
                        // SERVICES
                        // =================================================

                        items(

                            items =
                                services,

                            key = {
                                it.service_id
                            }

                        ) { service ->


                            val providerName =

                                providers
                                    .firstOrNull {

                                        it.provider_id ==
                                                service.provider_id

                                    }
                                    ?.name
                                    ?: "Unknown Provider"


                            val categoryName =

                                categories
                                    .firstOrNull {

                                        it.category_id ==
                                                service.category_id

                                    }
                                    ?.category_name
                                    ?: "Unknown Category"


                            val removed =
                                service
                                    .service_status
                                    .uppercase() ==
                                        "REMOVED"


                            val statusColor =

                                if (removed)

                                    Color(0xFFC62828)

                                else

                                    Color(0xFF2E7D32)


                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        24.dp
                                    ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
                                            5.dp
                                        )

                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            20.dp
                                        )

                                ) {


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
                                                "🛠 ${service.service_name}",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleLarge,

                                            modifier =
                                                Modifier.weight(
                                                    1f
                                                )

                                        )


                                        Box(

                                            modifier =
                                                Modifier
                                                    .background(

                                                        statusColor.copy(
                                                            alpha =
                                                                0.15f
                                                        ),

                                                        RoundedCornerShape(
                                                            25.dp
                                                        )

                                                    )
                                                    .padding(

                                                        horizontal =
                                                            12.dp,

                                                        vertical =
                                                            6.dp

                                                    )

                                        ) {

                                            Text(

                                                text =
                                                    service.service_status,

                                                color =
                                                    statusColor

                                            )

                                        }

                                    }


                                    Spacer(
                                        Modifier.height(
                                            12.dp
                                        )
                                    )


                                    Text(

                                        text =
                                            "Category: $categoryName"

                                    )


                                    Spacer(
                                        Modifier.height(
                                            6.dp
                                        )
                                    )


                                    Row(

                                        verticalAlignment =
                                            Alignment.CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Person,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(0xFF007A7A),

                                            modifier =
                                                Modifier.size(
                                                    19.dp
                                                )

                                        )

                                        Spacer(
                                            Modifier.width(
                                                6.dp
                                            )
                                        )

                                        Text(
                                            providerName
                                        )

                                    }


                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )


                                    Text(
                                        "💰 Price: ${service.price} taka"
                                    )

                                    Text(
                                        "⏱ Duration: ${service.duration}"
                                    )


                                    if (
                                        !service.description
                                            .isNullOrBlank()
                                    ) {

                                        Spacer(
                                            Modifier.height(
                                                8.dp
                                            )
                                        )

                                        Text(
                                            service.description
                                        )

                                    }


                                    Spacer(
                                        Modifier.height(
                                            18.dp
                                        )
                                    )


                                    if (!removed) {

                                        Button(

                                            onClick = {

                                                serviceToRemove =
                                                    service

                                            },

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            colors =
                                                ButtonDefaults
                                                    .buttonColors(

                                                        containerColor =
                                                            Color(
                                                                0xFFC62828
                                                            )

                                                    ),

                                            shape =
                                                RoundedCornerShape(
                                                    16.dp
                                                )

                                        ) {

                                            Icon(

                                                imageVector =
                                                    Icons.Default.Delete,

                                                contentDescription =
                                                    null

                                            )

                                            Spacer(
                                                Modifier.width(
                                                    7.dp
                                                )
                                            )

                                            Text(
                                                "Remove Service"
                                            )

                                        }

                                    } else {

                                        Button(

                                            onClick = {

                                                serviceToRestore =
                                                    service

                                            },

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            colors =
                                                ButtonDefaults
                                                    .buttonColors(

                                                        containerColor =
                                                            Color(
                                                                0xFF2E7D32
                                                            )

                                                    ),

                                            shape =
                                                RoundedCornerShape(
                                                    16.dp
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
                                                    7.dp
                                                )
                                            )

                                            Text(
                                                "Restore Service"
                                            )

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}