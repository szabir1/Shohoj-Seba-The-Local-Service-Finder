package com.example.shohojseba


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.shohojseba.navigation.NavGraph
import com.example.shohojseba.ui.theme.ShohojSebaTheme



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {


            ShohojSebaTheme {


                NavGraph()


            }


        }


    }


}