package com.example.shohojseba.ui.provider


import androidx.compose.foundation.background
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

import com.example.shohojseba.viewmodel.ProviderViewModel



@Composable
fun ProviderDashboard(


    viewModel: ProviderViewModel = viewModel(),


    onAddServiceClick: () -> Unit


) {



    val provider by viewModel.provider

    val services by viewModel.services

    val message by viewModel.message




    LaunchedEffect(Unit) {


        viewModel.loadProviderProfile()


    }







    Column(


        modifier = Modifier

            .fillMaxSize()

            .background(

                Brush.verticalGradient(

                    colors = listOf(

                        Color(0xFFE8FFFA),

                        Color.White

                    )

                )

            )

            .verticalScroll(

                rememberScrollState()

            )

            .padding(24.dp)


    ) {



        Text(

            text = "Provider Dashboard",

            style = MaterialTheme.typography.headlineMedium

        )



        Spacer(

            Modifier.height(8.dp)

        )



        Text(

            text = "Manage your services easily",

            style = MaterialTheme.typography.bodyLarge

        )





        Spacer(

            Modifier.height(25.dp)

        )







        // PROFILE CARD


        Card(


            modifier = Modifier.fillMaxWidth(),


            shape = RoundedCornerShape(28.dp),


            elevation = CardDefaults.cardElevation(

                6.dp

            ),


            colors = CardDefaults.cardColors(

                containerColor = Color(0xFFDDF7F1)

            )


        ){



            Column(

                modifier = Modifier.padding(20.dp)

            ){



                Text(

                    text = "👤 Provider Profile",

                    style = MaterialTheme.typography.titleLarge

                )



                Spacer(

                    Modifier.height(15.dp)

                )





                if(provider != null){



                    Text(

                        text = "Name: ${provider!!.name}"

                    )



                    Text(

                        text = "Email: ${provider!!.email}"

                    )



                    Text(

                        text = "Phone: ${provider!!.phone}"

                    )



                    Text(

                        text = "⭐ Experience: ${provider!!.experience} years"

                    )



                }

                else {



                    Text(

                        text = "Loading provider..."

                    )


                }



            }


        }







        Spacer(

            Modifier.height(25.dp)

        )







        Button(


            onClick = {


                onAddServiceClick()


            },


            modifier = Modifier

                .fillMaxWidth()

                .height(55.dp),


            shape = RoundedCornerShape(30.dp)


        ){



            Text(

                text = "＋ Add New Service"

            )



        }








        Spacer(

            Modifier.height(30.dp)

        )







        Text(

            text = "My Services",

            style = MaterialTheme.typography.headlineSmall

        )






        Spacer(

            Modifier.height(15.dp)

        )







        if(services.isEmpty()){



            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(25.dp)

            ){


                Text(

                    text = "No services added yet",

                    modifier = Modifier.padding(20.dp)

                )


            }



        }

        else {



            services.forEach { service ->





                Card(


                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(vertical = 8.dp),


                    shape = RoundedCornerShape(25.dp),


                    elevation = CardDefaults.cardElevation(

                        5.dp

                    )


                ){



                    Column(

                        modifier = Modifier.padding(20.dp)

                    ){



                        Text(

                            text = "🛠 ${service.service_name}",


                            style = MaterialTheme.typography.titleLarge

                        )





                        Spacer(

                            Modifier.height(10.dp)

                        )





                        Text(

                            text = "💰 Price: ${service.price} taka"

                        )





                        Text(

                            text = "⏱ Duration: ${service.duration}"

                        )



                    }



                }




            }



        }






        Spacer(

            Modifier.height(20.dp)

        )






        if(message.isNotEmpty()){



            Text(

                text = message,

                color = MaterialTheme.colorScheme.primary

            )


        }


    }



}