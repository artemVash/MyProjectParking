package by.vashkevich.myprojectparking.utilits

import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import java.io.IOException
import java.io.OutputStream

lateinit var BLUETOOTH : BluetoothAdapter

fun initBLE(){
    BLUETOOTH = BluetoothAdapter.getDefaultAdapter()
}
