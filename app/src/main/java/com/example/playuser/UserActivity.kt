package com.example.playuser


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.playuser.compose.PlayUserApp
import com.example.playuser.compose.adduser.RegisterScreen
import com.example.playuser.ui.theme.PlayUserTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlayUserTheme {
                MyBottomAppBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar(){

    val tabItems = listOf("List", "Add")
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.parent?.route
    // todo yet implemented
    //val routeName = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
    }, bottomBar = {
        NavigationBar {
            tabItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = parentRouteName == item,
                    onClick = {
                        selectedItem.value = index
                        navController.navigate(item, navOptions{

                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true

                        })


                    },
                    icon = {
                        when (item) {
                            "List" -> Icon(
                                painter = painterResource(id = R.drawable.baseline_list_24),
                                contentDescription = null
                            )
                            "Add"-> Icon(
                                painter = painterResource(id = R.drawable.baseline_add_reaction_24),
                                contentDescription = null
                            )
                        }

                    },
                    label = { Text(text = item) })
            }

        }

    }) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "List") {
                navigation(startDestination = "ListPage", route = "List") {
                    composable("ListPage", deepLinks = listOf(NavDeepLink("deeplink://home"))) {
                        PlayUserApp(onUserClick ={})
                    }
                }

                navigation(startDestination = "AddPage", route = "Add") {
                    composable(
                        "AddPage",
                        deepLinks = listOf(NavDeepLink("deeplink://product"))
                    ) {
                        RegisterScreen()
                    }
                }
            }
        }
        }
    }
}