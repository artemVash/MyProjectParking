package by.vashkevich.myprojectparking.singInRegistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.vashkevich.myprojectparking.R
import kotlinx.coroutines.*

class IntroductoryFragment : Fragment() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fargment_introductory,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ioScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.showNumberRegisterFragment)
            }
        }

    }
}