package by.vashkevich.myprojectparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.singInRegistration.SingInAndRegisterActivity


class IntroductoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introductory)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,SingInAndRegisterActivity::class.java)
            startActivity(intent)
            finish()
        },2500)
    }
}