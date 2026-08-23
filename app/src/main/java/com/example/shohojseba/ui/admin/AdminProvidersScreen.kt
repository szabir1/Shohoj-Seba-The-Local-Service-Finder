package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.viewmodel.AdminViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProvidersScreen(

    viewModel: AdminViewModel = viewModel()

) {

    // =====================================================
    // VIEWMODEL STATES
    // =====================================================

    val providers by viewModel.providers

    val isLoading by viewModel.isLoading

    val message by viewModel.message


    // =====================================================
    // DIALOG STATES
    // =====================================================

    var providerToSuspend by remember {
        mutableStateOf<Provider?>(null)
    }

    var providerToReactivate by remember {
        mutableStateOf<Provider?>(null)
    }

    var providerToRemove by remember {
        mutableStateOf<Provider?>(null)
    }

    var showMessageDialog by remember {
        mutableStateOf(false)
    }


    // =====================================================
    // LOAD PROVIDERS
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel.loadProviders()

    }


    // =====================================================
    // SHOW SUCCESS / ERROR MESSAGE
    // =====================================================

    LaunchedEffect(message) {

        if (message.isNotBlank()) {

            showMessageDialog = true

        }

    }


    // =====================================================
    // SUCCESS / ERROR DIALOG
    // =====================================================

    if (showMessageDialog) {

        val isSuccess =
            message.contains(
                "successfully",
                ignoreCase = true
            )

        AlertDialog(

            onDismissRequest = {

                showMessageDialog = false

                viewModel.clearMessage()

            },

            icon = {

                Icon(

                    imageVector =
                        if (isSuccess)
                            Icons.Default.CheckCircle
                        else
                            Icons.Default.Block,

                    contentDescription = null,

                    tint =
                        if (isSuccess)
                            Color(0xFF2E7D32)
                        else
                            Color(0xFFC62828),

                    modifier =
                        Modifier.size(48.dp)

                )

            },

            title = {

                Text(

                    text =
                        if (isSuccess)
                            "Success"
                        else
                            "Notice"

                )

            },

            text = {

                Text(
                    text = message
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showMessageDialog = false

                        viewModel.clearMessage()

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
    // SUSPEND CONFIRMATION
    // =====================================================

    if (providerToSuspend != null) {

        AlertDialog(

            onDismissRequest = {

                providerToSuspend = null

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Block,

                    contentDescription = null,

                    tint =
                        Color(0xFFFFA000),

                    modifier =
                        Modifier.size(48.dp)

                )

            },

            title = {

                Text(
                    "Suspend Provider?"
                )

            },

            text = {

                Text(

                    "${providerToSuspend?.name} will not be able to log in to the provider side until the account is reactivated."

                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        providerToSuspend
                            ?.provider_id
                            ?.let { providerId ->

                                viewModel
                                    .suspendProvider(
                                        providerId
                                    )

                            }

                        providerToSuspend =
                            null

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFFFFA000)

                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    Text(
                        "Suspend"
                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        providerToSuspend =
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
    // REACTIVATE CONFIRMATION
    // =====================================================

    if (providerToReactivate != null) {

        AlertDialog(

            onDismissRequest = {

                providerToReactivate =
                    null

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.CheckCircle,

                    contentDescription = null,

                    tint =
                        Color(0xFF2E7D32),

                    modifier =
                        Modifier.size(48.dp)

                )

            },

            title = {

                Text(
                    "Reactivate Provider?"
                )

            },

            text = {

                Text(

                    "${providerToReactivate?.name} will regain access to the provider account."

                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        providerToReactivate
                            ?.provider_id
                            ?.let { providerId ->

                                viewModel
                                    .reactivateProvider(
                                        providerId
                                    )

                            }

                        providerToReactivate =
                            null

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFF2E7D32)

                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    Text(
                        "Reactivate"
                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        providerToReactivate =
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
    // REMOVE CONFIRMATION
    // =====================================================

    if (providerToRemove != null) {

        AlertDialog(

            onDismissRequest = {

                providerToRemove =
                    null

            },

            icon = {

                Icon(

                    imageVector =
                        Icons.Default.Delete,

                    contentDescription = null,

                    tint =
                        Color(0xFFC62828),

                    modifier =
                        Modifier.size(48.dp)

                )

            },

            title = {

                Text(
                    "Remove Provider?"
                )

            },

            text = {

                Text(

                    "${providerToRemove?.name} will be removed from active use of ShohojSeba. Existing booking and review history will remain stored."

                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        providerToRemove
                            ?.provider_id
                            ?.let { providerId ->

                                viewModel
                                    .removeProvider(
                                        providerId
                                    )

                            }

                        providerToRemove =
                            null

                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Color(0xFFC62828)

                        ),

                    shape =
                        RoundedCornerShape(14.dp)

                ) {

                    Text(
                        "Remove"
                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        providerToRemove =
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
    // MAIN SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Providers"
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


                // =================================================
                // LOADING
                // =================================================

                isLoading &&
                        providers.isEmpty() -> {

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


                // =================================================
                // EMPTY
                // =================================================

                providers.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Card(

                            shape =
                                RoundedCornerShape(
                                    24.dp
                                ),

                            elevation =
                                CardDefaults.cardElevation(
                                    5.dp
                                )

                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(
                                        28.dp
                                    ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.People,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(0xFF007A7A),

                                    modifier =
                                        Modifier.size(
                                            56.dp
                                        )

                                )

                                Spacer(
                                    Modifier.height(
                                        10.dp
                                    )
                                )

                                Text(

                                    text =
                                        "No providers found",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium

                                )

                            }

                        }

                    }

                }


                // =================================================
                // PROVIDER LIST
                // =================================================

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
                        // SUMMARY CARD
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

                                    ),

                                elevation =
                                    CardDefaults.cardElevation(
                                        4.dp
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
                                            Icons.Default.People,

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
                                                "Total Providers",

                                            color =
                                                Color.Gray

                                        )

                                        Text(

                                            text =
                                                providers.size
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
                        // PROVIDER CARDS
                        // =================================================

                        items(

                            items =
                                providers,

                            key = { provider ->

                                provider.provider_id
                                    ?: provider.auth_user_id
                                    ?: provider.email

                            }

                        ) { provider ->


                            val status =
                                provider
                                    .account_status
                                    .uppercase()


                            val statusColor =

                                when (status) {

                                    "ACTIVE" ->

                                        Color(
                                            0xFF2E7D32
                                        )

                                    "SUSPENDED" ->

                                        Color(
                                            0xFFFFA000
                                        )

                                    "REMOVED" ->

                                        Color(
                                            0xFFC62828
                                        )

                                    else ->

                                        Color.Gray

                                }


                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        24.dp
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


                                    // =================================================
                                    // PROVIDER NAME + STATUS
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
                                                Modifier.weight(
                                                    1f
                                                )

                                        ) {

                                            Text(

                                                text =
                                                    "👤 ${provider.name}",

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .titleLarge

                                            )


                                            if (
                                                provider.provider_id != null
                                            ) {

                                                Spacer(
                                                    Modifier.height(
                                                        3.dp
                                                    )
                                                )

                                                Text(

                                                    text =
                                                        "Provider ID: ${provider.provider_id}",

                                                    color =
                                                        Color.Gray,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .bodySmall

                                                )

                                            }

                                        }


                                        Box(

                                            modifier =
                                                Modifier
                                                    .background(

                                                        statusColor
                                                            .copy(
                                                                alpha =
                                                                    0.15f
                                                            ),

                                                        RoundedCornerShape(
                                                            30.dp
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
                                                    status,

                                                color =
                                                    statusColor,

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .labelMedium

                                            )

                                        }

                                    }


                                    Spacer(
                                        Modifier.height(
                                            14.dp
                                        )
                                    )


                                    HorizontalDivider()


                                    Spacer(
                                        Modifier.height(
                                            14.dp
                                        )
                                    )


                                    // =================================================
                                    // EMAIL
                                    // =================================================

                                    Row(

                                        verticalAlignment =
                                            Alignment.CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Email,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(0xFF007A7A),

                                            modifier =
                                                Modifier.size(
                                                    20.dp
                                                )

                                        )

                                        Spacer(
                                            Modifier.width(
                                                8.dp
                                            )
                                        )

                                        Text(
                                            provider.email
                                        )

                                    }


                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )


                                    // =================================================
                                    // PHONE
                                    // =================================================

                                    Row(

                                        verticalAlignment =
                                            Alignment.CenterVertically

                                    ) {

                                        Icon(

                                            imageVector =
                                                Icons.Default.Phone,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(0xFF007A7A),

                                            modifier =
                                                Modifier.size(
                                                    20.dp
                                                )

                                        )

                                        Spacer(
                                            Modifier.width(
                                                8.dp
                                            )
                                        )

                                        Text(
                                            provider.phone
                                        )

                                    }


                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )


                                    Text(

                                        text =
                                            "⭐ Experience: ${provider.experience} years"

                                    )


                                    // =================================================
                                    // OLD PROVIDER WARNING
                                    // =================================================

                                    if (
                                        provider.auth_user_id == null
                                    ) {

                                        Spacer(
                                            Modifier.height(
                                                12.dp
                                            )
                                        )

                                        Card(

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            shape =
                                                RoundedCornerShape(
                                                    14.dp
                                                ),

                                            colors =
                                                CardDefaults.cardColors(

                                                    containerColor =
                                                        Color(
                                                            0xFFFFF8E1
                                                        )

                                                )

                                        ) {

                                            Text(

                                                text =
                                                    "⚠ This provider is not connected to a Supabase Auth account.",

                                                modifier =
                                                    Modifier.padding(
                                                        12.dp
                                                    ),

                                                color =
                                                    Color(
                                                        0xFFFF8F00
                                                    ),

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .bodySmall

                                            )

                                        }

                                    }


                                    Spacer(
                                        Modifier.height(
                                            18.dp
                                        )
                                    )


                                    // =================================================
                                    // ACTIVE ACTIONS
                                    // =================================================

                                    if (
                                        status ==
                                        "ACTIVE"
                                    ) {

                                        Row(

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            horizontalArrangement =
                                                Arrangement.spacedBy(
                                                    10.dp
                                                )

                                        ) {


                                            OutlinedButton(

                                                onClick = {

                                                    providerToSuspend =
                                                        provider

                                                },

                                                modifier =
                                                    Modifier.weight(
                                                        1f
                                                    ),

                                                colors =
                                                    ButtonDefaults
                                                        .outlinedButtonColors(

                                                            contentColor =
                                                                Color(
                                                                    0xFFFFA000
                                                                )

                                                        ),

                                                shape =
                                                    RoundedCornerShape(
                                                        16.dp
                                                    )

                                            ) {

                                                Text(
                                                    "Suspend"
                                                )

                                            }


                                            Button(

                                                onClick = {

                                                    providerToRemove =
                                                        provider

                                                },

                                                modifier =
                                                    Modifier.weight(
                                                        1f
                                                    ),

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

                                                Text(
                                                    "Remove"
                                                )

                                            }

                                        }

                                    }


                                    // =================================================
                                    // SUSPENDED ACTIONS
                                    // =================================================

                                    else if (
                                        status ==
                                        "SUSPENDED"
                                    ) {

                                        Row(

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            horizontalArrangement =
                                                Arrangement.spacedBy(
                                                    10.dp
                                                )

                                        ) {


                                            Button(

                                                onClick = {

                                                    providerToReactivate =
                                                        provider

                                                },

                                                modifier =
                                                    Modifier.weight(
                                                        1f
                                                    ),

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

                                                Text(
                                                    "Reactivate"
                                                )

                                            }


                                            Button(

                                                onClick = {

                                                    providerToRemove =
                                                        provider

                                                },

                                                modifier =
                                                    Modifier.weight(
                                                        1f
                                                    ),

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

                                                Text(
                                                    "Remove"
                                                )

                                            }

                                        }

                                    }


                                    // =================================================
                                    // REMOVED
                                    // =================================================

                                    else if (
                                        status ==
                                        "REMOVED"
                                    ) {

                                        Card(

                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            shape =
                                                RoundedCornerShape(
                                                    16.dp
                                                ),

                                            colors =
                                                CardDefaults.cardColors(

                                                    containerColor =
                                                        Color(
                                                            0xFFFFEBEE
                                                        )

                                                )

                                        ) {

                                            Text(

                                                text =
                                                    "This provider has been removed from the platform.",

                                                modifier =
                                                    Modifier.padding(
                                                        14.dp
                                                    ),

                                                color =
                                                    Color(
                                                        0xFFC62828
                                                    )

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