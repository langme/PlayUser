package com.example.playuser.compose.userlist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.playuser.data.User

@Composable
fun UserListItem(user: User, onClick: () -> Unit) {
    ImageListItem(user = user, onClick = onClick)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListItem(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
    ) {

        Row(Modifier.height(60.dp)) {
            IconProfile(Modifier.weight(1f))
            InfoProfile(Modifier.weight(3f), user)
            EditProfile(Modifier.weight(1f))
        }
        Divider(
            thickness = 2.dp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun IconProfile(modifier: Modifier = Modifier){
    Box(modifier){
        Icon(
            Icons.Filled.Person,
            contentDescription = "person",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(7.dp)
                .size(46.dp)
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
            val (firstText, secondText, thirdText) = createRefs()
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
        }
    }
}

@Composable
fun EditProfile(modifier: Modifier = Modifier){
    Box(modifier){
        Icon(
            Icons.Filled.Edit,
            contentDescription = "person",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(7.dp)
                .size(46.dp)
        )
    }
}

@Preview
@Composable
fun  ImageListItemPreview(
    @PreviewParameter(UserPreviewParamProvider::class) user: User)
{
    ImageListItem(user = user){}
}

private class UserPreviewParamProvider : PreviewParameterProvider<User> {
    override val values: Sequence<User> =
        sequenceOf(
                User(1, "Apple", "test", "tot@gmail.com", true),
        )
}