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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import zeltbrennt.trinkwas.model.TrinkViewModel
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
fun StatusScreenPreview(
    trinkViewModel: ViewModel = viewModel()
) {
}
/*
TODO:
    - Send Notification
    - make state persistent
    - add menue
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(
    modifier: Modifier = Modifier,
    trinkViewModel: TrinkViewModel = viewModel(),
) {
    val trinkUIState by trinkViewModel.uiState.collectAsState()
    val minimum = 100F
    val maximum = 500F

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    )
    {
        Text(
            text = stringResource(R.string.fill_percentage, trinkUIState.currentPercent),
            fontSize = 80.sp
        )
        LinearProgressIndicator(
            modifier = modifier.fillMaxWidth(),
            progress = (trinkUIState.currentPercent / 100F).coerceAtMost(1F)
        )
        Text(
            text = stringResource(
                R.string.fill_amount,
                trinkUIState.amount,
                trinkUIState.target
            )
        )
        Button(onClick = { trinkViewModel.updateUI() }) {
            Text(stringResource(R.string.step, trinkUIState.stepSize.toInt()))
        }
        Slider(
            modifier = modifier.fillMaxWidth(),
            valueRange = (minimum..maximum),
            steps = (maximum.toInt() - minimum.toInt() - 50) / 50,
            value = trinkUIState.stepSize,
            onValueChange = { trinkViewModel.changeStepSize(it) })
        Button(onClick = { trinkViewModel.reset() }) {
            Text(text = stringResource(R.string.reset))
        }
    }

}