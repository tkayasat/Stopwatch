package com.example.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.data.TimestampProviderImpl
import com.example.stopwatch.data.calculate.ElapsedTimeCalculator
import com.example.stopwatch.data.calculates.StopwatchStateCalculator
import com.example.stopwatch.databinding.ActivityMainBinding
import com.example.stopwatch.viewmodel.StopwatchStateHolder
import com.example.stopwatch.viewmodel.StopwatchViewModel
import com.example.stopwatch.viewmodel.StopwatchViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val timestampProvider = TimestampProviderImpl()
    private lateinit var stopwatchModel: StopwatchViewModel

    companion object {
        const val TIMER_1 = 1
        const val TIMER_2 = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stopwatchModel = StopwatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider)
                ),
                ElapsedTimeCalculator(timestampProvider)
            )
        ).create(StopwatchViewModel::class.java)

        stopwatchModel.getLiveData().observe(this) {
            when (it.first) {
                TIMER_1 -> binding.textTime1.text = it.second
                TIMER_2 -> binding.textTime2.text = it.second
            }
        }

        binding.buttonStart1.setOnClickListener {
            stopwatchModel.start(TIMER_1)
        }
        binding.buttonPause1.setOnClickListener {
            stopwatchModel.pause(TIMER_1)
        }
        binding.buttonStop1.setOnClickListener {
            stopwatchModel.stop(TIMER_1)
        }

        binding.buttonStart2.setOnClickListener {
            stopwatchModel.start(TIMER_2)
        }
        binding.buttonPause2.setOnClickListener {
            stopwatchModel.pause(TIMER_2)
        }
        binding.buttonStop2.setOnClickListener {
            stopwatchModel.stop(TIMER_2)
        }

    }
}