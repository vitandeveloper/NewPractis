package com.example.contactrandom.model

/** Created by marlon on 27/5/23. **/
data class Location(
    val street: Street?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: String?,
    val coordinates: Coordinates?,
    val timezone: Timezone?
)
