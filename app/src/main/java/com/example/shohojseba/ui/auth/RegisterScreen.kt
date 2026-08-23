package com.example.shohojseba.ui.auth


import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel



@Composable
fun RegisterScreen(

    viewModel: AuthViewModel = viewModel(),

    onLoginClick: () -> Unit

) {


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

        modifier = Modifier

            .fillMaxSize()

            .verticalScroll(
                rememberScrollState()
            )

            .imePadding()

            .padding(25.dp),


        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Text(

            text = "Create Account",

            fontSize = 32.sp,

            fontWeight = FontWeight.Bold

        )



        Spacer(
            Modifier.height(12.dp)
        )



        Text(

            text = "Who are you?",

            fontSize = 20.sp

        )



        Spacer(
            Modifier.height(15.dp)
        )




        Row(

            horizontalArrangement = Arrangement.spacedBy(15.dp)

        ) {


            AccountCard(

                icon = "👤",

                title = "Customer",

                selected = role == "CUSTOMER"

            ){

                role = "CUSTOMER"

            }





            AccountCard(

                icon = "🛠",

                title = "Provider",

                selected = role == "PROVIDER"

            ){

                role = "PROVIDER"

            }


        }



        Spacer(
            Modifier.height(25.dp)
        )



        RegisterField(
            value = name,
            label = "Full Name"
        ){
            name = it
        }



        RegisterField(
            value = phone,
            label = "Phone"
        ){
            phone = it
        }



        RegisterField(
            value = email,
            label = "Email"
        ){
            email = it
        }



        RegisterField(
            value = password,
            label = "Password"
        ){
            password = it
        }





        if(role == "PROVIDER"){


            RegisterField(

                value = experience,

                label = "Experience years"

            ){

                experience = it

            }


        }





        Spacer(
            Modifier.height(25.dp)
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


            modifier = Modifier

                .fillMaxWidth()

                .height(58.dp),


            shape = RoundedCornerShape(30.dp)

        ){


            Text(

                "Create Account →",

                fontSize = 16.sp

            )

        }




        Spacer(
            Modifier.height(12.dp)
        )





        if(message.isNotEmpty()){


            Text(

                text = message,


                color = if(
                    message.contains("success",
                        ignoreCase = true
                    )
                )

                    Color(0xFF008577)

                else

                    Color.Red,


                fontWeight = FontWeight.Bold

            )


        }





        TextButton(

            onClick = onLoginClick

        ){

            Text(

                "Already have account? Login",

                color = Color(0xFF008577)

            )

        }



    }

}





@Composable
fun RegisterField(

    value:String,

    label:String,

    onChange:(String)->Unit

){


    OutlinedTextField(

        value = value,

        onValueChange = onChange,


        label = {

            Text(label)

        },


        modifier = Modifier

            .fillMaxWidth()

            .padding(vertical = 6.dp),


        shape = RoundedCornerShape(16.dp)

    )


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


        modifier = Modifier

            .width(150.dp)

            .height(110.dp),



        colors = CardDefaults.cardColors(


            containerColor =

                if(selected)

                    Color(0xFFDFF7F2)

                else

                    Color.White

        )

    ){



        Column(

            horizontalAlignment = Alignment.CenterHorizontally,


            verticalArrangement = Arrangement.Center,


            modifier = Modifier.fillMaxSize()

        ){



            Text(

                text = icon,

                fontSize = 35.sp

            )



            Text(title)



        }



    }



}