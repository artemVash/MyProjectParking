package by.vashkevich.myprojectparking.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.vashkevich.myprojectparking.R
import by.vashkevich.myprojectparking.utilits.*
import com.google.android.material.textfield.TextInputEditText

class ChangeNameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chenge_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebase()

        val newName = view.findViewById<TextInputEditText>(R.id.input_new_name)
        val btnChangeName = view.findViewById<Button>(R.id.btn_change_name)


        val uid = AUTH.currentUser?.uid.toString()

        val name = newName.text

        btnChangeName.setOnClickListener {

            if (newName.text.isNullOrEmpty()) {
                Toast.makeText(context, "поле пустое", Toast.LENGTH_SHORT).show()
            } else {
                DATABASE_REF.child(NODE_USERS).child(uid).child(CHILD_NAME).setValue(name.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show()
                            USER.name = name.toString()
                            findNavController().navigate(R.id.action_changeNameFragment_to_nav_account)
                        }
                    }
            }
        }
    }


}
