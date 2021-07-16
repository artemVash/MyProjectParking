package by.vashkevich.myprojectparking.singInRegistration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.vashkevich.myprojectparking.MainActivity
import by.vashkevich.myprojectparking.MainViewModel
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.model.User
import by.vashkevich.myprojectparking.utilits.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RegisterFragment : Fragment() {

    lateinit var nameUser: TextInputEditText
    lateinit var emailUser: TextInputEditText
    lateinit var btnToMain: Button

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebase()

        nameUser = view.findViewById(R.id.input_user_name)
        emailUser = view.findViewById(R.id.input_user_email)
        btnToMain = view.findViewById(R.id.btn_transition_to_main)
        val uid = AUTH.currentUser?.uid.toString()

        btnToMain.setOnClickListener {

            viewModel.numberUser.observe(requireActivity()) {
                writingToDatabase(uid, nameUser.text.toString(), emailUser.text.toString(), it)
            }
        }
    }

    private fun writingToDatabase(uid:String,name:String,email:String,number:String) {

        val user = User(uid,name,email,number)

        DATABASE_REF.child(NODE_USERS).child(uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Добро пожаловать", Toast.LENGTH_LONG).show()
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
    }
}