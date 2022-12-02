package com.example.shoppinglist.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.black
import com.example.shoppinglist.ui.theme.floatingB


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.firebase1),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(top = 30.dp)
                .size(90.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = "Login",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = floatingB
        )

        if (isError){
            Text(
                text = loginUiState?.loginError ?: "unknown error",
                color = Color.Red,
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userName ?: "",
            onValueChange = {loginViewModel?.onUserNameChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Email")
            },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = floatingB,
                unfocusedBorderColor = Gray,
                focusedLabelColor =floatingB,
                cursorColor =floatingB,
            ),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.password ?: "",
            onValueChange = { loginViewModel?.onPasswordNameChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Password")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = floatingB,
                unfocusedBorderColor = Gray,
                focusedLabelColor =floatingB,
                cursorColor =floatingB,
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = { loginViewModel?.loginUser(context) },
            colors = ButtonDefaults.buttonColors(backgroundColor = floatingB, contentColor = black)) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Don't have an Account?",  modifier = Modifier.padding(top = 12.dp))
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToSignUpPage.invoke() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = floatingB, backgroundColor = Color.Transparent)
            ) {
                Text(text = "SignUp")
            }


        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator( color = floatingB)

        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }







    }


}

@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToLoginPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = floatingB
        )

        if (isError){
            Text(
                text = loginUiState?.signUpError ?: "unknown error",
                color = Color.Red,
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userNameSignUp?: "",
            onValueChange = {loginViewModel?.onUserNameChangeSignup(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Email")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = floatingB,
                unfocusedBorderColor = Gray,
                focusedLabelColor =floatingB,
                cursorColor =floatingB,
            ),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.passwordSignUp ?: "",
            onValueChange = { loginViewModel?.onPasswordChangeSignup(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Password")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = floatingB,
                unfocusedBorderColor = Gray,
                focusedLabelColor =floatingB,
                cursorColor =floatingB,
            ),

            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.confirmPasswordSignUp ?: "",
            onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Confirm Password")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = floatingB,
                unfocusedBorderColor = Gray,
                focusedLabelColor =floatingB,
                cursorColor =floatingB,
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = { loginViewModel?.createUser(context) }
            , colors = ButtonDefaults.buttonColors(backgroundColor = floatingB,contentColor = black)

        ) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Already have an Account?",modifier = Modifier.padding(top = 12.dp))
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = floatingB, backgroundColor = Color.Transparent)) {
                Text(text = "Sign In")
            }

        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }







    }


}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {

        LoginScreen(onNavToHomePage = { /*TODO*/ }) {


    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevSignUpScreen() {

        SignUpScreen(onNavToHomePage = { /*TODO*/ }) {

        }
    }



