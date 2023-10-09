package com.mauriciofeijo.weartimer.presentation.apps

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mauriciofeijo.weartimer.presentation.SoundViewModel
import com.mauriciofeijo.weartimer.presentation.components.SoundBar

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun SoundApp(
    viewModel: SoundViewModel = viewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        start(viewModel)

        val soundValue = viewModel.value.observeAsState(0).value
        SoundBar(Color(0xFFD64E34), soundValue.toDouble() > 20000)
        SoundBar(Color(0xFFE07538), soundValue.toDouble() > 18000)
        SoundBar(Color(0xFFE5A145), soundValue.toDouble() > 16000)
        SoundBar(Color(0xFFE1Ca4C), soundValue.toDouble() > 13000)
        SoundBar(Color(0xFFEEE350), soundValue.toDouble() > 10000)
        SoundBar(Color(0xFFE9E66B), soundValue.toDouble() > 7500)
        SoundBar(Color(0xFFE3E55A), soundValue.toDouble() > 6000)
        SoundBar(Color(0xFFAFCE59), soundValue.toDouble() > 4000)
        SoundBar(Color(0xFFA1C85A), soundValue.toDouble() > 2000)
        SoundBar(Color(0xFF86BE56), soundValue.toDouble() > 100)
    }
}

@Composable
fun start(viewModel: SoundViewModel) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }
    val context = LocalContext.current

    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) -> {
            viewModel.startRecorder()
        }
        else -> {
            // Asking for permission
            launcher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}