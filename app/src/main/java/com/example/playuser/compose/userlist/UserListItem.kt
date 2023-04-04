package com.example.playuser.compose.userlist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.playuser.R
import com.example.playuser.data.User


@Composable
fun UserItem(
    user: User,
    onEditClick : (User) -> Unit,
    onRemoveClick: (User) -> Unit,
    onUpCount : (User) -> Unit,
    onDownCount : (User) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        shape = MaterialTheme.shapes.small,
    ) {

        Row(Modifier.height(80.dp)) {
            IconProfile(Modifier.weight(1f))
            InfoProfile(Modifier.weight(3f), user)
            CountProfile(user, Modifier.weight(1f),
                onUpCount = {
                    onUpCount(user)
                },
                onDownCount = {
                    onDownCount(user)
                }
            )
            EditProfile(user, Modifier.weight(1f),
                onEditClicked = {
                    onEditClick(user)
                })
            DeleteProfile(user, Modifier.weight(1f),
                onRemoveClicked = {
                onRemoveClick(user)
            })
        }
        Divider(
            thickness = 2.dp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun IconProfile(
    modifier: Modifier = Modifier,
){
    Box(modifier){
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(id = R.string.profile),
            modifier = Modifier
                .padding(7.dp)
                .size(46.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun InfoProfile(modifier: Modifier = Modifier, user: User){
    Box(modifier){
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (firstText, secondText, thirdText, quatoText) = createRefs()
            Text(
                modifier = Modifier.constrainAs(firstText) {
                    top.linkTo(parent.top,)
                    start.linkTo(parent.start, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
                text = user.firstName,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Start,
            )
            Text(
                modifier = Modifier.constrainAs(secondText) {
                    top.linkTo(firstText.bottom)
                    start.linkTo(parent.start,margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
                text = user.lastName,
                textAlign = TextAlign.Start,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.constrainAs(thirdText) {
                    top.linkTo(secondText.bottom,)
                    start.linkTo(parent.start,margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
                text = user.emailUser,
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.constrainAs(quatoText) {
                    top.linkTo(thirdText.bottom,)
                    start.linkTo(parent.start,margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
                text = user.count.toString(),
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun EditProfile(
    user: User,
    modifier: Modifier = Modifier,
    onEditClicked: (user :User) -> Unit
){
    Box(modifier){
        IconButton(
            onClick = {
                onEditClicked(user)
            },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(7.dp)
                .size(46.dp)
        ){
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit ${user.userId}"
            )
        }
    }
}

@Composable
fun CountProfile(
    user: User,
    modifier: Modifier = Modifier,
    onUpCount: (user :User) -> Unit,
    onDownCount: (user :User) -> Unit
){
    Box(){
        Column() {
            Row(modifier = Modifier
                .padding(2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {
                        onUpCount(user)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(28.dp),
                ){
                    Icon(
                        imageVector = Icons.Filled.ArrowDropUp,
                        contentDescription = "count UP ${user.userId}"
                    )
                }
            }
            Row(
                modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {
                        onDownCount(user)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(28.dp),
                ){
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Count Down ${user.userId}"
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteProfile(
    user: User,
    modifier: Modifier = Modifier,
    onRemoveClicked: (user: User) -> Unit
){
    Box(modifier){
        IconButton(
            onClick = {
                      onRemoveClicked(user)
                      },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(7.dp)
                .size(46.dp)
        ){
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove ${user.userId}"
            )
        }
    }
}

@Preview
@Composable
fun  ImageListItemPreview(
    @PreviewParameter(UserPreviewParamProvider::class) user: User)
{
    UserItem(
        user = user,
        onEditClick = {},
        onRemoveClick = {},
        onUpCount = {},
        onDownCount = {}
    )
}

@Preview
@Composable
fun CountProfilePreview(
    @PreviewParameter(UserPreviewParamProvider::class) user: User)
{
    CountProfile(
        user = user,
        onUpCount = {},
        onDownCount = {}
    )
}

private class UserPreviewParamProvider : PreviewParameterProvider<User> {
    override val values: Sequence<User> =
        sequenceOf(
                User(1, "Apple", "test", "tot@gmail.com", true),
        )
}