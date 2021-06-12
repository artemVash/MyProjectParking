package by.vashkevich.myprojectparking

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_map, R.id.nav_account, R.id.nav_credit_card,R.id.nav_help), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        permissionLocation()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun permissionLocation(){

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){

            if(it){
                Toast.makeText(this,"да",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"нет",Toast.LENGTH_SHORT).show()
            }

        }

        when{
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION

            ) == PackageManager.PERMISSION_GRANTED ->{
                Toast.makeText(this,"разрешение уже есть",Toast.LENGTH_SHORT).show()
            }
            else ->{
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }
}