package com.example.playuser.compose.userlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.playuser.data.User
import com.example.playuser.viewmodels.UserListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playuser.viewmodels.UserListViewEvent

@Composable
fun UserListScreen(
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.consumableState().collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier
            .testTag("user_list")
            .background(color = MaterialTheme.colorScheme.background,),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 12.dp
        )
    ){
        items(
            items = viewState.value.users,
            key = { item -> item.userId }
        ) { user ->
            UserItem(
                user = user,
                onEditClick = {
                    viewModel.handleViewEvent(UserListViewEvent.EditItem(user))
                    onUserClick(user)
                    Log.d("", "Edit ${user.userId}")
                },
                onRemoveClick = {
                    viewModel.handleViewEvent(UserListViewEvent.RemoveItem(user))
                    Log.d("", "Remove ${user.userId}")
                })
        }
    }

    if (viewState.value.isLoading){
        LoadingIndicator()
    }
}

@Composable
fun LoadingIndicator(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
            androidx.compose.material3.CircularProgressIndicator()
        }
}
@Composable
fun UserListScreen(
    users: List<User>,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
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
            // know if you display Edit / delete profile
            UserItem(
                user = user,
                onEditClick = {
                    viewModel.handleViewEvent(UserListViewEvent.EditItem(user))
                },
                onRemoveClick = {
                    viewModel.handleViewEvent(UserListViewEvent.RemoveItem(user))
                })
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