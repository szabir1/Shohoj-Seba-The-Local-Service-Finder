package com.example.shohojseba.ui.customer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.ui.customer.components.ServiceCard
import com.example.shohojseba.viewmodel.ServiceViewModel



@Composable
fun ServiceScreen(


    categoryId: Long,


    viewModel: ServiceViewModel = viewModel()


){



    val services by viewModel.services.collectAsState()



    LaunchedEffect(categoryId){


        viewModel.loadServicesByCategory(

            categoryId

        )


    }





    Column(


        modifier = Modifier

            .fillMaxSize()

            .background(


                Brush.verticalGradient(

                    listOf(

                        Color(0xFFEFFFFB),

                        Color.White

                    )

                )

            )

            .verticalScroll(

                rememberScrollState()

            )

            .padding(20.dp)



    ){





        Text(


            text = "Services",


            style = MaterialTheme.typography.headlineMedium



        )





        Spacer(

            modifier = Modifier.height(20.dp)

        )







        if(services.isEmpty()){



            Card(


                modifier = Modifier.fillMaxWidth(),


                shape = RoundedCornerShape(25.dp)



            ){



                Text(


                    text = "No services found for this category",


                    modifier = Modifier.padding(20.dp)


                )



            }





        }

        else {




            services.forEach { service ->





                ServiceCard(



                    title = service.service_name,



                    description =

                        service.description

                            ?: "Professional service for your home",




                    price =

                        service.price.toString(),





                    duration =

                        service.duration,





                    provider =

                        service.provider_name,





                    phone =

                        service.provider_phone,





                    experience =

                        service.experience.toString()



                )



            }



        }





    }



}