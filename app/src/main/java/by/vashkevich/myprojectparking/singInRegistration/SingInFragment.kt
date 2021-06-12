package by.vashkevich.myprojectparking.singInRegistration

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SingInFragment : Fragment() {

    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sing_in,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.btn_test)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

        btn.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }


    private fun permissionLocation(){

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){

            if(it){

            }else{
                Toast.makeText(context,"нет",Toast.LENGTH_SHORT).show()
            }

        }

        when{
            ContextCompat.checkSelfPermission(
            context as Activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION

        ) == PackageManager.PERMISSION_GRANTED ->{
                Toast.makeText(context,"разрешение уже есть",Toast.LENGTH_SHORT).show()
        }
            else ->{
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }




//    private fun permissionLocation(){
//
//        val task = fusedLocationProvider.lastLocation
//
//        if (context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_FINE_LOCATION) }
//            != PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
//        }
//        task.addOnCompleteListener {
//            if (it != null) {
//                startActivity(Intent(context, MainActivity::class.java))
//            }
//        }
//
//    }
}