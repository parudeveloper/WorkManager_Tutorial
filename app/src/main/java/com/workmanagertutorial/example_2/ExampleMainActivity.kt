package com.workmanagertutorial.example_2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.workmanagertutorial.R
import com.workmanagertutorial.databinding.ActivityExampleMainBinding

class ExampleMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityExampleMainBinding
    private val TAG = "ExampleMainActivity"
    lateinit var workManager: OneTimeWorkRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartWork.setOnClickListener() {
            assignWork()
        }
        binding.btnCancel.setOnClickListener(){
            cancelWork(workRequest = workManager)
        }
    }

    private fun assignWork() {

        workManager = OneTimeWorkRequest.Builder(DownloadWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workManager)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workManager.id).observe(
            this@ExampleMainActivity,
            Observer {
                // we can able to track the work progress
                when (it?.state) {
                    WorkInfo.State.ENQUEUED -> {
                        Log.d(TAG, "assignWork: ENQUEUED")
                    }

                    WorkInfo.State.RUNNING -> {
                        Log.d(TAG, "assignWork: RUNNING")
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        Toast.makeText(
                            this@ExampleMainActivity,
                            "Assigned Work is successfully Completed",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, "assignWork: SUCCEEDED")
                    }

                    WorkInfo.State.FAILED -> {
                        Log.d(TAG, "assignWork: FAILED")
                    }

                    WorkInfo.State.BLOCKED -> {
                        Log.d(TAG, "assignWork: BLOCKED")
                    }

                    WorkInfo.State.CANCELLED -> {
                        Log.d(TAG, "assignWork: CANCELLED")
                    }

                    null -> {
                        Log.d(TAG, "assignWork: Work is coming as Null")
                    }
                }
                val progress = it?.progress?.getInt("progress", -1)
                if (progress != -1) {
                    binding.tvProgressData.text = "Download Progress: $progress%"
                    Log.d(TAG, "Download Progress: $progress%")
                }
            })
    }
    private fun cancelWork(workRequest: OneTimeWorkRequest) {
        // Cancel the work
        WorkManager.getInstance(this).cancelWorkById(workRequest.id)
        Log.d("MainActivity", "Work cancelled programmatically.")
    }
}