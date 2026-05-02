package edu.learning.noteapp.di

import edu.learning.noteapp.db.DatabaseDriverFactory
import edu.learning.noteapp.platform.AndroidDeviceInfo
import edu.learning.noteapp.platform.AndroidNetworkMonitor
import edu.learning.noteapp.platform.DeviceInfo
import edu.learning.noteapp.platform.NetworkMonitor
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory(get()) }
    single<DeviceInfo> { AndroidDeviceInfo() }
    single<NetworkMonitor> { AndroidNetworkMonitor(get()) }
}
