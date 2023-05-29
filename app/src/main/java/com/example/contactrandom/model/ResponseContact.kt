package com.example.contactrandom.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/** Created by marlon on 27/5/23. **/
data class ResponseContact(
    val results : List<Contact>
)
