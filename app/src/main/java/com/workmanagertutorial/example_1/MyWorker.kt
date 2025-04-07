package com.workmanagertutorial.example_1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        // From here we can able to get the data which we are pasing from main activity
        val inputData = getInputData().getString("InputData")
        // Just We are printing the data here
        Log.d("WorkManagerData", "Periodic Work Request Input Data is $inputData")

        val data = Data.Builder().putString("DataFromWorker","Data is passing from Worker Class").build()
        return Result.success(data )
    }
}