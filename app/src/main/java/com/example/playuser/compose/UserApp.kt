package com.example.playuser.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.playuser.compose.userlist.UserListScreen
import com.example.playuser.data.User

@Composable
fun PlayUserApp(
    onUserClick: (User) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    UserListScreen(
        onUserClick = onUserClick,
        modifier = modifier.fillMaxSize(),
    )
}

