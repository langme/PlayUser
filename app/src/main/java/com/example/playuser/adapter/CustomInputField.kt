package com.example.playuser.adapter

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playuser.R
import com.example.playuser.domain.UIEvent
import com.example.playuser.ui.theme.PlayUserTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFieldComponent(
    typeField: UIEvent.TypeField,
    isError: Boolean,
    onTextChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val label: String
    val leadingIcon: ImageVector


    var text by remember {
        mutableStateOf("")
    }

    when (typeField) {
        UIEvent.TypeField.FIRST -> {
            label = stringResource(id = R.string.firstName)
            leadingIcon = Icons.Filled.Person
        }
        UIEvent.TypeField.LAST -> {
            label = stringResource(id = R.string.lastName)
            leadingIcon = Icons.Default.Edit
        }
        UIEvent.TypeField.EMAIL -> {
            label = stringResource(id = R.string.email)
            leadingIcon =  Icons.Default.Email
        }
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 10.dp),
        singleLine = true,
        label = { Text(text = label) },
        placeholder = { Text(text = label) },
        leadingIcon =  {
            Icon (
                leadingIcon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Back button"
            ) },
        colors =  TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        shape = MaterialTheme.shapes.small.copy(
            bottomEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize
        ),
        isError = isError
    )
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)

@Composable
fun PreviewInputFieldComponent() {
    PlayUserTheme() {
        Surface() {
            var text = "test"
            InputFieldComponent(
                UIEvent.TypeField.FIRST,
                false
            ){text = it}
        }
    }
}