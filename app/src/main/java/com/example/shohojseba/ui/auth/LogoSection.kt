package com.example.shohojseba.ui.auth


import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun LogoSection(){


    val infiniteTransition =
        rememberInfiniteTransition()



    val scale by infiniteTransition.animateFloat(

        initialValue = 0.95f,

        targetValue = 1.05f,

        animationSpec = infiniteRepeatable(

            animation = tween(

                1800,

                easing = EaseInOut

            ),

            repeatMode = RepeatMode.Reverse

        )

    )



    Column(

        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.scale(scale)

    ){



        Surface(

            modifier = Modifier.size(150.dp),

            shape = MaterialTheme.shapes.extraLarge,

            color = MaterialTheme.colorScheme.primary

        ){


            Box(

                contentAlignment = Alignment.Center

            ){


                Text(

                    text = "🏠",

                    fontSize = 65.sp

                )


            }


        }



        Spacer(

            modifier = Modifier.height(20.dp)

        )




        Text(

            text = "ShohojSeba",

            style = MaterialTheme.typography.displaySmall

        )



        Text(

            text = "All Services. One App.",

            style = MaterialTheme.typography.titleMedium

        )



    }


}