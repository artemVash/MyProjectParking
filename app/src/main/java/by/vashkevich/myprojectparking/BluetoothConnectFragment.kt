package by.vashkevich.myprojectparking

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.vashkevich.myprojectparking.model.BluetoothDevices
import by.vashkevich.myprojectparking.utilits.BLUETOOTH
import by.vashkevich.myprojectparking.utilits.initBLE


class BluetoothConnectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bluetooth_connect, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBLE()


        val textDeviceSave = view.findViewById<TextView>(R.id.textDeviceSave)
        val textDeviceSearch = view.findViewById<TextView>(R.id.textDeviceSearch)

        textDeviceSave.text = getSaveDevice().toString()

    }



    private fun getSaveDevice(): ArrayList<BluetoothDevices> {
        val arrayDevices = ArrayList<BluetoothDevices>()

        val pairedDevices: Set<BluetoothDevice>? = BLUETOOTH.bondedDevices
        pairedDevices?.forEach { device ->

            val deviceSave = BluetoothDevices(device.name, device.address)
            arrayDevices.add(deviceSave)

        }
        return arrayDevices
    }
}