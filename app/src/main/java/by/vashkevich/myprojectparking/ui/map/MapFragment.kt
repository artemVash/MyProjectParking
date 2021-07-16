package by.vashkevich.myprojectparking.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.lifecycle.ViewModelProvider
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.MainViewModel
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.utilits.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

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
        permissionLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locatinResult: LocationResult) {
                locatinResult ?: return
                for (location in locatinResult.locations) {
                    mMap.uiSettings.isZoomControlsEnabled = true

                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                location.latitude,
                                location.longitude,
                            )
                        )
                    )
                }
            }
        }
        startLocationUpdates(locationCallback)

        getLocationDenAndWriteToMarker()

        mMap.setOnMarkerClickListener {
            viewModel.setIdDen(it.id)
            BOTTOM_SHEET.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            false
        }

    }

    private fun getRequest() = LocationRequest.create().apply {
        interval = 3000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationProvider.requestLocationUpdates(
            getRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun permissionLocation() {

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                if (it) {
                    Toast.makeText(context, "да", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivity::class.java))
                } else {
                    Toast.makeText(context, "нет", Toast.LENGTH_SHORT).show()
                }

            }

        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION

                )
            } == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(context, "разрешение уже есть", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }

    private fun getLocationDenAndWriteToMarker() {
        ioScope.launch {
            for (x in DEN) {
                val location = x.longitude?.let { x.latitude?.let { it1 -> LatLng(it1, it) } }
                withContext(Dispatchers.Main) {
                    val marker = mMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(x.market)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                    )
                    x.id?.let { ID_AND_ID_DEN.put(marker.id, it) }
                }
            }
        }

    }
}