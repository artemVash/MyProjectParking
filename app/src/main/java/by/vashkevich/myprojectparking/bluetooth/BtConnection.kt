package by.vashkevich.myprojectparking.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.Context
import by.vashkevich.myprojectparking.utilits.BLUETOOTH
import by.vashkevich.myprojectparking.utilits.initBLE

class BtConnection(
//    val context:Context,
    private val mac:String
    ) {

    private lateinit var device:BluetoothDevice
    private lateinit var connectBLE:ConnectBLEThread

    fun connect(){
        initBLE()
        if (!BLUETOOTH.isEnabled || mac.isEmpty()) return
        device = BLUETOOTH.getRemoteDevice(mac)
        if(device == null) return
        connectBLE = ConnectBLEThread(BLUETOOTH, device)
        connectBLE.start()
    }

}