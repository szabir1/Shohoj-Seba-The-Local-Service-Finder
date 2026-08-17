package com.example.shohojseba.ui.provider


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.viewmodel.ProviderViewModel



@Composable
fun AddServiceScreen(

    onServiceAdded: () -> Unit,

    viewModel: ProviderViewModel = viewModel()

) {



    var serviceName by remember {

        mutableStateOf("")

    }



    var description by remember {

        mutableStateOf("")

    }



    var price by remember {

        mutableStateOf("")

    }



    var duration by remember {

        mutableStateOf("")

    }



    var categoryId by remember {

        mutableStateOf("")

    }





    val provider by viewModel.provider


    val message by viewModel.message





    LaunchedEffect(Unit) {


        viewModel.loadProviderProfile()


    }





    Column(

        modifier = Modifier

            .fillMaxSize()

            .padding(24.dp)

    ) {



        Text(

            text = "Create Service",

            style = MaterialTheme.typography.headlineMedium

        )




        Spacer(

            modifier = Modifier.height(8.dp)

        )




        Text(

            text = "Add your service and reach more customers",

            style = MaterialTheme.typography.bodyLarge

        )





        Spacer(

            modifier = Modifier.height(25.dp)

        )






        OutlinedTextField(

            value = serviceName,

            onValueChange = {

                serviceName = it

            },

            label = {

                Text("Service Name")

            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)

        )





        Spacer(

            modifier = Modifier.height(12.dp)

        )





        OutlinedTextField(

            value = description,

            onValueChange = {

                description = it

            },

            label = {

                Text("Description")

            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)

        )





        Spacer(

            modifier = Modifier.height(12.dp)

        )






        OutlinedTextField(

            value = price,

            onValueChange = {

                price = it

            },

            label = {

                Text("Price")

            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)

        )





        Spacer(

            modifier = Modifier.height(12.dp)

        )





        OutlinedTextField(

            value = duration,

            onValueChange = {

                duration = it

            },

            label = {

                Text("Duration")

            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)

        )





        Spacer(

            modifier = Modifier.height(12.dp)

        )






        OutlinedTextField(

            value = categoryId,

            onValueChange = {

                categoryId = it

            },

            label = {

                Text("Category ID")

            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)

        )







        Spacer(

            modifier = Modifier.height(25.dp)

        )







        Button(

            onClick = {



                val providerId = provider?.provider_id




                if(providerId != null){



                    val service = AddServiceRequest(



                        service_name = serviceName,



                        description = description,



                        price = price.toDoubleOrNull()

                            ?: 0.0,



                        duration = duration,



                        provider_id = providerId,



                        category_id = categoryId.toLongOrNull()

                            ?: 0L



                    )





                    viewModel.addService(service)



                    onServiceAdded()



                }



            },



            modifier = Modifier

                .fillMaxWidth()

                .height(55.dp),



            shape = RoundedCornerShape(30.dp)



        ) {



            Text(

                text = "＋ Add Service"

            )



        }





        Spacer(

            modifier = Modifier.height(20.dp)

        )





        if(message.isNotEmpty()){


            Text(

                text = message

            )


        }





    }



}