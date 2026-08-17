package com.example.shohojseba.ui.auth


import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel



@Composable
fun LoginScreen(

    viewModel: AuthViewModel = viewModel(),

    onRegisterClick: () -> Unit,

    onLoginSuccess: (String) -> Unit

){



    var email by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }



    val message by viewModel.message




    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(

                Brush.verticalGradient(

                    listOf(

                        Color(0xFFE8FFFA),

                        Color.White

                    )

                )

            )

    ){



        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(30.dp),


            horizontalAlignment = Alignment.CenterHorizontally,


            verticalArrangement = Arrangement.Center


        ){



            Text(

                text = "👋",

                fontSize = 55.sp

            )



            Spacer(

                Modifier.height(15.dp)

            )




            Text(

                text = "Welcome Back!",

                fontSize = 32.sp,

                fontWeight = FontWeight.Bold

            )





            Text(

                text = "Login to continue your\nShohojSeba journey",

                color = Color.Gray

            )





            Spacer(

                Modifier.height(35.dp)

            )





            OutlinedTextField(

                value = email,

                onValueChange = {
                    email = it
                },

                label = {
                    Text("Email Address")
                },


                modifier =
                    Modifier.fillMaxWidth(),


                shape =
                    RoundedCornerShape(18.dp)

            )





            Spacer(

                Modifier.height(15.dp)

            )





            OutlinedTextField(

                value = password,

                onValueChange = {
                    password = it
                },


                label = {
                    Text("Password")
                },


                modifier =
                    Modifier.fillMaxWidth(),


                shape =
                    RoundedCornerShape(18.dp)

            )






            Spacer(

                Modifier.height(30.dp)

            )





            Button(

                onClick = {


                    viewModel.login(

                        email,

                        password

                    ){


                            role ->

                        onLoginSuccess(role)


                    }


                },


                modifier =
                    Modifier

                        .fillMaxWidth()

                        .height(60.dp),


                shape =
                    RoundedCornerShape(25.dp)


            ){



                Text(

                    "Login →",

                    fontSize = 17.sp

                )


            }





            Spacer(

                Modifier.height(20.dp)

            )





            TextButton(

                onClick = {

                    onRegisterClick()

                }

            ){

                Text(

                    "New to ShohojSeba? Create Account"

                )


            }






            Text(

                message,

                color = Color.Gray

            )





        }


    }


}