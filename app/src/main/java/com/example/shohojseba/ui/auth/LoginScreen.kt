package com.example.shohojseba.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.shohojseba.viewmodel.AuthViewModel


@Composable
fun LoginScreen(

    viewModel: AuthViewModel = viewModel(),

    onRegisterClick: () -> Unit,

    onLoginSuccess: (String) -> Unit

) {


    // =====================================================
    // INPUT STATE
    // =====================================================

    var email by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }


    var passwordVisible by remember {
        mutableStateOf(false)
    }


    // =====================================================
    // VIEWMODEL STATE
    // =====================================================

    val message by
    viewModel.message


    val isLoading by
    viewModel.isLoading


    // =====================================================
    // KEYBOARD / FOCUS
    // =====================================================

    val focusManager =
        LocalFocusManager.current


    val keyboardController =
        LocalSoftwareKeyboardController.current


    // =====================================================
    // LOGIN FUNCTION
    // =====================================================

    fun performLogin() {

        focusManager.clearFocus()

        keyboardController?.hide()


        viewModel.login(

            email =
                email.trim(),

            password =
                password,

            onLoginSuccess = { role ->

                onLoginSuccess(role)

            }

        )

    }


    // =====================================================
    // SCREEN
    // =====================================================

    Box(

        modifier =
            Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        listOf(

                            Color(0xFFE8FFFA),

                            Color.White

                        )

                    )

                )

    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxSize()

                    // Allows screen to move when keyboard appears
                    .verticalScroll(
                        rememberScrollState()
                    )

                    // Adds keyboard-safe bottom spacing
                    .imePadding()

                    .padding(
                        horizontal = 30.dp,
                        vertical = 32.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {


            // Gives normal screens some upper breathing room
            Spacer(
                Modifier.height(
                    25.dp
                )
            )


            // =================================================
            // WELCOME ICON
            // =================================================

            Text(

                text =
                    "👋",

                fontSize =
                    55.sp

            )


            Spacer(
                Modifier.height(
                    12.dp
                )
            )


            // =================================================
            // TITLE
            // =================================================

            Text(

                text =
                    "Welcome Back!",

                fontSize =
                    32.sp,

                fontWeight =
                    FontWeight.Bold

            )


            Spacer(
                Modifier.height(
                    6.dp
                )
            )


            Text(

                text =
                    "Login to continue your\nShohojSeba journey",

                color =
                    Color.Gray,

                fontSize =
                    17.sp

            )


            Spacer(
                Modifier.height(
                    34.dp
                )
            )


            // =================================================
            // EMAIL
            // =================================================

            OutlinedTextField(

                value =
                    email,

                onValueChange = {

                    email =
                        it

                    // Remove old error when user edits again
                    viewModel.clearMessage()

                },

                label = {

                    Text(
                        "Email Address"
                    )

                },

                placeholder = {

                    Text(
                        "example@gmail.com"
                    )

                },

                singleLine =
                    true,

                enabled =
                    !isLoading,

                keyboardOptions =
                    KeyboardOptions(

                        keyboardType =
                            KeyboardType.Email,

                        imeAction =
                            ImeAction.Next

                    ),

                keyboardActions =
                    KeyboardActions(

                        onNext = {

                            focusManager
                                .moveFocus(
                                    FocusDirection.Down
                                )

                        }

                    ),

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        18.dp
                    )

            )


            Spacer(
                Modifier.height(
                    15.dp
                )
            )


            // =================================================
            // PASSWORD
            // =================================================

            OutlinedTextField(

                value =
                    password,

                onValueChange = {

                    password =
                        it

                    viewModel.clearMessage()

                },

                label = {

                    Text(
                        "Password"
                    )

                },

                singleLine =
                    true,

                enabled =
                    !isLoading,

                visualTransformation =

                    if (
                        passwordVisible
                    ) {

                        VisualTransformation.None

                    } else {

                        PasswordVisualTransformation()

                    },

                trailingIcon = {

                    IconButton(

                        onClick = {

                            passwordVisible =
                                !passwordVisible

                        }

                    ) {

                        Icon(

                            imageVector =

                                if (
                                    passwordVisible
                                ) {

                                    Icons.Default.VisibilityOff

                                } else {

                                    Icons.Default.Visibility

                                },

                            contentDescription =

                                if (
                                    passwordVisible
                                ) {

                                    "Hide password"

                                } else {

                                    "Show password"

                                }

                        )

                    }

                },

                keyboardOptions =
                    KeyboardOptions(

                        keyboardType =
                            KeyboardType.Password,

                        imeAction =
                            ImeAction.Done

                    ),

                keyboardActions =
                    KeyboardActions(

                        onDone = {

                            performLogin()

                        }

                    ),

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        18.dp
                    )

            )


            // =================================================
            // FRIENDLY ERROR MESSAGE
            // =================================================

            if (
                message.isNotBlank() &&
                message != "Login successful"
            ) {

                Spacer(
                    Modifier.height(
                        16.dp
                    )
                )


                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(
                                    0xFFFFEBEE
                                )

                        )

                ) {

                    Row(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    14.dp
                                ),

                        verticalAlignment =
                            Alignment.Top

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ErrorOutline,

                            contentDescription =
                                null,

                            tint =
                                Color(
                                    0xFFC62828
                                )

                        )


                        Spacer(
                            Modifier.width(
                                10.dp
                            )
                        )


                        Text(

                            text =
                                message,

                            color =
                                Color(
                                    0xFFC62828
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium

                        )

                    }

                }

            }


            Spacer(
                Modifier.height(
                    28.dp
                )
            )


            // =================================================
            // LOGIN BUTTON
            // =================================================

            Button(

                onClick = {

                    performLogin()

                },

                enabled =
                    !isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            60.dp
                        ),

                shape =
                    RoundedCornerShape(
                        25.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Color(
                                0xFF007A7A
                            )

                    )

            ) {


                if (
                    isLoading
                ) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(
                                24.dp
                            ),

                        color =
                            Color.White,

                        strokeWidth =
                            2.5.dp

                    )

                } else {

                    Text(

                        text =
                            "Login →",

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.SemiBold

                    )

                }

            }


            Spacer(
                Modifier.height(
                    18.dp
                )
            )


            // =================================================
            // REGISTER
            // =================================================

            TextButton(

                onClick = {

                    viewModel.clearMessage()

                    onRegisterClick()

                },

                enabled =
                    !isLoading

            ) {

                Text(

                    text =
                        "New to ShohojSeba? Create Account",

                    color =
                        Color(
                            0xFF007A7A
                        ),

                    fontWeight =
                        FontWeight.SemiBold

                )

            }


            // Important when keyboard is visible
            Spacer(
                Modifier.height(
                    40.dp
                )
            )

        }

    }

}