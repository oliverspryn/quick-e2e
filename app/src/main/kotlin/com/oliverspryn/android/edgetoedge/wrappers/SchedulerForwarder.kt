package com.oliverspryn.android.edgetoedge.wrappers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerForwarder @Inject constructor() {
    val io: Scheduler by lazy { Schedulers.io() }
    val mainThread: Scheduler by lazy { AndroidSchedulers.mainThread() }
}
