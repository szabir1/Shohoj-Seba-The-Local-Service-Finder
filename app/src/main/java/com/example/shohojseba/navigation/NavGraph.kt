package com.example.shohojseba.navigation

import android.net.Uri

import androidx.compose.runtime.Composable

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.shohojseba.ui.auth.LandingScreen
import com.example.shohojseba.ui.auth.LoginScreen
import com.example.shohojseba.ui.auth.RegisterScreen

import com.example.shohojseba.ui.customer.BookingScreen
import com.example.shohojseba.ui.customer.CategoryScreen
import com.example.shohojseba.ui.customer.CustomerBookingsScreen
import com.example.shohojseba.ui.customer.HomeScreen
import com.example.shohojseba.ui.customer.ProviderReviewsScreen
import com.example.shohojseba.ui.customer.ReviewScreen
import com.example.shohojseba.ui.customer.ServiceScreen

import com.example.shohojseba.ui.provider.AddServiceScreen
import com.example.shohojseba.ui.provider.ProviderBookingsScreen
import com.example.shohojseba.ui.provider.ProviderDashboard

sealed class Screen(
    val route: String
) {

    object Landing :
        Screen("landing")

    object Login :
        Screen("login")

    object Register :
        Screen("register")

    object Home :
        Screen("home")

    object Category :
        Screen("category")

    object Service :
        Screen(
            "services/{categoryId}?areaId={areaId}&areaName={areaName}"
        )

    object Booking :
        Screen(
            "booking/{providerId}/{serviceId}/{serviceName}/{providerName}"
        )

    object CustomerBookings :
        Screen(
            "customer_bookings"
        )

    object Review :
        Screen(
            "review/{bookingId}/{providerId}/{serviceName}/{providerName}"
        )

    object ProviderReviews :
        Screen(
            "provider_reviews/{providerId}/{providerName}"
        )

    object Provider :
        Screen("provider")

    object ProviderBookings :
        Screen(
            "provider_bookings"
        )

    object AddService :
        Screen(
            "add_service"
        )
}


@Composable
fun NavGraph() {

    val navController =
        rememberNavController()


    NavHost(

        navController =
            navController,

        startDestination =
            Screen.Landing.route

    ) {


        // LANDING

        composable(
            Screen.Landing.route
        ) {

            LandingScreen(

                onGetStartedClick = {

                    navController.navigate(
                        Screen.Register.route
                    )

                },

                onLoginClick = {

                    navController.navigate(
                        Screen.Login.route
                    )

                }

            )

        }


        // LOGIN

        composable(
            Screen.Login.route
        ) {

            LoginScreen(

                onRegisterClick = {

                    navController.navigate(
                        Screen.Register.route
                    )

                },

                onLoginSuccess = {
                        role ->

                    if (
                        role ==
                        "CUSTOMER"
                    ) {

                        navController.navigate(
                            Screen.Home.route
                        ) {

                            popUpTo(
                                Screen.Login.route
                            ) {

                                inclusive =
                                    true

                            }

                        }

                    } else if (
                        role ==
                        "PROVIDER"
                    ) {

                        navController.navigate(
                            Screen.Provider.route
                        ) {

                            popUpTo(
                                Screen.Login.route
                            ) {

                                inclusive =
                                    true

                            }

                        }

                    }

                }

            )

        }


        // REGISTER

        composable(
            Screen.Register.route
        ) {

            RegisterScreen(

                onLoginClick = {

                    navController.navigate(
                        Screen.Login.route
                    )

                }

            )

        }


        // HOME

        composable(
            Screen.Home.route
        ) {

            HomeScreen(

                navController =
                    navController

            )

        }


        // CUSTOMER BOOKINGS

        composable(
            Screen.CustomerBookings.route
        ) {

            CustomerBookingsScreen(

                onReviewClick = {
                        bookingId,
                        providerId,
                        serviceName,
                        providerName ->


                    navController.navigate(

                        "review/" +
                                "$bookingId/" +
                                "$providerId/" +
                                "${Uri.encode(serviceName)}/" +
                                Uri.encode(providerName)

                    )

                }

            )

        }


        // REVIEW

        composable(

            route =
                Screen.Review.route,

            arguments =
                listOf(

                    navArgument(
                        "bookingId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "providerId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "serviceName"
                    ) {

                        type =
                            NavType.StringType

                    },

                    navArgument(
                        "providerName"
                    ) {

                        type =
                            NavType.StringType

                    }

                )

        ) { entry ->

            ReviewScreen(

                bookingId =
                    entry.arguments
                        ?.getLong(
                            "bookingId"
                        )
                        ?: 0L,

                providerId =
                    entry.arguments
                        ?.getLong(
                            "providerId"
                        )
                        ?: 0L,

                serviceName =
                    entry.arguments
                        ?.getString(
                            "serviceName"
                        )
                        ?: "Service",

                providerName =
                    entry.arguments
                        ?.getString(
                            "providerName"
                        )
                        ?: "Provider",

                onReviewSubmitted = {

                    navController
                        .popBackStack()

                }

            )

        }


        // PROVIDER REVIEWS

        composable(

            route =
                Screen.ProviderReviews.route,

            arguments =
                listOf(

                    navArgument(
                        "providerId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "providerName"
                    ) {

                        type =
                            NavType.StringType

                    }

                )

        ) { entry ->

            ProviderReviewsScreen(

                providerId =
                    entry.arguments
                        ?.getLong(
                            "providerId"
                        )
                        ?: 0L,

                providerName =
                    entry.arguments
                        ?.getString(
                            "providerName"
                        )
                        ?: "Provider"

            )

        }


        // CATEGORY

        composable(
            Screen.Category.route
        ) {

            CategoryScreen(

                navController =
                    navController

            )

        }


        // SERVICES

        composable(

            route =
                Screen.Service.route,

            arguments =
                listOf(

                    navArgument(
                        "categoryId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "areaId"
                    ) {

                        type =
                            NavType.LongType

                        defaultValue =
                            0L

                    },

                    navArgument(
                        "areaName"
                    ) {

                        type =
                            NavType.StringType

                        defaultValue =
                            ""

                    }

                )

        ) { entry ->


            val categoryId =
                entry.arguments
                    ?.getLong(
                        "categoryId"
                    )
                    ?: 0L


            val areaId =
                entry.arguments
                    ?.getLong(
                        "areaId"
                    )
                    ?: 0L


            val areaName =
                entry.arguments
                    ?.getString(
                        "areaName"
                    )
                    ?: ""


            ServiceScreen(

                categoryId =
                    categoryId,

                areaId =
                    areaId,

                areaName =
                    areaName,

                onBookServiceClick = {
                        providerId,
                        serviceId,
                        serviceName,
                        providerName ->


                    navController.navigate(

                        "booking/" +
                                "$providerId/" +
                                "$serviceId/" +
                                "${Uri.encode(serviceName)}/" +
                                Uri.encode(providerName)

                    )

                },

                onReviewsClick = {
                        providerId,
                        providerName ->


                    navController.navigate(

                        "provider_reviews/" +
                                "$providerId/" +
                                Uri.encode(
                                    providerName
                                )

                    )

                }

            )

        }


        // BOOKING

        composable(

            route =
                Screen.Booking.route,

            arguments =
                listOf(

                    navArgument(
                        "providerId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "serviceId"
                    ) {

                        type =
                            NavType.LongType

                    },

                    navArgument(
                        "serviceName"
                    ) {

                        type =
                            NavType.StringType

                    },

                    navArgument(
                        "providerName"
                    ) {

                        type =
                            NavType.StringType

                    }

                )

        ) { entry ->


            BookingScreen(

                providerId =
                    entry.arguments
                        ?.getLong(
                            "providerId"
                        )
                        ?: 0L,

                serviceId =
                    entry.arguments
                        ?.getLong(
                            "serviceId"
                        )
                        ?: 0L,

                serviceName =
                    entry.arguments
                        ?.getString(
                            "serviceName"
                        )
                        ?: "",

                providerName =
                    entry.arguments
                        ?.getString(
                            "providerName"
                        )
                        ?: ""

            )

        }


        // PROVIDER

        composable(
            Screen.Provider.route
        ) {

            ProviderDashboard(

                onAddServiceClick = {

                    navController.navigate(
                        Screen.AddService.route
                    )

                },

                onBookingRequestsClick = {

                    navController.navigate(
                        Screen.ProviderBookings.route
                    )

                }

            )

        }


        // PROVIDER BOOKINGS

        composable(
            Screen.ProviderBookings.route
        ) {

            ProviderBookingsScreen()

        }


        // ADD SERVICE

        composable(
            Screen.AddService.route
        ) {

            AddServiceScreen(

                onServiceAdded = {

                    navController
                        .popBackStack()

                }

            )

        }

    }

}