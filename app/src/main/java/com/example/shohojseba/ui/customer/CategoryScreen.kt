package com.example.shohojseba.ui.customer


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shohojseba.viewmodel.CategoryViewModel



@Composable
fun CategoryScreen(

    navController: NavController,

    viewModel: CategoryViewModel = viewModel()

) {


    val categories by viewModel.categories



    println(
        "CATEGORY SCREEN SIZE = ${categories.size}"
    )



    LaunchedEffect(Unit) {


        println(
            "CATEGORY SCREEN LOADING STARTED"
        )


        viewModel.loadCategories()


    }





    Column(

        modifier = Modifier

            .fillMaxSize()

            .padding(24.dp)

    ) {



        Text(

            text = "Categories",

            style = MaterialTheme.typography.headlineMedium

        )



        Spacer(

            modifier = Modifier.height(20.dp)

        )



        Text(

            text = "Total Categories: ${categories.size}"

        )



        Spacer(

            modifier = Modifier.height(20.dp)

        )





        if (categories.isEmpty()) {



            Text(

                text = "No categories found..."

            )



        } else {



            categories.forEach { category ->




                Button(

                    onClick = {


                        navController.navigate(

                            "services/${category.category_id}"

                        )


                    },

                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(vertical = 6.dp)

                ) {



                    Text(

                        text = category.category_name

                    )



                }



            }



        }



    }


}