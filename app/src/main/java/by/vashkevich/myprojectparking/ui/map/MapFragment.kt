package by.vashkevich.myprojectparking.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.R.id.showBottomSheetFragment2
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        val locationCallback = object  : LocationCallback(){
            override fun onLocationResult(locatinResult: LocationResult) {
                locatinResult ?: return
                for (location in locatinResult.locations){
                    mMap.uiSettings.isZoomControlsEnabled = true

                    mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude,location.longitude)))
                }
            }
        }
        startLocationUpdates(locationCallback)

        val minsk = LatLng(53.864395, 27.448683)
        mMap.addMarker(MarkerOptions()
            .position(minsk)
            .title("Marker in Minsk")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))

        val minsk2 = LatLng(53.864648, 27.451846)
        mMap.addMarker(MarkerOptions()
            .position(minsk2)
            .title("Marker in Minsk")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))

        val minsk3 = LatLng(53.862674, 27.446701)
        mMap.addMarker(MarkerOptions()
            .position(minsk3)
            .title("Marker in Minsk")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))


        mMap.setOnMarkerClickListener {
            findNavController().navigate(showBottomSheetFragment2)
            false
        }

    }

    private fun getRequest() = LocationRequest.create().apply {
        interval = 3000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationCallback:LocationCallback){
        fusedLocationProvider.requestLocationUpdates(getRequest(),locationCallback, Looper.getMainLooper())
    }
}