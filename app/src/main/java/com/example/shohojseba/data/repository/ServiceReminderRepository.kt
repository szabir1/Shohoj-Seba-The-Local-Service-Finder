package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Booking
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.model.Service
import com.example.shohojseba.data.model.ServiceReminder
import com.example.shohojseba.data.supabase.supabase

import io.github.jan.supabase.postgrest.from

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ServiceReminderRepository {


    // =====================================================
    // CREATE REMINDER FOR COMPLETED AC SERVICE
    // =====================================================

    suspend fun createReminderForCompletedBooking(

        booking: Booking

    ): Result<Boolean> {

        return try {


            // =================================================
            // 1. FIND SERVICE
            // =================================================

            val service =
                supabase
                    .from("Service")
                    .select {

                        filter {

                            eq(
                                "service_id",
                                booking.serviceId
                            )

                        }

                    }
                    .decodeList<Service>()
                    .firstOrNull()


            if (service == null) {

                Log.e(
                    "REMINDER_TEST",
                    "Service not found for service_id=${booking.serviceId}"
                )

                return Result.success(false)

            }


            // =================================================
            // 2. FIND CATEGORY
            // =================================================

            val category =
                supabase
                    .from("category")
                    .select {

                        filter {

                            eq(
                                "category_id",
                                service.category_id
                            )

                        }

                    }
                    .decodeList<Category>()
                    .firstOrNull()


            if (category == null) {

                Log.e(
                    "REMINDER_TEST",
                    "Category not found"
                )

                return Result.success(false)

            }


            Log.d(
                "REMINDER_TEST",
                "COMPLETED CATEGORY = ${category.category_name}"
            )


            // =================================================
            // 3. ONLY AC SERVICE
            // =================================================

            if (
                !category.category_name.equals(
                    "AC Service",
                    ignoreCase = true
                )
            ) {

                Log.d(
                    "REMINDER_TEST",
                    "NOT AC SERVICE - NO REMINDER"
                )

                return Result.success(false)

            }


            // =================================================
            // 4. CREATE COMPLETED DATE
            // =================================================

            val dateFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )


            val completedCalendar =
                Calendar.getInstance()


            val completedDate =
                dateFormat.format(
                    completedCalendar.time
                )


            // =================================================
            // 5. NEXT DATE = 6 MONTHS LATER
            // =================================================

            val nextServiceCalendar =
                Calendar.getInstance()


            nextServiceCalendar.add(
                Calendar.MONTH,
                6
            )


            val nextServiceDate =
                dateFormat.format(
                    nextServiceCalendar.time
                )


            // =================================================
            // 6. CREATE REMINDER OBJECT
            // =================================================

            val reminder =
                ServiceReminder(

                    customer_id =
                        booking.customerId,

                    provider_id =
                        booking.providerId,

                    service_id =
                        booking.serviceId,

                    booking_id =
                        booking.bookingId,

                    service_name =
                        service.service_name,

                    completed_date =
                        completedDate,

                    next_service_date =
                        nextServiceDate,

                    reminder_status =
                        "ACTIVE"

                )


            // =================================================
            // 7. INSERT
            // =================================================

            supabase
                .from("service_reminder")
                .insert(
                    reminder
                )


            Log.d(
                "REMINDER_TEST",
                "REMINDER CREATED = $reminder"
            )


            Result.success(true)


        } catch (e: Exception) {


            Log.e(
                "REMINDER_TEST",
                "CREATE REMINDER ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }


    // =====================================================
    // GET CURRENT CUSTOMER REMINDERS
    // =====================================================

    suspend fun getCustomerReminders():
            Result<List<ServiceReminder>> {

        return try {


            val customerId =
                UserSession.customerId
                    ?: throw Exception(
                        "Customer not logged in"
                    )


            val reminders =
                supabase
                    .from("service_reminder")
                    .select {

                        filter {

                            eq(
                                "customer_id",
                                customerId
                            )

                        }

                    }
                    .decodeList<ServiceReminder>()
                    .sortedBy {

                        it.next_service_date

                    }


            Log.d(
                "REMINDER_TEST",
                "CUSTOMER REMINDERS = $reminders"
            )


            Result.success(
                reminders
            )


        } catch (e: Exception) {


            Log.e(
                "REMINDER_TEST",
                "LOAD REMINDERS ERROR = ${e.message}",
                e
            )


            Result.failure(e)

        }

    }

}