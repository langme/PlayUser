package com.example.playuser.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.playuser.R

object Dimens {

    val PaddingSmall: Dp
        @Composable get() = dimensionResource(R.dimen.margin_small)

    val PaddingNormal: Dp
        @Composable get() = dimensionResource(R.dimen.margin_normal)

    val PaddingLarge: Dp = 24.dp

    val UserDetailAppBarHeight: Dp
        @Composable get() = dimensionResource(R.dimen.user_detail_app_bar_height)

    val ToolbarIconPadding = 12.dp

    val ToolbarIconSize = 32.dp
}