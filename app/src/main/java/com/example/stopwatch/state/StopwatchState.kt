package com.example.stopwatch.state

sealed class StopwatchState {

    data class Paused(
        val elapsedTime: Long,
    ) : StopwatchState()
    data class Running(
        val startTime: Long,
        val elapsedTime: Long,
    ) : StopwatchState()
}