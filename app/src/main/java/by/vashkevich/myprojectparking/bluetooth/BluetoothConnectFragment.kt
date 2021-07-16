package by.vashkevich.myprojectparking.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.vashkevich.myprojectparking.MainViewModel
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.model.BluetoothDevices
import by.vashkevich.myprojectparking.utilits.BLUETOOTH
import by.vashkevich.myprojectparking.utilits.ID_AND_ID_DEN
import by.vashkevich.myprojectparking.utilits.initBLE
import kotlinx.coroutines.*


class BluetoothConnectFragment : Fragment() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var list = ArrayList<BluetoothDevices>()
    lateinit var myReceiver: BroadcastReceiver
    lateinit var btConnection:BtConnection
    lateinit var connectedThread : ConnectBLEThread.ConnectedThread

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

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
        broadcast()
        BLUETOOTH.startDiscovery()


        val textDeviceSave = view.findViewById<TextView>(R.id.textDeviceSave)
        val textDeviceSearch = view.findViewById<TextView>(R.id.textDeviceSearch)
        val connect = view.findViewById<TextView>(R.id.connect)

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_test)

        val btnCon = view.findViewById<Button>(R.id.btn_ble_test)

        list = broadcast()

        textDeviceSearch.isVisible = false
        textDeviceSave.isVisible = false
        btnCon.isVisible = false
        connect.isVisible = false
        progressBar.isVisible = true

        ioScope.launch {
            delay(8000)
            withContext(Dispatchers.Main) {

                textDeviceSearch.isVisible = true
                textDeviceSave.isVisible = true
                btnCon.isVisible = true
                connect.isVisible = true
                progressBar.isVisible = false

                viewModel.list.observe(requireActivity()) {
                    textDeviceSearch.text = it.toString()
                }
            }
        }
        textDeviceSave.text = getSaveDevice().toString()

        btnCon.setOnClickListener {
            //сопряжение с существуещеми устройствами (не работает)
            for (ble in getSaveDevice()) {
                viewModel.idDen.observe(requireActivity()) {

                    val id = ID_AND_ID_DEN[it]

                    if (id == ble.btDevice.name) {
                        btConnection = BtConnection(ble.btDevice.address)
                        btConnection.connect()
                    }
                }
            }

            //сопряжение с найдеными устройствами
//            for (ble in list) {
//                viewModel.idDen.observe(requireActivity()) {
//
//                    val id = ID_AND_ID_DEN[it]
//
//                    if (id == ble.btDevice.name) {
////                        ble.btDevice.createBond()
//
//                        connect.text = "true ${id},${ble.btDevice.name}"
//                    } else {
////                        connect.text = "false ${id},${ble.btDevice.name}"
//                    }
//                }
//            }

        }
    }

    override fun onResume() {
        super.onResume()
        val f1 = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val f2 = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        requireActivity().registerReceiver(myReceiver, f1)
        requireActivity().registerReceiver(myReceiver, f2)

    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(myReceiver)
    }

    private fun getSaveDevice(): ArrayList<BluetoothDevices> {
        val arrayDevices = ArrayList<BluetoothDevices>()

        val pairedDevices: Set<BluetoothDevice>? = BLUETOOTH.bondedDevices
        pairedDevices?.forEach { device ->

            val deviceSave = BluetoothDevices(device.name, device.address, device)
            arrayDevices.add(deviceSave)

        }
        return arrayDevices
    }

    private fun broadcast(): ArrayList<BluetoothDevices> {

        val arrayDevices = ArrayList<BluetoothDevices>()

        myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                if (BluetoothDevice.ACTION_FOUND == intent?.action) {

                    val devise =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    Toast.makeText(context, devise?.name, Toast.LENGTH_LONG).show()

                    val device = devise?.name?.let { BluetoothDevices(it, devise.address, devise) }
                    if (device != null) {
                        arrayDevices.add(device)
                    }
                }
            }
        }
        viewModel.setList(arrayDevices)
        return arrayDevices
    }


}