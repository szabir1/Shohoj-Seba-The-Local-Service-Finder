package com.example.shohojseba.ui.auth


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel




@Composable
fun RegisterScreen(

    viewModel: AuthViewModel = viewModel(),

    onLoginClick: () -> Unit

){



    var role by remember {
        mutableStateOf("")
    }



    var name by remember {
        mutableStateOf("")
    }



    var phone by remember {
        mutableStateOf("")
    }



    var email by remember {
        mutableStateOf("")
    }



    var password by remember {
        mutableStateOf("")
    }



    var experience by remember {
        mutableStateOf("")
    }





    val message by viewModel.message






    Column(

        modifier =
            Modifier

                .fillMaxSize()

                .padding(25.dp),


        horizontalAlignment =
            Alignment.CenterHorizontally

    ){





        Text(

            "Create Account",

            fontSize = 32.sp,

            fontWeight = FontWeight.Bold

        )





        Spacer(
            Modifier.height(15.dp)
        )





        Text(

            "Who are you?",

            fontSize = 20.sp

        )






        Spacer(
            Modifier.height(15.dp)
        )





        Row(

            horizontalArrangement =
                Arrangement.spacedBy(15.dp)

        ){



            AccountCard(

                "👤",

                "Customer",

                role=="CUSTOMER"

            ){

                role="CUSTOMER"

            }





            AccountCard(

                "🛠",

                "Provider",

                role=="PROVIDER"

            ){

                role="PROVIDER"

            }



        }







        Spacer(
            Modifier.height(20.dp)
        )






        OutlinedTextField(

            value=name,

            onValueChange={
                name=it
            },

            label={
                Text("Full Name")
            },

            modifier=
                Modifier.fillMaxWidth()

        )





        OutlinedTextField(

            value=phone,

            onValueChange={
                phone=it
            },

            label={
                Text("Phone")
            },

            modifier=
                Modifier.fillMaxWidth()

        )





        OutlinedTextField(

            value=email,

            onValueChange={
                email=it
            },

            label={
                Text("Email")
            },

            modifier=
                Modifier.fillMaxWidth()

        )





        OutlinedTextField(

            value=password,

            onValueChange={
                password=it
            },

            label={
                Text("Password")
            },

            modifier=
                Modifier.fillMaxWidth()

        )





        if(role=="PROVIDER"){


            OutlinedTextField(

                value=experience,

                onValueChange={
                    experience=it
                },


                label={
                    Text("Experience years")
                },


                modifier=
                    Modifier.fillMaxWidth()

            )


        }





        Spacer(
            Modifier.height(20.dp)
        )






        Button(

            onClick = {


                viewModel.register(

                    role,

                    name,

                    phone,

                    email,

                    password,

                    experience.toIntOrNull() ?: 0

                )


            },

            modifier =
                Modifier

                    .fillMaxWidth()

                    .height(60.dp),


            shape =
                RoundedCornerShape(25.dp)

        ){


            Text(

                "Create Account →"

            )

        }





        TextButton(

            onClick = onLoginClick

        ){

            Text(
                "Already have account? Login"
            )

        }





        Text(message)






    }



}







@Composable
fun AccountCard(

    icon:String,

    title:String,

    selected:Boolean,

    onClick:()->Unit

){



    Card(

        onClick = onClick,


        modifier =
            Modifier

                .width(150.dp)

                .height(110.dp),


        colors =
            CardDefaults.cardColors(

                containerColor =
                    if(selected)

                        Color(0xFFDFF7F2)

                    else

                        Color.White

            )

    ){



        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally,


            verticalArrangement =
                Arrangement.Center,


            modifier =
                Modifier.fillMaxSize()

        ){


            Text(

                icon,

                fontSize = 35.sp

            )



            Text(

                title

            )

        }


    }


}