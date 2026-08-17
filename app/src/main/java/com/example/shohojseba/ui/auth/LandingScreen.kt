package com.example.shohojseba.ui.auth


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun LandingScreen(

    onGetStartedClick: () -> Unit,

    onLoginClick: () -> Unit

) {


    val infinite =
        rememberInfiniteTransition()



    val floatAnimation by infinite.animateFloat(

        initialValue = 0f,

        targetValue = -12f,

        animationSpec = infiniteRepeatable(

            tween(1800),

            RepeatMode.Reverse

        )

    )



    val logoScale by infinite.animateFloat(

        initialValue = 0.95f,

        targetValue = 1.05f,

        animationSpec = infiniteRepeatable(

            tween(2000),

            RepeatMode.Reverse

        )

    )





    Box(

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

    ){





        // Floating service bubbles


        ServiceBubble(

            "🧹",

            Modifier

                .align(Alignment.TopStart)

                .padding(

                    top = 150.dp,

                    start = 35.dp

                )

                .offset(y = floatAnimation.dp)

        )



        ServiceBubble(

            "🔧",

            Modifier

                .align(Alignment.TopEnd)

                .padding(

                    top = 180.dp,

                    end = 35.dp

                )

                .offset(y = floatAnimation.dp)

        )



        ServiceBubble(

            "🚰",

            Modifier

                .align(Alignment.CenterStart)

                .padding(start = 25.dp)

                .offset(y = floatAnimation.dp)

        )



        ServiceBubble(

            "⚡",

            Modifier

                .align(Alignment.CenterEnd)

                .padding(end = 25.dp)

                .offset(y = floatAnimation.dp)

        )








        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(horizontal = 30.dp),


            horizontalAlignment = Alignment.CenterHorizontally,


            verticalArrangement = Arrangement.Center

        ){






            // Logo placeholder (replace later with real logo)

            Surface(

                modifier = Modifier

                    .size(150.dp)

                    .scale(logoScale),


                shape = RoundedCornerShape(40.dp),


                color = Color(0xFF00796B)

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

                fontSize = 36.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF00695C)

            )





            Text(

                text = "All Services. One App.",

                fontSize = 16.sp,

                fontWeight = FontWeight.Medium

            )








            Spacer(

                modifier = Modifier.height(35.dp)

            )







            Text(

                text = "Your Home,\nOur Priority",

                fontSize = 32.sp,

                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Center

            )







            Spacer(

                modifier = Modifier.height(12.dp)

            )






            Text(

                text =

                    "Book trusted professionals\nfor every household need.",


                fontSize = 16.sp,

                color = Color.Gray,

                textAlign = TextAlign.Center

            )








            Spacer(

                modifier = Modifier.height(25.dp)

            )







            Row(

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ){

                SmallBadge("✓ Verified")

                SmallBadge("🔒 Secure")

                SmallBadge("⭐ Quality")

            }







            Spacer(

                modifier = Modifier.height(35.dp)

            )








            Button(

                onClick = onGetStartedClick,


                modifier = Modifier

                    .fillMaxWidth()

                    .height(60.dp),


                shape = RoundedCornerShape(25.dp),


                colors = ButtonDefaults.buttonColors(

                    containerColor = Color(0xFF00796B)

                )

            ){

                Text(

                    "Get Started →",

                    fontSize = 17.sp,

                    fontWeight = FontWeight.Bold

                )


            }







            Spacer(

                modifier = Modifier.height(15.dp)

            )







            OutlinedButton(

                onClick = onLoginClick,


                modifier = Modifier

                    .fillMaxWidth()

                    .height(60.dp),


                shape = RoundedCornerShape(25.dp)

            ){

                Text(

                    "Already a user? Login",

                    fontSize = 15.sp

                )


            }



        }


    }


}







@Composable
fun ServiceBubble(

    icon:String,

    modifier: Modifier

){


    Surface(

        modifier = modifier

            .size(55.dp),

        shape = CircleShape,

        tonalElevation = 8.dp

    ){

        Box(

            contentAlignment = Alignment.Center

        ){

            Text(

                icon,

                fontSize = 24.sp

            )

        }

    }

}







@Composable
fun SmallBadge(

    text:String

){


    Surface(

        shape = RoundedCornerShape(20.dp),

        color = Color(0xFFE0F2F1)

    ){

        Text(

            text,

            modifier = Modifier.padding(

                horizontal = 10.dp,

                vertical = 6.dp

            ),

            fontSize = 11.sp

        )

    }


}