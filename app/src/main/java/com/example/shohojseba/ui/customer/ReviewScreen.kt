package com.example.shohojseba.ui.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.viewmodel.ReviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(

    bookingId: Long,

    providerId: Long,

    serviceName: String,

    providerName: String,

    onReviewSubmitted: () -> Unit,

    viewModel: ReviewViewModel = viewModel()

) {

    var rating by remember {
        mutableIntStateOf(0)
    }

    var comment by remember {
        mutableStateOf("")
    }

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }

    val isLoading by viewModel.isLoading
    val reviewSuccess by viewModel.reviewSuccess
    val message by viewModel.message
    val existingReview by viewModel.existingReview

    val primary = Color(0xFF007A7A)
    val background = Color(0xFFEFFFFB)

    LaunchedEffect(bookingId) {

        viewModel.clearExistingReview()

        viewModel.checkExistingReview(
            bookingId
        )
    }

    LaunchedEffect(reviewSuccess) {

        if (reviewSuccess) {
            showSuccessDialog = true
        }
    }

    // ================= SUCCESS DIALOG =================

    if (showSuccessDialog) {

        AlertDialog(

            onDismissRequest = {},

            icon = {

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(60.dp)
                )

            },

            title = {

                Text(
                    text = "Review Submitted!",
                    style = MaterialTheme.typography.headlineSmall
                )

            },

            text = {

                Text(
                    text =
                        "Thank you for sharing your experience. Your review has been added successfully."
                )

            },

            confirmButton = {

                Button(

                    onClick = {

                        showSuccessDialog = false

                        viewModel.resetReviewState()

                        onReviewSubmitted()

                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    ),

                    shape = RoundedCornerShape(16.dp)

                ) {

                    Text("Done")

                }

            },

            shape = RoundedCornerShape(26.dp)

        )

    }

    Scaffold(

        containerColor = background,

        topBar = {

            TopAppBar(

                title = {

                    Text("Rate Your Service")

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background
                )

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .imePadding()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            // ================= SERVICE CARD =================

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(26.dp),

                elevation = CardDefaults.cardElevation(
                    6.dp
                )

            ) {

                Column(

                    modifier = Modifier.padding(22.dp)

                ) {

                    Text(
                        text = "⭐ Share Your Experience",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        Modifier.height(14.dp)
                    )

                    Text(
                        text = serviceName,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(
                        Modifier.height(4.dp)
                    )

                    Text(
                        text = "Provider: $providerName",
                        color = Color.Gray
                    )

                }

            }

            Spacer(
                Modifier.height(30.dp)
            )

            Text(

                text = "How was your service?",

                style = MaterialTheme.typography.titleLarge

            )

            Spacer(
                Modifier.height(16.dp)
            )

            // ================= STAR RATING =================

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.Center

            ) {

                for (star in 1..5) {

                    Icon(

                        imageVector =
                            if (star <= rating)
                                Icons.Filled.Star
                            else
                                Icons.Outlined.Star,

                        contentDescription =
                            "$star Star",

                        tint =
                            if (star <= rating)
                                Color(0xFFFFC107)
                            else
                                Color.LightGray,

                        modifier = Modifier
                            .size(48.dp)
                            .clickable {

                                rating = star

                            }
                            .padding(4.dp)

                    )

                }

            }

            Spacer(
                Modifier.height(8.dp)
            )

            Text(

                text =
                    when (rating) {

                        1 -> "Poor"
                        2 -> "Fair"
                        3 -> "Good"
                        4 -> "Very Good"
                        5 -> "Excellent!"
                        else -> "Tap a star to rate"

                    },

                color =
                    if (rating > 0)
                        primary
                    else
                        Color.Gray

            )

            Spacer(
                Modifier.height(30.dp)
            )

            // ================= COMMENT =================

            OutlinedTextField(

                value = comment,

                onValueChange = {

                    comment = it

                },

                label = {

                    Text("Write a review")

                },

                placeholder = {

                    Text(
                        "Tell us about your experience..."
                    )

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),

                shape = RoundedCornerShape(20.dp),

                maxLines = 6

            )

            Spacer(
                Modifier.height(20.dp)
            )

            // ================= EXISTING REVIEW =================

            if (existingReview != null) {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(

                        containerColor =
                            Color(0xFFFFF8E1)

                    )

                ) {

                    Column(

                        modifier = Modifier.padding(18.dp)

                    ) {

                        Text(

                            text = "Already Reviewed",

                            style = MaterialTheme.typography.titleMedium

                        )

                        Spacer(
                            Modifier.height(6.dp)
                        )

                        Text(

                            text =
                                "You have already submitted a review for this booking."

                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Text(

                            text =
                                "Rating: ${existingReview?.rating ?: 0}/5 ⭐"

                        )

                    }

                }

                Spacer(
                    Modifier.height(20.dp)
                )

            }

            // ================= MESSAGE =================

            if (
                message.isNotBlank() &&
                existingReview == null
            ) {

                Text(

                    text = message,

                    color =
                        if (reviewSuccess)
                            Color(0xFF2E7D32)
                        else
                            MaterialTheme.colorScheme.error

                )

                Spacer(
                    Modifier.height(12.dp)
                )

            }

            // ================= SUBMIT BUTTON =================

            Button(

                onClick = {

                    viewModel.submitReview(

                        bookingId = bookingId,

                        providerId = providerId,

                        rating = rating,

                        comment = comment

                    )

                },

                enabled =
                    !isLoading &&
                            existingReview == null &&
                            rating in 1..5,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor = primary

                )

            ) {

                if (isLoading) {

                    CircularProgressIndicator(

                        color = Color.White,

                        strokeWidth = 2.dp,

                        modifier = Modifier.size(26.dp)

                    )

                } else {

                    Text("Submit Review")

                }

            }

            Spacer(
                Modifier.height(30.dp)
            )

        }

    }

}