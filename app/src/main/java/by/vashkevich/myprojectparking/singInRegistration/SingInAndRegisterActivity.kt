package by.vashkevich.myprojectparking.singInRegistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import by.vashkevich.myprojectparking.R
import com.google.android.material.tabs.TabLayoutMediator

class SingInAndRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_and_register)

    }
}