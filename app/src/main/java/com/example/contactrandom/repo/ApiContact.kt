package com.example.contactrandom.repo

import com.example.contactrandom.model.ResponseContact
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.*


/** Created by marlon on 27/5/23. **/
interface ApiContact {
    @GET("/")
    fun getListContact(@Query ("results") results: Int): Call<ResponseContact>
}