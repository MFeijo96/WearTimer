package com.mauriciofeijo.weartimer.presentation

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.log10


class SoundViewModel : ViewModel() {

    private var mRecorder: MediaRecorder? = null
    private var mEMA = 0.0
    private val EMA_FILTER = 0.6

    private val _value = MutableLiveData(0.0)
    val value: LiveData<Double> = _value

    private fun startRecord() {
        while (mRecorder != null) {
            Thread.sleep(100)
            _value.postValue(getAmplitudeEMA())
        }
    }

    fun soundDb(ampl: Double): Double {
        return 20 * log10(getAmplitudeEMA() / ampl)
    }

    fun getAmplitude(): Double {
        return if (mRecorder != null) mRecorder!!.maxAmplitude.toDouble() else 0.0
    }

    fun getAmplitudeEMA(): Double {
        val amp = getAmplitude()
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA
        return mEMA
    }

    fun startRecorder() {
        if (mRecorder == null) {
            mRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile("/dev/null")
                try {
                    prepare()
                } catch (ioe: IOException) {
                    Log.e("[Monkey]", "IOException: " + Log.getStackTraceString(ioe))
                } catch (e: SecurityException) {
                    Log.e("[Monkey]", "SecurityException: " + Log.getStackTraceString(e))
                }
                try {
                    start()
                } catch (e: SecurityException) {
                    Log.e("[Monkey]", "SecurityException: " + Log.getStackTraceString(e))
                }
            }

            //mEMA = 0.0;

            viewModelScope.launch(Dispatchers.Default) { startRecord() }
        }
    }

    fun stopRecorder() {
        mRecorder?.let {
            it.stop()
            it.release()
        }
        mRecorder = null
    }
}