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

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_and_register)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)

        init()

    }

    private fun init() {
        val loginAdapter = LoginAdapter(this, tabLayout.tabCount)
        viewPager.apply {
            adapter = loginAdapter
            offscreenPageLimit = LoginAdapter.LIMIT as Int
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectedTabPosition = position
                    

                }
            })
            currentItem = selectedTabPosition

        }


        TabLayoutMediator(tabLayout, viewPager) { currentTab, currentPosition ->
            currentTab.text = when (currentPosition) {
                LoginAdapter.SING_IN -> "Вход"
                LoginAdapter.REGISTER -> "Регистрация"
                else -> "?"

            }
        }.attach()
    }
    companion object {
        private var selectedTabPosition = 0
    }
}