package com.mauriciofeijo.weartimer.presentation.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.mauriciofeijo.weartimer.R
import com.mauriciofeijo.weartimer.presentation.DEFAULT_TIME_VALUE
import com.mauriciofeijo.weartimer.presentation.TimerViewModel
import com.mauriciofeijo.weartimer.presentation.components.ActionButton

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun TimerApp(
    viewModel: TimerViewModel = viewModel(),
    state: TimerViewModel.TimerState = viewModel.currentState.collectAsStateWithLifecycle().value,
    text: String = viewModel.timeElapsed.observeAsState(DEFAULT_TIME_VALUE).value
) {
    Column(
        modifier = Modifier.fillMaxSize(),

        ) {
        Text(text = text)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state != TimerViewModel.TimerState.RUNNING) {
                ActionButton(text = stringResource(R.string.start)) { viewModel.startTimer() }
            } else {
                ActionButton(text = stringResource(R.string.pause)) { viewModel.pauseTimer() }
            }
            ActionButton(text = stringResource(R.string.stop), enabled = state != TimerViewModel.TimerState.STOPPED) { viewModel.stopTimer() }
        }
    }
}