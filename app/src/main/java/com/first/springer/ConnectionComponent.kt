package com.first.springer

import com.first.springer.common.ConnectionStateMonitor
import com.first.springer.common.MyApplication
import dagger.Component

@Component
interface ConnectionComponent {
    val connectionStateMonitor: ConnectionStateMonitor?
    fun inject(myApplication: MyApplication?)
}