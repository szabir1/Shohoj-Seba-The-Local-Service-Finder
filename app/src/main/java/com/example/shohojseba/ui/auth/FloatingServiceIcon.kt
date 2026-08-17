package com.example.shohojseba.ui.auth


import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.dp



@Composable
fun FloatingServiceIcon(

    icon:String,

    modifier: Modifier = Modifier

){



    val transition =
        rememberInfiniteTransition()



    val y by transition.animateFloat(

        initialValue = 0f,

        targetValue = -10f,

        animationSpec = infiniteRepeatable(

            tween(1500),

            RepeatMode.Reverse

        )

    )



    Surface(

        modifier = modifier.offset(

            y = y.dp

        ),

        shape = MaterialTheme.shapes.large,

        tonalElevation = 5.dp

    ){


        Text(

            text = icon,

            modifier = Modifier.padding(14.dp)

        )


    }


}