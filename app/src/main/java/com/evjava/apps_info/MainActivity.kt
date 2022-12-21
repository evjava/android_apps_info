package com.evjava.apps_info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.ui.compose.RootContent
import com.evjava.apps_info.ui.navigation.RootComponent
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.apps_info.ui.theme.AppsInfoTheme

class MainActivity : ComponentActivity(), ApperContextI {
    lateinit var rootComponent: RootComponentI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent = RootComponent(this, defaultComponentContext())

        setContent {
            RootContent(component = rootComponent)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppsInfoTheme {
        Greeting("Android")
    }
}