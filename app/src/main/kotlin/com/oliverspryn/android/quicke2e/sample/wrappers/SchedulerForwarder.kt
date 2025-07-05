package com.oliverspryn.android.quicke2e.sample.wrappers

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulerForwarder @Inject constructor() {
    val io: Scheduler by lazy { Schedulers.io() }
    val mainThread: Scheduler by lazy { AndroidSchedulers.mainThread() }
}
