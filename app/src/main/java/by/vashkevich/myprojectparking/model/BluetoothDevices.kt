package by.vashkevich.myprojectparking.model

import android.bluetooth.BluetoothDevice

data class BluetoothDevices(
    val deviceName:String,
    val deviceMac:String,
    val btDevice:BluetoothDevice
)
