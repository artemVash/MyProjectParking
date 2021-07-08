package by.vashkevich.myprojectparking.utilits

import android.bluetooth.BluetoothAdapter

lateinit var BLUETOOTH : BluetoothAdapter

fun initBLE(){
    BLUETOOTH = BluetoothAdapter.getDefaultAdapter()
}