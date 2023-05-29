package com.example.contactrandom.model


/** Created by marlon on 28/5/23. **/
data class Contact(
    val gender: String?,
    val name : Name?,
    val location: Location?,
    val email: String?,
    val login: Login?,
    val phone: String?,
    val cell: String?,
    val picture: Picture?,
    val dob : Dob?
)
