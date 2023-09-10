package zeltbrennt.trinkwas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zeltbrennt.trinkwas.ui.theme.TrinkWasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrinkWasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StatusScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    StatusScreen()
}
/*
TODO:
    - Send Notification
    - make state persistent
    - add menue
 */

@Composable
fun StatusScreen(modifier: Modifier = Modifier) {
    val minimum = 100F
    val maximum = 500F
    val target = 3000F
    var current by remember { mutableStateOf(0F) }
    var step by remember { mutableStateOf(200F) }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    )
    {
        Text(
            text = "${(current / target * 100).toInt()}%",
            fontSize = 80.sp
        )
        LinearProgressIndicator(
            modifier = modifier.fillMaxWidth(),
            progress = (current / target).coerceAtMost(1F)
        )
        Text(text = "${current.toInt()} / ${target.toInt()} ml")
        Button(onClick = { current += step }) {
            Text("${step.toInt()}ml")
        }
        Slider(
            modifier = modifier.fillMaxWidth(),
            valueRange = (minimum..maximum),
            steps = (maximum.toInt() - minimum.toInt() - 50) / 50,
            value = step,
            onValueChange = { step = it })
        Button(onClick = { current = 0F }) {
            Text(text = "reset")
        }
    }
}