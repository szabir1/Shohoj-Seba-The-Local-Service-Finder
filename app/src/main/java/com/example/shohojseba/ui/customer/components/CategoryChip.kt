package com.example.shohojseba.ui.customer.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun CategoryChip(

    icon: String,

    name: String,

    onClick: () -> Unit

){


    Surface(

        modifier = Modifier

            .width(95.dp)

            .height(95.dp)

            .clickable {

                onClick()

            },


        shape = RoundedCornerShape(25.dp),


        color = MaterialTheme.colorScheme.surfaceVariant,


        shadowElevation = 6.dp


    ){



        Column(

            modifier = Modifier.padding(10.dp),


            horizontalAlignment = Alignment.CenterHorizontally,


            verticalArrangement = Arrangement.Center


        ){


            Text(

                text = icon,

                fontSize = 30.sp

            )


            Spacer(

                modifier = Modifier.height(8.dp)

            )


            Text(

                text = name,

                fontSize = 12.sp

            )


        }


    }


}