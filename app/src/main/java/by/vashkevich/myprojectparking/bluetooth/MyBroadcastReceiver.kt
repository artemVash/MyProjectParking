package by.vashkevich.myprojectparking.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (BluetoothDevice.ACTION_FOUND == intent?.action){
            val devise = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            Toast.makeText(context,devise?.name,Toast.LENGTH_LONG).show()


        }
    }
}