package edu.learning.noteapp.di

import edu.learning.noteapp.db.DatabaseDriverFactory
import edu.learning.noteapp.platform.DeviceInfo
import edu.learning.noteapp.platform.IosDeviceInfo
import edu.learning.noteapp.platform.IosNetworkMonitor
import edu.learning.noteapp.platform.NetworkMonitor
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory() }
    single<DeviceInfo> { IosDeviceInfo() }
    single<NetworkMonitor> { IosNetworkMonitor() }
}
