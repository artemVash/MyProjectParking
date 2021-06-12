package by.vashkevich.myprojectparking.singInRegistration

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jetbrains.annotations.NotNull

class LoginAdapter(
    activity:FragmentActivity,
    private val x:Int
) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int{
        return NUMBER
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            SING_IN->{
                val fragmentSingIn = SingInFragment()
                fragmentSingIn
            }
            REGISTER->{
                val fragmentRegister = RegisterFragment()
                fragmentRegister
            }
            else ->{
                createFragment(position)
            }
        }
    }

    companion object{
        internal const val SING_IN = 0
        internal const val REGISTER = 1
        internal const val NUMBER = 2
        internal const val LIMIT = 3
    }
}

