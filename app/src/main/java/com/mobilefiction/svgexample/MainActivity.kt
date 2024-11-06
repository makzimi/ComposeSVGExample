package com.mobilefiction.svgexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mobilefiction.svgexample.ui.theme.SvgExampleTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SvgExampleTheme {
                ElectoralMapApp(
                    modifier = Modifier
                        .padding(top = 200.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ElectoralMapApp(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var statePaths by remember {
        mutableStateOf(parseSvgFile(context, "us.svg"))
    }

    InteractiveMap(
        stateStates = statePaths,
        modifier = modifier,
    ) { clickedState ->
        statePaths = statePaths.map { statePath ->
            if (statePath.id == clickedState.id) {
                reduceState(statePath)
            } else {
                statePath
            }
        }
    }
}

private fun reduceState(statePath: StateState): StateState =
    when (statePath.partyChoice) {
        PartyChoice.UNKNOWN -> statePath.copy(
            partyChoice = PartyChoice.DEM,
            color = PartyColor.demBlue,
        )

        PartyChoice.REP -> statePath.copy(
            partyChoice = PartyChoice.UNKNOWN,
            color = PartyColor.unknownGrey,
        )

        PartyChoice.DEM -> statePath.copy(
            partyChoice = PartyChoice.REP,
            color = PartyColor.repRed,
        )
    }


