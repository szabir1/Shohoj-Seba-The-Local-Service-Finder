package com.example.shohojseba.ui.customer

import android.app.DatePickerDialog
import android.app.TimePickerDialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.BookingRequest
import com.example.shohojseba.viewmodel.BookingViewModel

import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(

    providerId: Long,

    serviceId: Long,

    serviceName: String,

    providerName: String,

    // =====================================================
    // PROMOTION PRICE DATA
    // =====================================================

    originalPrice: Double = 0.0,

    discountPercent: Double = 0.0,

    finalPrice: Double = 0.0,

    viewModel: BookingViewModel =
        viewModel()

) {


    val context =
        LocalContext.current


    var bookingDate by remember {
        mutableStateOf("")
    }


    var bookingTime by remember {
        mutableStateOf("")
    }


    var address by remember {
        mutableStateOf("")
    }


    var problem by remember {
        mutableStateOf("")
    }


    // =====================================================
    // QUOTATION
    // =====================================================

    var requestQuotation by remember {

        mutableStateOf(false)

    }


    val isLoading by
    viewModel.isLoading


    val bookingSuccess by
    viewModel.bookingSuccess


    val background =
        Color(0xFFEAF7F5)


    val primary =
        Color(0xFF007A7A)


    // =====================================================
    // PROMOTION CHECK
    // =====================================================

    val hasPromotion =

        discountPercent > 0.0 &&
                originalPrice > 0.0 &&
                finalPrice > 0.0 &&
                finalPrice < originalPrice


    // =====================================================
    // SUCCESS POPUP
    // =====================================================

    if (
        bookingSuccess
    ) {

        AlertDialog(

            onDismissRequest = {

                viewModel
                    .resetBookingState()

            },

            icon = {

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
                            60.dp
                        )

                )

            },

            title = {

                Text(

                    text =

                        if (
                            requestQuotation
                        ) {

                            "Quotation Requested!"

                        } else {

                            "Booking Confirmed!"

                        },

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall

                )

            },

            text = {


                val successMessage =

                    if (
                        requestQuotation
                    ) {

                        "Your quotation request has been sent to the provider successfully.\n\nCurrent Status: Quotation Requested"

                    } else if (
                        hasPromotion
                    ) {

                        "Your booking has been sent successfully.\n\n20%% promotional discount applied.\nFinal Price: ৳%.0f\n\nCurrent Status: Pending"
                            .format(
                                finalPrice
                            )

                    } else {

                        "Your booking has been sent to the provider successfully.\n\nCurrent Status: Pending"

                    }


                Text(
                    successMessage
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        viewModel
                            .resetBookingState()

                    },

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    primary

                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )

                ) {

                    Text(
                        "Awesome!"
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
    // SCREEN
    // =====================================================

    Scaffold(

        containerColor =
            background,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Book Service"
                    )

                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                background

                        )

            )

        }

    ) { padding ->


        Column(

            modifier =
                Modifier
                    .padding(
                        padding
                    )
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .imePadding()
                    .padding(
                        20.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    18.dp
                )

        ) {


            // =================================================
            // SERVICE
            // =================================================

            Card(

                shape =
                    RoundedCornerShape(
                        22.dp
                    ),

                elevation =
                    CardDefaults
                        .cardElevation(
                            6.dp
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
                            "🛠",

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
                            serviceName,

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

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
                            "Provider: $providerName",

                        color =
                            Color.Gray

                    )

                }

            }


            // =================================================
            // PROMOTIONAL PRICE SUMMARY
            // =================================================

            if (
                hasPromotion &&
                !requestQuotation
            ) {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            22.dp
                        ),

                    colors =
                        CardDefaults
                            .cardColors(

                                containerColor =
                                    Color(
                                        0xFFE0F2F1
                                    )

                            )

                ) {


                    Column(

                        modifier =
                            Modifier.padding(
                                18.dp
                            )

                    ) {


                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.LocalOffer,

                                contentDescription =
                                    null,

                                tint =
                                    Color(
                                        0xFF00897B
                                    )

                            )


                            Spacer(
                                Modifier.width(
                                    8.dp
                                )
                            )


                            Text(

                                text =
                                    "Special Offer Applied",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF00695C
                                    )

                            )

                        }


                        Spacer(
                            Modifier.height(
                                16.dp
                            )
                        )


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Text(
                                "Original Price"
                            )


                            Text(

                                text =
                                    "৳%.0f"
                                        .format(
                                            originalPrice
                                        )

                            )

                        }


                        Spacer(
                            Modifier.height(
                                7.dp
                            )
                        )


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Text(

                                text =
                                    "Promotion",

                                color =
                                    Color(
                                        0xFF00897B
                                    )

                            )


                            Text(

                                text =
                                    "-%.0f%%"
                                        .format(
                                            discountPercent
                                        ),

                                color =
                                    Color(
                                        0xFF00897B
                                    ),

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }


                        HorizontalDivider(

                            modifier =
                                Modifier.padding(

                                    vertical =
                                        12.dp

                                )

                        )


                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Text(

                                text =
                                    "Final Price",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold

                            )


                            Text(

                                text =
                                    "৳%.0f"
                                        .format(
                                            finalPrice
                                        ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(
                                        0xFF00897B
                                    )

                            )

                        }

                    }

                }

            }


            // =================================================
            // DATE
            // =================================================

            OutlinedTextField(

                value =
                    bookingDate,

                onValueChange = {},

                readOnly =
                    true,

                label = {

                    Text(
                        "Booking Date"
                    )

                },

                leadingIcon = {

                    Icon(

                        Icons.Default.CalendarMonth,

                        null

                    )

                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                trailingIcon = {

                    TextButton(

                        onClick = {


                            val calendar =
                                Calendar.getInstance()


                            DatePickerDialog(

                                context,

                                {
                                        _,
                                        year,
                                        month,
                                        day ->


                                    bookingDate =
                                        String.format(

                                            "%04d-%02d-%02d",

                                            year,

                                            month + 1,

                                            day

                                        )

                                },

                                calendar.get(
                                    Calendar.YEAR
                                ),

                                calendar.get(
                                    Calendar.MONTH
                                ),

                                calendar.get(
                                    Calendar.DAY_OF_MONTH
                                )

                            ).show()

                        }

                    ) {

                        Text(
                            "Pick"
                        )

                    }

                }

            )


            // =================================================
            // TIME
            // =================================================

            OutlinedTextField(

                value =
                    bookingTime,

                onValueChange = {},

                readOnly =
                    true,

                label = {

                    Text(
                        "Booking Time"
                    )

                },

                leadingIcon = {

                    Icon(

                        Icons.Default.Schedule,

                        null

                    )

                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                trailingIcon = {

                    TextButton(

                        onClick = {


                            val calendar =
                                Calendar.getInstance()


                            TimePickerDialog(

                                context,

                                {
                                        _,
                                        hour,
                                        minute ->


                                    bookingTime =
                                        String.format(

                                            "%02d:%02d",

                                            hour,

                                            minute

                                        )

                                },

                                calendar.get(
                                    Calendar.HOUR_OF_DAY
                                ),

                                calendar.get(
                                    Calendar.MINUTE
                                ),

                                true

                            ).show()

                        }

                    ) {

                        Text(
                            "Pick"
                        )

                    }

                }

            )


            // =================================================
            // ADDRESS
            // =================================================

            OutlinedTextField(

                value =
                    address,

                onValueChange = {

                    address =
                        it

                },

                label = {

                    Text(
                        "Service Address"
                    )

                },

                leadingIcon = {

                    Icon(

                        Icons.Default.LocationOn,

                        null

                    )

                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    )

            )


            // =================================================
            // PROBLEM
            // =================================================

            OutlinedTextField(

                value =
                    problem,

                onValueChange = {

                    problem =
                        it

                },

                label = {

                    Text(
                        "Describe your problem"
                    )

                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            150.dp
                        ),

                shape =
                    RoundedCornerShape(
                        16.dp
                    )

            )


            // =================================================
            // QUOTATION
            // =================================================

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        18.dp
                    ),

                colors =
                    CardDefaults
                        .cardColors(

                            containerColor =
                                Color(
                                    0xFFFFF8E1
                                )

                        )

            ) {


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                14.dp
                            ),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Checkbox(

                        checked =
                            requestQuotation,

                        onCheckedChange = {

                            requestQuotation =
                                it

                        }

                    )


                    Spacer(
                        Modifier.width(
                            8.dp
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
                                "Request custom quotation",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleSmall

                        )


                        Spacer(
                            Modifier.height(
                                3.dp
                            )
                        )


                        Text(

                            text =

                                if (
                                    hasPromotion
                                ) {

                                    "A quotation uses the provider's custom price instead of the promotional fixed price."

                                } else {

                                    "Select this if the job may require a custom price based on the problem."

                                },

                            color =
                                Color.Gray,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall

                        )

                    }

                }

            }


            Spacer(
                Modifier.height(
                    8.dp
                )
            )


            // =================================================
            // CONFIRM
            // =================================================

            Button(

                onClick = {


                    val customerId =
                        UserSession.customerId


                    if (
                        customerId != null &&
                        bookingDate.isNotBlank() &&
                        bookingTime.isNotBlank() &&
                        address.isNotBlank() &&
                        problem.isNotBlank()
                    ) {


                        // -----------------------------------------
                        // Quotation overrides promotional price
                        // -----------------------------------------

                        val savedOriginalPrice =

                            if (
                                requestQuotation
                            ) {

                                null

                            } else if (
                                originalPrice > 0
                            ) {

                                originalPrice

                            } else {

                                null

                            }


                        val savedDiscount =

                            if (
                                requestQuotation
                            ) {

                                0.0

                            } else {

                                discountPercent

                            }


                        val savedFinalPrice =

                            if (
                                requestQuotation
                            ) {

                                null

                            } else if (
                                finalPrice > 0
                            ) {

                                finalPrice

                            } else {

                                null

                            }


                        viewModel
                            .createBooking(

                                BookingRequest(

                                    booking_date =
                                        bookingDate,

                                    booking_time =
                                        bookingTime,

                                    address =
                                        address,

                                    problem_description =
                                        problem,

                                    status =

                                        if (
                                            requestQuotation
                                        ) {

                                            "Quotation Requested"

                                        } else {

                                            "Pending"

                                        },

                                    customer_id =
                                        customerId,

                                    provider_id =
                                        providerId,

                                    service_id =
                                        serviceId,

                                    quotation_requested =
                                        requestQuotation,

                                    original_price =
                                        savedOriginalPrice,

                                    discount_percent =
                                        savedDiscount,

                                    final_price =
                                        savedFinalPrice

                                )

                            )

                    }

                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            56.dp
                        ),

                shape =
                    RoundedCornerShape(
                        18.dp
                    ),

                colors =
                    ButtonDefaults
                        .buttonColors(

                            containerColor =
                                primary

                        )

            ) {


                if (
                    isLoading
                ) {

                    CircularProgressIndicator(

                        color =
                            Color.White,

                        strokeWidth =
                            2.dp

                    )

                } else {

                    Text(

                        text =

                            if (
                                requestQuotation
                            ) {

                                "Request Quotation"

                            } else if (
                                hasPromotion
                            ) {

                                "Book for ৳%.0f"
                                    .format(
                                        finalPrice
                                    )

                            } else {

                                "Confirm Booking"

                            },

                        fontSize =
                            16.sp

                    )

                }

            }


            Spacer(
                Modifier.height(
                    20.dp
                )
            )

        }

    }

}