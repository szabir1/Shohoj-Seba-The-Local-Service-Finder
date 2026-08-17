package com.example.shohojseba.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


import com.example.shohojseba.ui.auth.LandingScreen
import com.example.shohojseba.ui.auth.LoginScreen
import com.example.shohojseba.ui.auth.RegisterScreen

import com.example.shohojseba.ui.customer.HomeScreen
import com.example.shohojseba.ui.customer.CategoryScreen
import com.example.shohojseba.ui.customer.ServiceScreen

import com.example.shohojseba.ui.provider.ProviderDashboard
import com.example.shohojseba.ui.provider.AddServiceScreen




sealed class Screen(val route: String) {


    object Landing : Screen("landing")


    object Login : Screen("login")


    object Register : Screen("register")


    object Home : Screen("home")


    object Category : Screen("category")


    object Service : Screen("services/{categoryId}")


    object Provider : Screen("provider")


    object AddService : Screen("add_service")


}







@Composable
fun NavGraph() {


    val navController =
        rememberNavController()



    NavHost(

        navController = navController,

        startDestination = Screen.Landing.route

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



                onLoginSuccess = { role ->



                    if(role == "CUSTOMER"){



                        navController.navigate(
                            Screen.Home.route
                        ){

                            popUpTo(
                                Screen.Login.route
                            ){

                                inclusive = true

                            }

                        }



                    }



                    else if(role == "PROVIDER"){



                        navController.navigate(
                            Screen.Provider.route
                        ){

                            popUpTo(
                                Screen.Login.route
                            ){

                                inclusive = true

                            }

                        }



                    }



                }


            )


        }









        // REGISTER

        composable(

            Screen.Register.route

        ){


            RegisterScreen(


                onLoginClick = {


                    navController.navigate(
                        Screen.Login.route
                    )


                }


            )


        }









        // CUSTOMER HOME

        composable(

            Screen.Home.route

        ){



            HomeScreen(

                navController = navController

            )


        }









        // OLD CATEGORY SCREEN
        // keep it for now

        composable(

            Screen.Category.route

        ){



            CategoryScreen(

                navController = navController

            )


        }









        // SERVICES BY CATEGORY

        composable(

            route = Screen.Service.route,


            arguments = listOf(

                navArgument("categoryId"){


                    type = NavType.LongType


                }

            )


        ){ entry ->



            val categoryId =

                entry.arguments
                    ?.getLong("categoryId")
                    ?: 0L




            ServiceScreen(

                categoryId = categoryId

            )



        }









        // PROVIDER DASHBOARD

        composable(

            Screen.Provider.route

        ){



            ProviderDashboard(



                onAddServiceClick = {


                    navController.navigate(

                        Screen.AddService.route

                    )


                }


            )



        }









        // ADD SERVICE

        composable(

            Screen.AddService.route

        ){



            AddServiceScreen(


                onServiceAdded = {


                    navController.popBackStack()


                }


            )


        }





    }



}