package by.vashkevich.myprojectparking.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectBLEThread(
//    val context: Context,
    private val btAdapter: BluetoothAdapter,
    device: BluetoothDevice
) : Thread() {

    private val UUID = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID))
    lateinit var conTEhr:ConnectedThread


    private var success = false

    override fun run() {
        super.run()
        btAdapter.cancelDiscovery()

        sleep(500)

        try {
            mSocket.connect()
            Log.e("MyLog", "connect")

            success = true
//            getMessage(1)


        } catch (e: IOException) {
            try {
                closeConnection()
                Log.e("MyLog", "no connect")
            } catch (y: IOException) {

            }
        }

        if (success) {
            conTEhr = ConnectedThread(mSocket)
            conTEhr.start()
            enableLed()
            Log.e("MyLog", "success")
        }
    }

    fun closeConnection() {
        try {
            mSocket.close()
        } catch (e: IOException) {
        }

    }

//    fun getMessage(command: Int) {
//        val buffer = command
//
//        if (mOutPutStream != null) {
//            try {
//                mOutPutStream.write(command)
//                mOutPutStream.flush()
//                Log.e("MyLog", "отправлено")
//            } catch (e: IOException) {
//                e.message
//            }
//        }
//    }

    fun enableLed(){
        if (mSocket != null && mSocket.isConnected){
            conTEhr.write(1)
            Log.e("MyLog", "message")
        }
    }


    class ConnectedThread(
        btSocket: BluetoothSocket
    ) : Thread() {

        lateinit var inputStream: InputStream
        lateinit var outputStream: OutputStream

        init {
            try {
                inputStream = btSocket.inputStream
                outputStream = btSocket.outputStream
            } catch (e: IOException) {

            }
        }
        fun write(command: Int) {

            if (outputStream != null) {
                try {
                    outputStream.write(command)
                    outputStream.flush()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }

        fun cancel(){
            try {
                inputStream.close()
                outputStream.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

}