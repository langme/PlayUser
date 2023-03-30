package com.example.playuser.compose.adduser

import com.example.playuser.ui.theme.PlayUserTheme

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playuser.viewmodels.UserAddViewModel
import com.example.playuser.R
import com.example.playuser.adapter.InputFieldComponent
import com.example.playuser.data.User
import com.example.playuser.domain.UIEvent
import com.example.playuser.domain.ValidationEvent


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    viewModel: UserAddViewModel = hiltViewModel(),
) {
    val modifier: Modifier = Modifier
    val context = LocalContext.current

    val state = viewModel.uiState.value
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            keyboard?.hide()
            when(event) {
                is ValidationEvent.Success -> {
                    viewModel.addUser(User(
                        firstName =  viewModel.uiState.value.firstName,
                        lastName =  viewModel.uiState.value.lastName,
                        email =  viewModel.uiState.value.email
                    ))
                    Toast
                        .makeText(context,"All inputs are valid", Toast.LENGTH_SHORT)
                        .show()
                }

                is ValidationEvent.Error -> {
                    Toast
                        .makeText(context, "All inputs are not valid", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val configuration = LocalConfiguration.current
        val weightInputField: Float

        when(configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                weightInputField = 2f
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                weightInputField = 1.5f
            }
            else -> {
                // Orientation Square & undefined
                weightInputField = 1.5f
            }
        }

        Column (
            modifier.fillMaxSize(),
        ){
            Box (modifier = Modifier
                .weight(weightInputField)
            ){
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // first name extField
                        InputFieldComponent(
                            UIEvent.TypeField.FIRST, state.hasFirstNameError
                        ) { viewModel.onEvent(UIEvent.FirstNameChanged(it)) }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // last name extField
                        InputFieldComponent(
                            UIEvent.TypeField.LAST, state.hasLastNameError
                        ) { viewModel.onEvent(UIEvent.LastNameChanged(it)) }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // email name extField
                        InputFieldComponent(
                            UIEvent.TypeField.EMAIL, state.hasEmailError
                        ) { viewModel.onEvent(UIEvent.EmailChanged(it)) }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(UIEvent.Submit)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(80.dp, 10.dp)
                        ) {
                            Text(text = stringResource(id = R.string.register))
                        }
                    }
                }
            }

        }
    }

}

@Preview
@Composable
private fun RegisterScreenPreview() {
    PlayUserTheme() {
        RegisterScreen()
    }
}