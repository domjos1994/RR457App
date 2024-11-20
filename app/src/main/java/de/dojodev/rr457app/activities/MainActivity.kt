package de.dojodev.rr457app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.dojodev.rr457app.components.TabBar
import de.dojodev.rr457app.components.TabBarItem
import de.dojodev.rr457app.screens.NewsScreen
import de.dojodev.rr457app.ui.theme.RR457AppTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var title by remember { mutableStateOf("") }

            val newsTab = TabBarItem(1L, "News", Icons.Filled.Notifications, Icons.Outlined.Notifications)
            val items = listOf(newsTab)

            RR457AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = { TabBar(items, navController) },
                        topBar = {
                            Column {
                                Row {
                                    TopAppBar(title = {Text(title)})
                                }
                            }
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
                            navController = navController,
                            startDestination = newsTab.title) {

                            composable(newsTab.title) {
                                title = newsTab.title
                                NewsScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}