package by.vashkevich.myprojectparking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    //User number
    private val _numberUser = MutableLiveData<String>()
    var numberUser : LiveData<String> = _numberUser

    fun setNumberUser(number : String){
        _numberUser.value = number
    }

    //User id
    private val _idUser = MutableLiveData<String>()
    var idUser : LiveData<String> = _idUser

    fun setId(id : String){
        _idUser.value = id
    }


}