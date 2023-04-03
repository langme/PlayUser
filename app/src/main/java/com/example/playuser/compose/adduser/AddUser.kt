package com.example.playuser.compose.adduser

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.ExperimentalComposeUiApi
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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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

    RegisterUiScreen(viewModel, modifier)

}

@Composable
fun RegisterUiScreen(
    viewModel: UserAddViewModel,
    modifier: Modifier
){
    val state = viewModel.uiState.value

    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column (
            modifier.fillMaxSize(),
        ){
            Box (modifier = Modifier
                .weight(2f)
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