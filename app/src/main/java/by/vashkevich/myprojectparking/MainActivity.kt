package by.vashkevich.myprojectparking

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import by.vashkevich.myprojectparking.model.Den
import by.vashkevich.myprojectparking.model.User
import by.vashkevich.myprojectparking.singInRegistration.SingInAndRegisterActivity
import by.vashkevich.myprojectparking.utilits.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initBLE()
        initFirebase()
        getAllDen()
        getDataUser()
        initBottomSheet()

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                findNavController(R.id.nav_host_fragment).navigate(R.id.showBluetoothConnectFragment)
            }else{
                Toast.makeText(this,"Необходимо включить Bluetooth то бы продолжить",Toast.LENGTH_LONG).show()
            }

        }


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_map, R.id.nav_account, R.id.nav_credit_card, R.id.nav_help
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun init(){
        AUTH = FirebaseAuth.getInstance()
        if (AUTH.currentUser == null) {
            startActivity(Intent(this, SingInAndRegisterActivity::class.java))
        }
    }

    private fun getDataUser() {
        DATABASE_REF.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    USER = snapshot.getValue(User::class.java) ?: User()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun getAllDen() {
        DATABASE_REF.child(NODE_DEN)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (DEN.size > 0) DEN.clear()

                    for (ds in snapshot.children) {
                        val den = ds.getValue(Den::class.java) ?: Den()
                        DEN.add(den)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun initBottomSheet() {

        val textMarker = findViewById<TextView>(R.id.text_market)
        val textStreet = findViewById<TextView>(R.id.text_street)
        val textCloseOpen = findViewById<TextView>(R.id.text_close_open)
        val btlLocation = findViewById<ConstraintLayout>(R.id.location)

        BOTTOM_SHEET = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        BOTTOM_SHEET.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        BOTTOM_SHEET.isFitToContents = false
        BOTTOM_SHEET.halfExpandedRatio = 0.6f
        BOTTOM_SHEET.state = BottomSheetBehavior.STATE_HIDDEN

        viewModel.idDen.observe(this){
            val id = ID_AND_ID_DEN[it]
            for (den in DEN){
                if (den.id == id){

                    textMarker.text = den.market
                    textStreet.text = den.street

                    if(den.state == true) textCloseOpen.text = "Открыто"
                    else textCloseOpen.text = "Закрыто"

                    btlLocation.setOnClickListener {
                        BOTTOM_SHEET.state = BottomSheetBehavior.STATE_HIDDEN
                        enableBt()
                    }
                }
            }
        }
    }
    private fun enableBt() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        resultLauncher.launch(intent)
    }
}