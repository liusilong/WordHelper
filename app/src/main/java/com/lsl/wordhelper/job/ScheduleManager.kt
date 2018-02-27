package com.lsl.wordhelper.job

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context

/**
 * Created by liusilong on 2018/1/8.
 * version:1.0
 * Describe: 管理 job
 */

object ScheduleManager {
    private const val CLIPBOARD_LISTENER_JOB = 0
    var jobScheduler: JobScheduler? = null

    private fun getJobScheduler(context: Context): JobScheduler? {
        if (jobScheduler == null) {
            jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        }
        return jobScheduler
    }

    fun scheduleClipboardJob(context: Context) {
        val jobInfo = JobInfo.Builder(
                CLIPBOARD_LISTENER_JOB,
                ComponentName(context, ClipboardJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build()
        getJobScheduler(context)?.schedule(jobInfo)
    }
}
