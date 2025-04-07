package com.workmanagertutorial.example_2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        for (i in 1..10) {
            Thread.sleep(1000)
            if (i == 6){
                break
            }
            Log.d("DownloadWorker", "doWork: $i")
            setProgressAsync(workDataOf("progress" to i * 10))
        }
        Log.d("DownloadWorker", "Download complete!")

        return Result.success()
    }
}