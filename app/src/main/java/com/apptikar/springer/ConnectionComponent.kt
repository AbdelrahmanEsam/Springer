package com.apptikar.springer

import com.apptikar.springer.common.ConnectionStateMonitor
import com.apptikar.springer.common.MyApplication
import dagger.Component

@Component
interface ConnectionComponent {
    val connectionStateMonitor: ConnectionStateMonitor?
    fun inject(myApplication: MyApplication?)
}