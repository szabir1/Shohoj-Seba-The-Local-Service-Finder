package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
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

import com.example.shohojseba.viewmodel.AdminViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCustomersScreen(

    viewModel: AdminViewModel = viewModel()

) {

    val customers by
    viewModel.customers

    val isLoading by
    viewModel.isLoading

    val message by
    viewModel.message


    // =====================================================
    // LOAD CUSTOMERS
    // =====================================================

    LaunchedEffect(Unit) {

        viewModel.loadCustomers()

    }


    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Customers"
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
                        customers.isEmpty() -> {

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

                customers.isEmpty() -> {

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
                                CardDefaults
                                    .cardElevation(
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

                                    modifier =
                                        Modifier.size(
                                            56.dp
                                        ),

                                    tint =
                                        Color(0xFF007A7A)

                                )

                                Spacer(
                                    Modifier.height(
                                        10.dp
                                    )
                                )

                                Text(

                                    text =
                                        "No customers found",

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
                // CUSTOMER LIST
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
                                14.dp
                            )

                    ) {


                        // =================================================
                        // TOTAL CUSTOMER SUMMARY
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
                                    CardDefaults
                                        .cardColors(

                                            containerColor =
                                                Color(
                                                    0xFFDDF8F3
                                                )

                                        ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
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
                                                "Total Customers",

                                            color =
                                                Color.Gray

                                        )

                                        Text(

                                            text =
                                                customers.size
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
                        // CUSTOMER CARDS
                        // =================================================

                        items(

                            items =
                                customers,

                            key = { customer ->

                                customer.customer_id
                                    ?: customer.auth_user_id

                            }

                        ) { customer ->


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


                                    // -------------------------------------
                                    // NAME
                                    // -------------------------------------

                                    Text(

                                        text =
                                            "👤 ${customer.name}",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge

                                    )


                                    if (
                                        customer.customer_id != null
                                    ) {

                                        Spacer(
                                            Modifier.height(
                                                4.dp
                                            )
                                        )

                                        Text(

                                            text =
                                                "Customer ID: ${customer.customer_id}",

                                            color =
                                                Color.Gray,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall

                                        )

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


                                    // -------------------------------------
                                    // EMAIL
                                    // -------------------------------------

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
                                            customer.email
                                        )

                                    }


                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )


                                    // -------------------------------------
                                    // PHONE
                                    // -------------------------------------

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
                                            customer.phone
                                        )

                                    }

                                }

                            }

                        }

                    }

                }

            }


            // =====================================================
            // ERROR MESSAGE
            // =====================================================

            if (
                message.isNotBlank() &&
                customers.isEmpty() &&
                !isLoading
            ) {

                Text(

                    text = message,

                    color =
                        MaterialTheme
                            .colorScheme
                            .error,

                    modifier =
                        Modifier
                            .align(
                                Alignment.BottomCenter
                            )
                            .padding(
                                20.dp
                            )

                )

            }

        }

    }

}