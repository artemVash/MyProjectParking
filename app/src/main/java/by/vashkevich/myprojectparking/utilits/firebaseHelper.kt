package by.vashkevich.myprojectparking.utilits

import by.vashkevich.myprojectparking.model.Den
import by.vashkevich.myprojectparking.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH : FirebaseAuth
lateinit var DATABASE_REF : DatabaseReference
lateinit var UID:String
lateinit var USER:User
lateinit var DEN:ArrayList<Den>
lateinit var ID_AND_ID_DEN:HashMap<String,String>

const val NODE_USERS = "users"
const val NODE_DEN = "ben"
const val CHILD_ID = "id"
const val CHILD_NAME = "name"
const val CHILD_EMAIL = "email"
const val CHILD_NUMBER = "number"


fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    DATABASE_REF = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = User()
    DEN = ArrayList()
    ID_AND_ID_DEN = HashMap()

}