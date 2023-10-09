package com.mauriciofeijo.weartimer.presentation

import android.R.attr
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModel: ViewModel() {

    private var _startTime = 0L
    private var _beforePauseTime = 0L
    private val format = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")

    private val _currentState = MutableStateFlow(TimerState.STOPPED)
    val currentState = _currentState.asStateFlow()

    private val _timeElapsed = MutableLiveData(DEFAULT_TIME_VALUE)
    val timeElapsed: LiveData<String> = _timeElapsed

    private fun startTimerFlow() {
        while (currentState.value == TimerState.RUNNING) {
            val millis = System.currentTimeMillis() - _startTime
            _timeElapsed.postValue(LocalTime.ofNanoOfDay(millis * 1_000_000).format(format))
            Thread.sleep(10)
        }
    }

    fun startTimer() {
        _startTime = System.currentTimeMillis()
        if (_currentState.value == TimerState.PAUSED) {
            _startTime -= _beforePauseTime
        }
        _currentState.update { TimerState.RUNNING }
        viewModelScope.launch(Dispatchers.Default) {
            startTimerFlow()
        }
    }

    fun pauseTimer() {
        _currentState.update { TimerState.PAUSED }
        _beforePauseTime = System.currentTimeMillis() - _startTime
    }

    fun stopTimer() {
        _startTime = 0L
        _beforePauseTime = 0L
        _currentState.update { TimerState.STOPPED }
        _timeElapsed.postValue(DEFAULT_TIME_VALUE)
    }

    enum class TimerState {
        STOPPED, PAUSED, RUNNING
    }
}