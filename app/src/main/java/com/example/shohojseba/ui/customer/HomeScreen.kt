package com.example.shohojseba.ui.customer


import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


import com.example.shohojseba.ui.customer.components.CategoryChip
import com.example.shohojseba.viewmodel.CategoryViewModel



@Composable
fun HomeScreen(

    navController: NavController,

    viewModel: CategoryViewModel = viewModel()

){



    val categories by viewModel.categories



    LaunchedEffect(Unit){

        viewModel.loadCategories()

    }



    Column(


        modifier = Modifier

            .fillMaxSize()

            .background(

                Brush.verticalGradient(

                    listOf(

                        Color(0xFFE9FFFA),

                        Color.White

                    )

                )

            )

            .verticalScroll(

                rememberScrollState()

            )

            .padding(24.dp)


    ){



        Text(

            text = "Good Morning 👋",

            style = MaterialTheme.typography.headlineMedium

        )



        Text(

            text = "Find services for your home",

            style = MaterialTheme.typography.titleMedium

        )





        Spacer(

            Modifier.height(20.dp)

        )





        OutlinedTextField(

            value = "",

            onValueChange = {},

            placeholder = {

                Text("Search services...")

            },


            modifier = Modifier.fillMaxWidth(),


            shape = RoundedCornerShape(30.dp)


        )






        Spacer(

            Modifier.height(25.dp)

        )





        Text(

            "Categories",

            style = MaterialTheme.typography.titleLarge

        )






        Spacer(

            Modifier.height(15.dp)

        )





        Row(

            modifier = Modifier.horizontalScroll(

                rememberScrollState()

            ),


            horizontalArrangement = Arrangement.spacedBy(14.dp)

        ){



            categories.forEach { category ->



                CategoryChip(


                    icon = when(category.category_name){


                        "Cleaning" -> "🧹"

                        "AC Service" -> "❄️"

                        "Plumbing" -> "🚰"

                        "Electrician" -> "⚡"

                        else -> "🔧"


                    },


                    name = category.category_name,


                    onClick = {


                        navController.navigate(

                            "services/${category.category_id}"

                        )


                    }


                )


            }



        }







        Spacer(

            Modifier.height(35.dp)

        )





        Card(

            modifier = Modifier.fillMaxWidth(),


            shape = RoundedCornerShape(30.dp),


            colors = CardDefaults.cardColors(

                containerColor = Color(0xFFDDF8F3)

            )


        ){



            Column(

                modifier = Modifier.padding(25.dp)

            ){



                Text(

                    "20% OFF",

                    color = Color(0xFF00897B)

                )



                Text(

                    "Home Cleaning",

                    style = MaterialTheme.typography.headlineSmall

                )



                Text(

                    "Professional cleaning at your doorstep"

                )





                Spacer(

                    Modifier.height(15.dp)

                )





                Button(

                    onClick = {}

                ){

                    Text("Book Now")

                }



            }


        }





        Spacer(

            Modifier.height(30.dp)

        )



        Text(

            "Categories Loaded: ${categories.size}"

        )


    }



}