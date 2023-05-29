package com.example.contactrandom.utils

import android.util.Log
import com.example.contactrandom.model.Contact
import com.example.contactrandom.model.Location
import com.example.contactrandom.model.Name

/** Created by marlon on 28/5/23. **/
fun Name.fullName(): String {
    return this.title.plus(". ").plus(this.first).plus(" ").plus(this.last)
}

fun Location.fullAddress() : String{
    return this.street?.name.plus("" ).plus(this.city).plus(" ").plus(this.state).
            plus(this.postcode)
}

fun  ArrayList<Contact>.removeRepeat(): ArrayList<Contact> {
    val listDef : ArrayList<Contact> = ArrayList()
    this.forEach {
        if (!listDef.contains(it))
            listDef.add(it)
        else
            Log.e("Contacto repetido ==>",it.toString())
    }
    return listDef
}

fun  ArrayList<Contact>.sorteByLastName(): ArrayList<Contact> {
    val list = this.sortedWith(compareBy { it.name?.last })
    return ArrayList(list)
}

fun  ArrayList<Contact>.sorteByAge(): ArrayList<Contact> {
    val list = this.sortedBy { it.dob?.age }
    return ArrayList(list)
}

fun  ArrayList<Contact>.filterByGender(genderEnumE: String): ArrayList<Contact> {
    return this.filter { it.gender == genderEnumE } as ArrayList<Contact>
}