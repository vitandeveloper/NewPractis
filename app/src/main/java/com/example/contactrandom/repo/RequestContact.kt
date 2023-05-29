package com.example.contactrandom.repo

import android.util.Log
import com.example.contactrandom.model.Contact
import com.example.contactrandom.utils.removeRepeat
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.awaitResponse
import javax.inject.Inject

/** Created by marlon on 27/5/23. **/
class RequestContact @Inject constructor(private val apiContact: ApiContact) {

    suspend fun getContactList(numerContact: Int) : ArrayList<Contact> {
        var list = ArrayList<Contact>()
        try {
            val response = apiContact.getListContact(numerContact).awaitResponse()
            if (response.isSuccessful){
                response.body()?.results?.let {
                    list = (it as ArrayList<Contact>).removeRepeat()
                }
            }
        }catch (e: Error){
            Log.e("ERROR getContactList",e.toString())
        }
        return  list
    }
}