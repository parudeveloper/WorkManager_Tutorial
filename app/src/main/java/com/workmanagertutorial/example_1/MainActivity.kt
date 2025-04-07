package com.workmanagertutorial.example_1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.workmanagertutorial.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartWork.setOnClickListener() {
            setWorkRequest()
        }

    }

    private fun setWorkRequest() {

        // If we want to put put any constraints like Network and Battery Status we can able to add like below
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true).build()

        //If we want to send the data we can able to send the data through Data.Builder
        val inputData =
            Data.Builder().putString("InputData", "Data is Coming From Main Activity").build()

        // This is Periodic Work Request
        val workRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        // this is One Time Work Request
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java )
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(
            this@MainActivity,
            Observer {
                if (it != null) {
                    if (it.state.isFinished){
                        Log.d("oneTimeWorkRequest", "setWorkRequest:${it.outputData} ")
                    }
                }

            })
    }
}