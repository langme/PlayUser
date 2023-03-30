package com.example.playuser.compose.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.playuser.data.User
import com.example.playuser.viewmodels.UserListViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserListScreen(
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    val users by viewModel.users.observeAsState(initial = emptyList())
    UserListScreen(users = users, modifier, onUserClick = onUserClick)
}

@Composable
fun UserListScreen(
    users: List<User>,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier
            .testTag("user_list")
            .background(color = MaterialTheme.colorScheme.background,),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 12.dp
        )
    ) {
        items(
            items = users,
            key = { it.userId }
        ) { user ->
            UserListItem(user = user) {
                onUserClick(user)
            }
        }
    }
}

@Preview
@Composable
private fun UserListScreenPreview(
    @PreviewParameter(UserListPreviewParamProvider::class) users: List<User>
) {
    UserListScreen(users = users)
}

private class UserListPreviewParamProvider : PreviewParameterProvider<List<User>> {
    override val values: Sequence<List<User>> =
        sequenceOf(
            emptyList(),
            listOf(
                User(1, "Apple", "Apple", "tot@gmail.com", true),
                User(2, "Banana", "Banana","tot@gmail.com", true),
                User(3, "Carrot", "Carrot", "tot@gmail.com", true),
                User(7, "Dill", "Dill", "tot@gmail.com", true),
            )
        )
}