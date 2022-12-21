package com.evjava.apps_info.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
    ) {
        CompositionLocalProvider(
            LocalExtColors provides (if (darkTheme) DarkExtColors else LightExtColors)
        ) {
            content()
        }
    }
}

private val LightColors = lightColors(
    primary = Color(0xffa4c8d6),
    primaryVariant = Color(0xff7595a4),
    secondary = Color(0xffa475a1),
    background = Color(0xffe1e2e1)
)

private val LightExtColors = ExtColors(
    isDark = false,
    text = Color.Black,
    textSecondary = Color(0xff676867),
    border = Color(0xff999999),
    itemBackground = Color(0xfff5f5f6),
    countBubbleBackground = LightColors.primaryVariant,
    countBubbleText = Color.White,
    toolbarColor = LightColors.primary,
    textButtonColor = LightColors.primaryVariant,
    monospaceBackground = Color(0xffdedede),
)

private val DarkColors = darkColors(
    primary = Color(0xff3f418f),
    primaryVariant = Color(0xff011b61),
    onPrimary = Color(0xffcccccc),
    secondary = Color(0xff5c8b5f),
    onSecondary = Color(0xff959495),
    background = Color(0Xff1f1e1f)
)

private val primaryLightColor = Color(0xff706cc0)

private val DarkExtColors = ExtColors(
    isDark = true,
    text = Color(0xffcccccc),
    textSecondary = Color(0xff959495),
    border = Color(0xff676767),
    itemBackground = Color(0xff333233),
    countBubbleBackground = primaryLightColor,
    countBubbleText = Color.Black,
    toolbarColor = DarkColors.background,
    textButtonColor = primaryLightColor,
    monospaceBackground = Color(0xff424242),
)

val LocalExtColors = staticCompositionLocalOf { LightExtColors }

data class ExtColors(
    val isDark: Boolean,
    val text: Color,
    val textSecondary: Color,
    val border: Color,
    val itemBackground: Color,
    val countBubbleBackground: Color,
    val countBubbleText: Color,
    val toolbarColor: Color,
    val textButtonColor: Color,
    val monospaceBackground: Color,
) {
    val icon: Color
        get() = text
    val disabledIcon: Color
        get() = border
}