WorkManager is a tool in Android that helps you run tasks in the background even when your app is not open or the phone is restarted. It's perfect for tasks that need to be done reliably, like syncing data, uploading files, or doing long-running jobs.
For example, if you want your app to upload a file even after the app is closed, WorkManager will make sure the upload happens, whether the app is running or not.


dependencies {
    implementation "androidx.work:work-runtime-ktx:2.7.1" // Check for the latest version
}

1.OneTimeWorkRequest
2.PeriodicWorkRequest

OneTime Work Request 

Step 1:
//Define Your Work like below

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
      // Here Do your Work 
        return Result.success()
    }
}

Step 2:
// In Main Activity we need to schedule our worker class like below 

val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
    .build()
WorkManager.getInstance(context).enqueue(myWorkRequest)

Periodic Work Request 

Step 1:
//Define Your Work like below

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
      // Here Do your Work 
        return Result.success()
    }
}
Step 2:
// In Main Activity we need to schedule our worker class like below 

// This request will triggered hourly basis 
val myPeriodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.HOURS)
    .build()
WorkManager.getInstance(context).enqueue(myPeriodicWorkRequest)

