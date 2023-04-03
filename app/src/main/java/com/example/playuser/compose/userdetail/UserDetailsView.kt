package com.example.playuser.compose.userdetail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playuser.compose.Dimens
import com.example.playuser.data.User
import com.example.playuser.viewmodels.UserDetailViewModel
import com.example.playuser.R

data class UserDetailsCallbacks(
    val onBackClick: () -> Unit,
)

@Composable
fun UserDetailsScreen(
    userDetailsViewModel: UserDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val user = userDetailsViewModel.user.observeAsState().value
    if (user != null) {
        Surface() {
            UserDetails(
                user,
                UserDetailsCallbacks(
                    onBackClick = onBackClick,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting
@Composable
fun UserDetails(
    user: User,
    callbacks: UserDetailsCallbacks,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Scaffold(
            topBar = {
                UserToolbar(
                    user.firstName, callbacks,
                )
            }, content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserDetailsContent(
                        user = user
                    )
                }
            })
    }
}


@Composable
private fun UserDetailsContent(
    user: User,
) {
    Column() {
        UserInformation(
            firsName = user.firstName,
            lastName = user.lastName,
            userId = user.userId.toString(),
            email = user.emailUser,
            onValueChange= {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserInformation(
    firsName: String,
    lastName: String,
    userId: String,
    email: String,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        ProfileImage()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Id", modifier = Modifier.width(100.dp))
            TextField(
                value = userId,
                onValueChange = { onValueChange },
                colors =  TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = firsName,
                onValueChange = { onValueChange },
                colors =  TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "lastName", modifier = Modifier.width(100.dp))
            TextField(
                value = lastName,
                onValueChange = { onValueChange },
                colors =  TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "email", modifier = Modifier.width(100.dp))
            TextField(
                value = email,
                onValueChange = { onValueChange },
                colors =  TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
            )
        }
    }
}

@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable() { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    //.clickable { launcher.launch("image/*") }
                ,
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}

@ExperimentalMaterial3Api
@Composable
private fun UserToolbar(
    firstName: String,
    callbacks: UserDetailsCallbacks
) {
    UserDetailsToolbar(
        firstName = firstName,
        onBackClick = callbacks.onBackClick,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun UserDetailsToolbar(
    firstName: String,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = firstName)
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
/*
            IconButton(
                onClick = onBackClick
                //    notification.value = "Profile updated"
            ) {
                Icon(Icons.Filled.Save, "Save")
            }*/

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ),
    )
}

@Composable
private fun UserHeaderActions(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = Dimens.ToolbarIconPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val iconModifier = Modifier
            .sizeIn(
                maxWidth = Dimens.ToolbarIconSize,
                maxHeight = Dimens.ToolbarIconSize
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = Dimens.ToolbarIconPadding)
                .then(iconModifier)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Navigate up"
            )
        }
    }
}

@Preview
@Composable
fun UserInformationPreview()
{
    UserInformation(
        firsName = "Jean",
        lastName = "DUPONT",
        userId = "1",
        email = "jean.dupont@gmail.com",
        onValueChange ={}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserDetailsToolbarPreview(){
    UserDetailsToolbar("toto", onBackClick = { Unit })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserHeaderActionsPreview(){
    UserHeaderActions(onBackClick = { Unit })
}