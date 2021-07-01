package by.vashkevich.myprojectparking.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.utilits.DEN
import by.vashkevich.myprojectparking.utilits.ID_AND_ID_DEN
import by.vashkevich.myprojectparking.utilits.USER

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testName = view.findViewById<TextView>(R.id.test_name_user)
        val testDen = view.findViewById<TextView>(R.id.test_den)

        testName.text = USER.name

        testDen.text = ID_AND_ID_DEN.size.toString()

    }
}