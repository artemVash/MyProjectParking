package by.vashkevich.myprojectparking.singInRegistration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.MainViewModel
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.utilits.AUTH
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class NumberRegisterFragment : Fragment() {

    lateinit var registerNumberUser : EditText
    lateinit var buttonReceiveSms : Button
    lateinit var mCallback : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    val viewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_number_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerNumberUser = view.findViewById(R.id.input_number_user)
        buttonReceiveSms = view.findViewById(R.id.button_receive_sms)

        AUTH = FirebaseAuth.getInstance()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(context,"Добро пожаловать",Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }else Toast.makeText(context,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(context,p0.toString(),Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {

                viewModel.setId(id)

                findNavController().navigate(R.id.showNumberConfirmationFragment)
            }
        }

        buttonReceiveSms.setOnClickListener {
            sendCode()
        }

    }

    private fun sendCode(){
        if(registerNumberUser.text.toString().isEmpty()){
            Toast.makeText(context,"Введите номер телефона", Toast.LENGTH_SHORT).show()
        }else{
            authUser()
            viewModel.setNumberUser(registerNumberUser.text.toString())
        }
    }

    private fun authUser(){
        val mPhoneNumber = registerNumberUser.text.toString()
        AUTH = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(AUTH)
            .setPhoneNumber(mPhoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(activity as SingInAndRegisterActivity)
            .setCallbacks(mCallback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}