package de.dojodev.rr457app.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TabBar(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    var height by remember { mutableStateOf(80.dp) }
    var showText by remember { mutableStateOf(true) }
    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        height = 45.dp
        showText = false
    }

    NavigationBar(modifier = Modifier.height(height)) {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            if(tabBarItem.visible) {
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unSelectedIcon,
                            selectedPainter = tabBarItem.selectedBitmap,
                            unselectedPainter = tabBarItem.unSelectedBitmap,
                            title = tabBarItem.title
                        )
                    },
                    label = {
                        if(showText && tabBarItems.filter { it.visible }.size < 7) {
                            Text(tabBarItem.title, fontSize = 10.sp)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector?,
    unselectedIcon: ImageVector?,
    selectedPainter: Painter?,
    unselectedPainter: Painter?,
    title: String
) {
    if(selectedIcon == null && unselectedIcon == null && selectedPainter == null && unselectedPainter == null) {
        return
    }
    if(selectedIcon != null || unselectedIcon != null) {
        val selected = selectedIcon ?: unselectedIcon
        val unselected = unselectedIcon ?: selectedIcon

        BadgedBox(
            badge = {}) {
            Icon(
                imageVector = if (isSelected) {selected} else {unselected}!!,
                contentDescription = title
            )
        }
    }
    if(selectedPainter != null || unselectedPainter != null) {
        val selected = selectedPainter ?: unselectedPainter
        val unselected = unselectedPainter ?: selectedPainter

        BadgedBox(
            badge = {}) {
            Icon(
                painter = if (isSelected) {selected} else {unselected}!!,
                contentDescription = title
            )
        }
    }
}