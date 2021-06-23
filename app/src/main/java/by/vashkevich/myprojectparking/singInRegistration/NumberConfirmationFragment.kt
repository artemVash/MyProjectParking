package by.vashkevich.myprojectparking.singInRegistration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.MainViewModel
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.utilits.AUTH
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class NumberConfirmationFragment : Fragment() {

    lateinit var inputSmsCode : EditText
    lateinit var buttonVerificationSmsCode : Button
    lateinit var textNumber : TextView

    val viewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_number_confirmation,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputSmsCode = view.findViewById(R.id.input_code_conf)
        buttonVerificationSmsCode = view.findViewById(R.id.button_verification_sms_code)
        textNumber = view.findViewById(R.id.number_user_text_in_fragment_confirmation)

        viewModel.numberUser.observe(requireActivity()){
            textNumber.text = it
        }

        buttonVerificationSmsCode.setOnClickListener {
            if(inputSmsCode.text.toString().length == 6){
                enterCode()
            }
        }
    }

    private fun enterCode(){
        val code = inputSmsCode.text.toString()

        viewModel.idUser.observe(requireActivity()){

            val credential = PhoneAuthProvider.getCredential(it,code)
            AUTH.signInWithCredential(credential).addOnCompleteListener { task ->

                if (task.isSuccessful){
                    Toast.makeText(context,"Добро пожаловать",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.showRegisterFragment)
                }else{
                    Toast.makeText(context,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }

            }

        }

    }
}