package com.example.shohojseba.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Review
import com.example.shohojseba.data.model.ReviewRequest
import com.example.shohojseba.data.repository.ReviewRepository
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

    private val repository = ReviewRepository()


    // =====================================================
    // Loading
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading


    // =====================================================
    // Review submit success
    // =====================================================

    private val _reviewSuccess =
        mutableStateOf(false)

    val reviewSuccess: State<Boolean> =
        _reviewSuccess


    // =====================================================
    // Message
    // =====================================================

    private val _message =
        mutableStateOf("")

    val message: State<String> =
        _message


    // =====================================================
    // Existing review
    // =====================================================

    private val _existingReview =
        mutableStateOf<Review?>(null)

    val existingReview: State<Review?> =
        _existingReview


    // =====================================================
    // Provider review list
    // =====================================================

    private val _providerReviews =
        mutableStateOf<List<Review>>(emptyList())

    val providerReviews: State<List<Review>> =
        _providerReviews


    // =====================================================
    // Provider average ratings
    // =====================================================

    private val _providerRatings =
        mutableStateMapOf<Long, Double>()

    val providerRatings: Map<Long, Double>
        get() = _providerRatings


    // =====================================================
    // Provider review counts
    // =====================================================

    private val _providerReviewCounts =
        mutableStateMapOf<Long, Int>()

    val providerReviewCounts: Map<Long, Int>
        get() = _providerReviewCounts


    // =====================================================
    // Prevent duplicate rating requests
    // =====================================================

    private val loadedProviderIds =
        mutableSetOf<Long>()


    // =====================================================
    // Submit review
    // =====================================================

    fun submitReview(
        bookingId: Long,
        providerId: Long,
        rating: Int,
        comment: String
    ) {

        val customerId =
            UserSession.customerId

        if (customerId == null) {

            _message.value =
                "Customer session not found"

            return
        }

        if (rating !in 1..5) {

            _message.value =
                "Please select a rating"

            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            val existing =
                repository.getReviewByBooking(
                    bookingId
                )

            if (existing != null) {

                _existingReview.value =
                    existing

                _message.value =
                    "You have already reviewed this booking"

                _reviewSuccess.value =
                    false

                _isLoading.value =
                    false

                return@launch
            }

            val request =
                ReviewRequest(

                    rating = rating,

                    comment =
                        comment
                            .trim()
                            .ifEmpty {
                                null
                            },

                    customer_id =
                        customerId,

                    provider_id =
                        providerId,

                    booking_id =
                        bookingId
                )

            val result =
                repository.createReview(
                    request
                )

            if (result != null) {

                _reviewSuccess.value =
                    true

                _existingReview.value =
                    result

                _message.value =
                    "Review submitted successfully"

                // Refresh this provider's rating
                loadedProviderIds.remove(
                    providerId
                )

                loadProviderRating(
                    providerId
                )

            } else {

                _reviewSuccess.value =
                    false

                _message.value =
                    "Failed to submit review"
            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // Check existing review
    // =====================================================

    fun checkExistingReview(
        bookingId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _existingReview.value =
                repository.getReviewByBooking(
                    bookingId
                )

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // Load complete provider reviews
    // =====================================================

    fun loadProviderReviews(
        providerId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _providerReviews.value =
                repository.getReviewsByProvider(
                    providerId
                )

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // Load provider average rating
    // =====================================================

    fun loadProviderRating(
        providerId: Long
    ) {

        if (
            loadedProviderIds.contains(
                providerId
            )
        ) {
            return
        }

        loadedProviderIds.add(
            providerId
        )

        viewModelScope.launch {

            val reviews =
                repository.getReviewsByProvider(
                    providerId
                )

            val reviewCount =
                reviews.size

            val averageRating =
                if (reviews.isNotEmpty()) {

                    reviews
                        .map {
                            it.rating
                        }
                        .average()

                } else {

                    0.0
                }

            _providerRatings[
                providerId
            ] = averageRating

            _providerReviewCounts[
                providerId
            ] = reviewCount
        }
    }


    // =====================================================
    // Reset states
    // =====================================================

    fun resetReviewState() {

        _reviewSuccess.value =
            false

        _message.value =
            ""

    }


    fun clearExistingReview() {

        _existingReview.value =
            null

    }
}