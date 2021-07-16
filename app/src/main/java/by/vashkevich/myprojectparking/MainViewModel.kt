package by.vashkevich.myprojectparking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.vashkevich.myprojectparking.model.BluetoothDevices

class MainViewModel : ViewModel() {

    //User number
    private val _numberUser = MutableLiveData<String>()
    var numberUser : LiveData<String> = _numberUser

    fun setNumberUser(number : String){
        _numberUser.value = number
    }

    //Den id
    private val _idDen = MutableLiveData<String>()
    var idDen : LiveData<String> = _idDen

    fun setIdDen(id : String){
        _idDen.value = id
    }

    //User id
    private val _idUser = MutableLiveData<String>()
    var idUser : LiveData<String> = _idUser

    fun setId(id : String){
        _idUser.value = id
    }

    private val _list = MutableLiveData<ArrayList<BluetoothDevices>>()
    var list : LiveData<ArrayList<BluetoothDevices>> = _list

    fun setList(list : ArrayList<BluetoothDevices>){
        _list.value = list
    }




}