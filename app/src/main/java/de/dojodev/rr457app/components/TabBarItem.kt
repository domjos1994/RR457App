package de.dojodev.rr457app.components

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class TabBarItem(
    val id: Long,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unSelectedIcon: ImageVector? = null,
    val selectedBitmap: Painter? = null,
    val unSelectedBitmap: Painter? = null,
    val visible: Boolean = true
)
