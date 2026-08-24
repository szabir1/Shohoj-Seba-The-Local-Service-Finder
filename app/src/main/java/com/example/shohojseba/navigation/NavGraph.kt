package com.example.shohojseba.navigation

import android.net.Uri

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


// =====================================================
// AUTH
// =====================================================

import com.example.shohojseba.ui.auth.LandingScreen
import com.example.shohojseba.ui.auth.LoginScreen
import com.example.shohojseba.ui.auth.RegisterScreen


// =====================================================
// ADMIN
// =====================================================

import com.example.shohojseba.ui.admin.AdminAreaScreen
import com.example.shohojseba.ui.admin.AdminCategoryScreen
import com.example.shohojseba.ui.admin.AdminCustomersScreen
import com.example.shohojseba.ui.admin.AdminDashboard
import com.example.shohojseba.ui.admin.AdminProvidersScreen
import com.example.shohojseba.ui.admin.AdminReviewsScreen
import com.example.shohojseba.ui.admin.AdminServicesScreen


// =====================================================
// CUSTOMER
// =====================================================

import com.example.shohojseba.ui.customer.BookingScreen
import com.example.shohojseba.ui.customer.CategoryScreen
import com.example.shohojseba.ui.customer.CustomerBookingsScreen
import com.example.shohojseba.ui.customer.FavoritesScreen
import com.example.shohojseba.ui.customer.HomeScreen
import com.example.shohojseba.ui.customer.NotificationsScreen
import com.example.shohojseba.ui.customer.ProviderReviewsScreen
import com.example.shohojseba.ui.customer.ReviewScreen
import com.example.shohojseba.ui.customer.ServiceRemindersScreen
import com.example.shohojseba.ui.customer.ServiceScreen

import com.example.shohojseba.ui.customer.components.CustomerBottomNavBar


// =====================================================
// PROVIDER
// =====================================================

import com.example.shohojseba.ui.provider.AddServiceScreen
import com.example.shohojseba.ui.provider.ProviderBookingsScreen
import com.example.shohojseba.ui.provider.ProviderDashboard
import com.example.shohojseba.ui.provider.ProviderNotificationsScreen

import com.example.shohojseba.ui.provider.components.ProviderBottomNavBar


// =====================================================
// VIEWMODELS
// =====================================================

import com.example.shohojseba.viewmodel.NotificationViewModel
import com.example.shohojseba.viewmodel.ProviderNotificationViewModel


sealed class Screen(

    val route: String

) {

    object Landing :
        Screen("landing")


    object Login :
        Screen("login")


    object Register :
        Screen("register")


    // =====================================================
    // CUSTOMER
    // =====================================================

    object Home :
        Screen("home")


    object Category :
        Screen("category")


    object Service :
        Screen(
            "services/{categoryId}" +
                    "?areaId={areaId}" +
                    "&areaName={areaName}" +
                    "&promo={promo}"
        )


    object Booking :
        Screen(
            "booking/{providerId}/{serviceId}/{serviceName}/{providerName}" +
                    "?originalPrice={originalPrice}" +
                    "&discountPercent={discountPercent}" +
                    "&finalPrice={finalPrice}"
        )


    object CustomerBookings :
        Screen("customer_bookings")


    object Review :
        Screen(
            "review/{bookingId}/{providerId}/{serviceName}/{providerName}"
        )


    object ProviderReviews :
        Screen(
            "provider_reviews/{providerId}/{providerName}"
        )


    object ServiceReminders :
        Screen("service_reminders")


    object Favorites :
        Screen("favorites")


    object Notifications :
        Screen("notifications")


    // =====================================================
    // PROVIDER
    // =====================================================

    object Provider :
        Screen("provider")


    object ProviderBookings :
        Screen("provider_bookings")


    object ProviderNotifications :
        Screen("provider_notifications")


    object AddService :
        Screen("add_service")


    // =====================================================
    // ADMIN
    // =====================================================

    object Admin :
        Screen("admin")


    object AdminCategories :
        Screen("admin_categories")


    object AdminAreas :
        Screen("admin_areas")


    object AdminCustomers :
        Screen("admin_customers")


    object AdminProviders :
        Screen("admin_providers")


    object AdminServices :
        Screen("admin_services")


    object AdminReviews :
        Screen("admin_reviews")

}


@Composable
fun NavGraph() {


    val navController =
        rememberNavController()


    val navBackStackEntry by
    navController
        .currentBackStackEntryAsState()


    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route


    // =====================================================
    // CUSTOMER NOTIFICATIONS
    // =====================================================

    val customerNotificationViewModel:
            NotificationViewModel =
        viewModel()


    val customerNotifications by
    customerNotificationViewModel.notifications


    val customerUnreadCount =
        customerNotifications.count {

            !it.is_read

        }


    // =====================================================
    // PROVIDER NOTIFICATIONS
    // =====================================================

    val providerNotificationViewModel:
            ProviderNotificationViewModel =
        viewModel()


    val providerNotifications by
    providerNotificationViewModel.notifications


    val providerUnreadCount =
        providerNotifications.count {

            !it.is_read

        }


    // =====================================================
    // LOAD BADGES
    // =====================================================

    LaunchedEffect(
        currentRoute
    ) {


        if (
            currentRoute == Screen.Home.route ||
            currentRoute == Screen.CustomerBookings.route ||
            currentRoute == Screen.Favorites.route ||
            currentRoute == Screen.Notifications.route
        ) {

            customerNotificationViewModel
                .loadNotifications()

        }


        if (
            currentRoute == Screen.Provider.route ||
            currentRoute == Screen.ProviderBookings.route ||
            currentRoute == Screen.ProviderNotifications.route ||
            currentRoute == Screen.AddService.route
        ) {

            providerNotificationViewModel
                .loadNotifications()

        }

    }


    // =====================================================
    // BOTTOM BAR VISIBILITY
    // =====================================================

    val showCustomerBottomBar =

        currentRoute ==
                Screen.CustomerBookings.route ||

                currentRoute ==
                Screen.Favorites.route ||

                currentRoute ==
                Screen.Notifications.route


    val showProviderBottomBar =

        currentRoute ==
                Screen.Provider.route ||

                currentRoute ==
                Screen.ProviderBookings.route ||

                currentRoute ==
                Screen.ProviderNotifications.route ||

                currentRoute ==
                Screen.AddService.route


    Scaffold(

        bottomBar = {


            when {


                // =================================================
                // CUSTOMER
                // =================================================

                showCustomerBottomBar -> {


                    CustomerBottomNavBar(

                        currentRoute =
                            currentRoute,

                        unreadNotificationCount =
                            customerUnreadCount,

                        onHomeClick = {

                            navController.navigate(
                                Screen.Home.route
                            ) {

                                popUpTo(
                                    Screen.Home.route
                                ) {

                                    inclusive =
                                        false

                                }

                                launchSingleTop =
                                    true

                            }

                        },

                        onBookingsClick = {

                            navController.navigate(
                                Screen.CustomerBookings.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        },

                        onSavedClick = {

                            navController.navigate(
                                Screen.Favorites.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        },

                        onAlertsClick = {

                            navController.navigate(
                                Screen.Notifications.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        }

                    )

                }


                // =================================================
                // PROVIDER
                // =================================================

                showProviderBottomBar -> {


                    ProviderBottomNavBar(

                        currentRoute =
                            currentRoute,

                        unreadNotificationCount =
                            providerUnreadCount,

                        onHomeClick = {

                            navController.navigate(
                                Screen.Provider.route
                            ) {

                                popUpTo(
                                    Screen.Provider.route
                                ) {

                                    inclusive =
                                        false

                                }

                                launchSingleTop =
                                    true

                            }

                        },

                        onBookingsClick = {

                            navController.navigate(
                                Screen.ProviderBookings.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        },

                        onAddServiceClick = {

                            navController.navigate(
                                Screen.AddService.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        },

                        onAlertsClick = {

                            navController.navigate(
                                Screen.ProviderNotifications.route
                            ) {

                                launchSingleTop =
                                    true

                            }

                        }

                    )

                }

            }

        }

    ) { outerPadding ->


        NavHost(

            navController =
                navController,

            startDestination =
                Screen.Landing.route,

            modifier =
                Modifier.padding(
                    outerPadding
                )

        ) {


            // =================================================
            // LANDING
            // =================================================

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


            // =================================================
            // LOGIN
            // =================================================

            composable(
                Screen.Login.route
            ) {

                LoginScreen(

                    onRegisterClick = {

                        navController.navigate(
                            Screen.Register.route
                        )

                    },

                    onLoginSuccess = { role ->


                        when (
                            role
                        ) {


                            "CUSTOMER" -> {

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

                            }


                            "PROVIDER" -> {

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


                            "ADMIN" -> {

                                navController.navigate(
                                    Screen.Admin.route
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

                    }

                )

            }


            // =================================================
            // REGISTER
            // =================================================

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


            // =================================================
            // ADMIN DASHBOARD
            // =================================================

            composable(
                Screen.Admin.route
            ) {

                AdminDashboard(

                    onCategoriesClick = {

                        navController.navigate(
                            Screen.AdminCategories.route
                        )

                    },

                    onAreasClick = {

                        navController.navigate(
                            Screen.AdminAreas.route
                        )

                    },

                    onCustomersClick = {

                        navController.navigate(
                            Screen.AdminCustomers.route
                        )

                    },

                    onProvidersClick = {

                        navController.navigate(
                            Screen.AdminProviders.route
                        )

                    },

                    onServicesClick = {

                        navController.navigate(
                            Screen.AdminServices.route
                        )

                    },

                    onReviewsClick = {

                        navController.navigate(
                            Screen.AdminReviews.route
                        )

                    },

                    onLogoutClick = {

                        navController.navigate(
                            Screen.Login.route
                        ) {

                            popUpTo(0) {

                                inclusive =
                                    true

                            }

                            launchSingleTop =
                                true

                        }

                    }

                )

            }


            // =================================================
            // ADMIN CATEGORIES
            // =================================================

            composable(
                Screen.AdminCategories.route
            ) {

                AdminCategoryScreen()

            }


            // =================================================
            // ADMIN AREAS
            // =================================================

            composable(
                Screen.AdminAreas.route
            ) {

                AdminAreaScreen()

            }


            // =================================================
            // ADMIN CUSTOMERS
            // =================================================

            composable(
                Screen.AdminCustomers.route
            ) {

                AdminCustomersScreen()

            }


            // =================================================
            // ADMIN PROVIDERS
            // =================================================

            composable(
                Screen.AdminProviders.route
            ) {

                AdminProvidersScreen()

            }


            // =================================================
            // ADMIN SERVICES
            // =================================================

            composable(
                Screen.AdminServices.route
            ) {

                AdminServicesScreen()

            }


            // =================================================
            // ADMIN REVIEWS
            // =================================================

            composable(
                Screen.AdminReviews.route
            ) {

                AdminReviewsScreen()

            }


            // =================================================
            // CUSTOMER HOME
            // =================================================

            composable(
                Screen.Home.route
            ) {

                HomeScreen(

                    navController =
                        navController

                )

            }


            // =================================================
            // CUSTOMER NOTIFICATIONS
            // =================================================

            composable(
                Screen.Notifications.route
            ) {

                NotificationsScreen(

                    onBookingsClick = {

                        navController.navigate(
                            Screen.CustomerBookings.route
                        ) {

                            launchSingleTop =
                                true

                        }

                    },

                    onRemindersClick = {

                        navController.navigate(
                            Screen.ServiceReminders.route
                        )

                    }

                )

            }


            // =================================================
            // FAVORITES
            // =================================================

            composable(
                Screen.Favorites.route
            ) {

                FavoritesScreen(

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
                                    "${Uri.encode(providerName)}" +
                                    "?originalPrice=0.0" +
                                    "&discountPercent=0.0" +
                                    "&finalPrice=0.0"

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


            // =================================================
            // SERVICE REMINDERS
            // =================================================

            composable(
                Screen.ServiceReminders.route
            ) {

                ServiceRemindersScreen()

            }


            // =================================================
            // CUSTOMER BOOKINGS
            // =================================================

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
                                    Uri.encode(
                                        providerName
                                    )

                        )

                    }

                )

            }


            // =================================================
            // REVIEW
            // =================================================

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


            // =================================================
            // PROVIDER REVIEWS
            // =================================================

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


            // =================================================
            // CATEGORY
            // =================================================

            composable(
                Screen.Category.route
            ) {

                CategoryScreen(

                    navController =
                        navController

                )

            }


            // =================================================
            // SERVICES
            // =================================================

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

                        },

                        navArgument(
                            "promo"
                        ) {

                            type =
                                NavType.BoolType

                            defaultValue =
                                false

                        }

                    )

            ) { entry ->


                ServiceScreen(

                    categoryId =
                        entry.arguments
                            ?.getLong(
                                "categoryId"
                            )
                            ?: 0L,

                    areaId =
                        entry.arguments
                            ?.getLong(
                                "areaId"
                            )
                            ?: 0L,

                    areaName =
                        entry.arguments
                            ?.getString(
                                "areaName"
                            )
                            ?: "",

                    isPromotion =
                        entry.arguments
                            ?.getBoolean(
                                "promo"
                            )
                            ?: false,

                    onBookServiceClick = {
                            providerId,
                            serviceId,
                            serviceName,
                            providerName,
                            originalPrice,
                            discountPercent,
                            finalPrice ->


                        navController.navigate(

                            "booking/" +
                                    "$providerId/" +
                                    "$serviceId/" +
                                    "${Uri.encode(serviceName)}/" +
                                    "${Uri.encode(providerName)}" +
                                    "?originalPrice=$originalPrice" +
                                    "&discountPercent=$discountPercent" +
                                    "&finalPrice=$finalPrice"

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


            // =================================================
            // BOOKING FORM
            // =================================================

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

                        },

                        navArgument(
                            "originalPrice"
                        ) {

                            type =
                                NavType.FloatType

                            defaultValue =
                                0.0f

                        },

                        navArgument(
                            "discountPercent"
                        ) {

                            type =
                                NavType.FloatType

                            defaultValue =
                                0.0f

                        },

                        navArgument(
                            "finalPrice"
                        ) {

                            type =
                                NavType.FloatType

                            defaultValue =
                                0.0f

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
                            ?: "",

                    originalPrice =
                        entry.arguments
                            ?.getFloat(
                                "originalPrice"
                            )
                            ?.toDouble()
                            ?: 0.0,

                    discountPercent =
                        entry.arguments
                            ?.getFloat(
                                "discountPercent"
                            )
                            ?.toDouble()
                            ?: 0.0,

                    finalPrice =
                        entry.arguments
                            ?.getFloat(
                                "finalPrice"
                            )
                            ?.toDouble()
                            ?: 0.0

                )

            }


            // =================================================
            // PROVIDER DASHBOARD
            // =================================================

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

                    },

                    onNotificationsClick = {

                        navController.navigate(
                            Screen.ProviderNotifications.route
                        )

                    },

                    onLogoutClick = {

                        navController.navigate(
                            Screen.Login.route
                        ) {

                            popUpTo(0) {

                                inclusive =
                                    true

                            }

                            launchSingleTop =
                                true

                        }

                    }

                )

            }


            // =================================================
            // PROVIDER BOOKINGS
            // =================================================

            composable(
                Screen.ProviderBookings.route
            ) {

                ProviderBookingsScreen()

            }


            // =================================================
            // PROVIDER NOTIFICATIONS
            // =================================================

            composable(
                Screen.ProviderNotifications.route
            ) {

                ProviderNotificationsScreen(

                    onServiceRequestsClick = {

                        navController.navigate(
                            Screen.ProviderBookings.route
                        ) {

                            launchSingleTop =
                                true

                        }

                    }

                )

            }


            // =================================================
            // ADD SERVICE
            // =================================================

            composable(
                Screen.AddService.route
            ) {

                AddServiceScreen(

                    onServiceAdded = {

                        navController.navigate(
                            Screen.Provider.route
                        ) {

                            popUpTo(
                                Screen.Provider.route
                            ) {

                                inclusive =
                                    false

                            }

                            launchSingleTop =
                                true

                        }

                    }

                )

            }

        }

    }

}