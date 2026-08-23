package com.example.shohojseba.data.repository

import android.util.Log
import com.example.shohojseba.data.model.Review
import com.example.shohojseba.data.model.ReviewRequest
import com.example.shohojseba.data.supabase.supabase
import io.github.jan.supabase.postgrest.from

class ReviewRepository {

    // =====================================================
    // CREATE REVIEW
    // =====================================================

    suspend fun createReview(
        review: ReviewRequest
    ): Review? {

        return try {

            Log.d(
                "REVIEW_TEST",
                "Creating review: $review"
            )

            // Uses the logged-in Supabase Auth session.
            supabase
                .from("review")
                .insert(review)

            // Read back the newly created review.
            val createdReview =
                supabase
                    .from("review")
                    .select {

                        filter {

                            eq(
                                "booking_id",
                                review.booking_id
                            )

                        }

                    }
                    .decodeList<Review>()
                    .firstOrNull()

            Log.d(
                "REVIEW_TEST",
                "Review created: $createdReview"
            )

            createdReview

        } catch (e: Exception) {

            Log.e(
                "REVIEW_TEST",
                "Create review error = ${e.message}"
            )

            null
        }
    }


    // =====================================================
    // GET REVIEWS FOR PROVIDER
    // =====================================================

    suspend fun getReviewsByProvider(
        providerId: Long
    ): List<Review> {

        return try {

            val reviews =
                supabase
                    .from("review")
                    .select {

                        filter {

                            eq(
                                "provider_id",
                                providerId
                            )

                        }

                    }
                    .decodeList<Review>()

            Log.d(
                "REVIEW_TEST",
                "Provider $providerId reviews = $reviews"
            )

            reviews

        } catch (e: Exception) {

            Log.e(
                "REVIEW_TEST",
                "Provider reviews error = ${e.message}"
            )

            emptyList()
        }
    }


    // =====================================================
    // CHECK REVIEW FOR BOOKING
    // =====================================================

    suspend fun getReviewByBooking(
        bookingId: Long
    ): Review? {

        return try {

            supabase
                .from("review")
                .select {

                    filter {

                        eq(
                            "booking_id",
                            bookingId
                        )

                    }

                }
                .decodeList<Review>()
                .firstOrNull()

        } catch (e: Exception) {

            Log.e(
                "REVIEW_TEST",
                "Booking review error = ${e.message}"
            )

            null
        }
    }
}