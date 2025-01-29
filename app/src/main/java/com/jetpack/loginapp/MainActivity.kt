package com.jetpack.loginapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.loginapp.ui.theme.LoginAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource as stringResource1

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginAppTheme {
                val scope = rememberCoroutineScope()
                val snackBarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
                ) { padding ->
                    LoginScreenComposable(
                        modifier = Modifier.padding(padding),
                        scope = scope,
                        snackBarHostState = snackBarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreenComposable(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var message = stringResource1(R.string.log_username_and_password, email, password)

    Box(
        // ToDo: Use a Box for full-screen centering
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            // ToDo: Align the entire Column to the bottom
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text : App Name
            Text(
                text = stringResource1(id = R.string.app_name),
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
            )

            // Image : Jetpack Compose Image
            Image(
                painter = painterResource(id = R.drawable.ic_jetpack_compose),
                modifier = Modifier
                    .height(400.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "App logo"
            )

            // Text Field : Username
            OutlinedTextField(
                modifier = Modifier.padding(10.dp),
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = stringResource1(id = R.string.label_username)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource1(id = R.string.place_holder_enter_username)
                    )
                }
            )

            // Text Field : Password
            OutlinedTextField(
                modifier = Modifier.padding(10.dp),
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        text = stringResource1(id = R.string.label_password)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource1(id = R.string.place_holder_enter_password)
                    )
                }
            )

            // Button : SUBMIT
            var controller = LocalSoftwareKeyboardController.current
            Button(
                // ToDo: Shows the snack-bar message while clicking on the "SUBMIT" button
                modifier = Modifier.padding(20.dp),
                onClick = {
                    controller?.hide()
                    scope.launch {
                        val result: SnackbarResult = snackBarHostState.showSnackbar(
                            message = "You are successfully logged in.",
                            duration = SnackbarDuration.Long
                        )
                    }
                    Log.e("MainActivity", message)
                }
            ) {
                // Button Text : CLICK ME!
                Text(
                    text = stringResource(R.string.action_submit),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    name = "Pixel 4 XL"
)
@Composable
fun TextEntryComposablePreview() {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LoginAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) { padding ->
            LoginScreenComposable(
                modifier = Modifier.padding(padding),
                scope = scope,
                snackBarHostState = snackBarHostState
            )
        }
    }
}
