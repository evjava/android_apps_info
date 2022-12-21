package com.evjava.apps_info.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.ui.theme.LocalExtColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFieldUI(searchState: SearchState.Enabled, isDisabled: MutableState<Boolean>, callback: (String?) -> Unit) {
    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val search = searchState.text
    val searchText = remember { mutableStateOf(TextFieldValue(search, TextRange(search.length))) }
    if (search != searchText.value.text) {
        searchText.value = TextFieldValue(search, TextRange(search.length))
    }

    OutlinedTextField(
        value = searchText.value,
        onValueChange = {
            if (isDisabled.value) {
                return@OutlinedTextField
            }
            searchText.value = it
            callback(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 0.dp, bottom = 0.dp)
            .focusRequester(focusRequester),
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black), // todo fix
        placeholder = { Text(text = "Search...") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary,
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { callback(null) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
//                        tint = LocalExtColors.current.text,
                        contentDescription = null
                    )
                }

            }
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}