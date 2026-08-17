package com.example.shohojseba.ui.customer.components



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun ServiceCard(

    title:String,

    description:String,

    price:String,

    duration:String,

    provider:String,

    phone:String,

    experience:String

){



    Card(

        modifier = Modifier

            .fillMaxWidth()

            .padding(vertical = 10.dp),


        shape = RoundedCornerShape(28.dp),


        elevation = CardDefaults.cardElevation(

            8.dp

        )

    ){



        Column(

            modifier = Modifier.padding(20.dp)

        ){



            Text(

                "🧹",

                fontSize = 42.sp

            )




            Text(

                title,

                style = MaterialTheme.typography.titleLarge

            )




            Spacer(

                Modifier.height(6.dp)

            )




            Row(

                horizontalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier.fillMaxWidth()

            ){



                Text(

                    "⭐ 4.8",

                    color = MaterialTheme.colorScheme.primary

                )



                Text(

                    "৳$price",

                    style = MaterialTheme.typography.titleMedium

                )



            }





            Spacer(

                Modifier.height(10.dp)

            )




            Text(description)





            Spacer(

                Modifier.height(12.dp)

            )



            Text(

                "⏱ Duration: $duration"

            )



            Divider(

                modifier = Modifier.padding(vertical = 10.dp)

            )



            Text(

                "👤 Provider",

                style = MaterialTheme.typography.titleMedium

            )



            Text(provider)



            Text(phone)



            Text(

                "Experience: $experience years"

            )





            Spacer(

                Modifier.height(15.dp)

            )



            Button(

                onClick = {}

                ,

                modifier = Modifier.fillMaxWidth()

            ){


                Text("Book Service")


            }



        }


    }



}