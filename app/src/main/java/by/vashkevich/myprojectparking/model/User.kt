package by.vashkevich.myprojectparking.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id:String? = null,
    var name:String? = null,
    val email:String? = null,
    val number:String? = null
)
