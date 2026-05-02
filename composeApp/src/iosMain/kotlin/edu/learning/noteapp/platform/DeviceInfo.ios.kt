package edu.learning.noteapp.platform

import platform.UIKit.UIDevice

class IosDeviceInfo : DeviceInfo {
    override val model: String = UIDevice.currentDevice.model
    override val osVersion: String = "${UIDevice.currentDevice.systemName} ${UIDevice.currentDevice.systemVersion}"
    override val platform: String = "iOS"
}
