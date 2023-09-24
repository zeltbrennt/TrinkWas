package zeltbrennt.trinkwas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.TrinkWasTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import zeltbrennt.trinkwas.ui.TrinkUIState
import zeltbrennt.trinkwas.ui.TrinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    trinkViewModel: TrinkViewModel = viewModel(factory = TrinkViewModel.Factory),
) {
    LaunchedEffect(key1 = trinkViewModel) {
        coroutineScope { trinkViewModel.retrieveTodayAmount() }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TrinkTopAppBar(trinkViewModel) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {
        StatusScreen(trinkViewModel = trinkViewModel, modifier = modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrinkTopAppBar(
    trinkViewModel: TrinkViewModel,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Settings, contentDescription = "target")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.PlayArrow, contentDescription = "pause")
        }
        AssistChip(
            onClick = { /*TODO*/ },
            label = { Text(text = trinkViewModel.showTimeOfNextEvent()) },
            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            colors = AssistChipDefaults.assistChipColors(
                labelColor = MaterialTheme.colorScheme.onPrimary,
                leadingIconContentColor = MaterialTheme.colorScheme.onPrimary,

                )
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "start")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Clear, contentDescription = "end")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    TrinkWasTheme {
        MainScreen()
    }
}
/*
TODO:
    - Send Notification
    - add menue
 */

@Composable
fun StatusScreen(
    trinkViewModel: TrinkViewModel,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
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
        StatusComposable(trinkUIState = trinkUIState)
        Button(onClick = { coroutineScope.launch { trinkViewModel.drinkGlass() } }) {
            Text(
                "${trinkUIState.glass.toInt()} ml",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Slider(
            modifier = modifier.fillMaxWidth(),
            valueRange = (minimum..maximum),
            steps = (maximum.toInt() - minimum.toInt() - 50) / 50,
            value = trinkUIState.glass,
            onValueChange = { trinkViewModel.changeStepSize(it) })
    }

}

@Composable
fun StatusComposable(
    trinkUIState: TrinkUIState,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(vertical = 20.dp, horizontal = 40.dp)
        ) {
            Text(
                text = "${trinkUIState.currentPercent}%",
                style = MaterialTheme.typography.displayLarge
            )
            Text(text = "${trinkUIState.amount} / ${trinkUIState.target} ml")
        }
    }
}