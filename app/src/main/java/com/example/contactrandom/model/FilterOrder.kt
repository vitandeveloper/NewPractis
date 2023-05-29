package com.example.contactrandom.model

import com.example.contactrandom.utils.GenderEnum

/** Created by marlon on 28/5/23. **/
data class FilterOrder(
    var filterByGender : String,
    var orderByName: Boolean,
    var orderByAge: Boolean,
    var orderDefaul: Boolean
)
