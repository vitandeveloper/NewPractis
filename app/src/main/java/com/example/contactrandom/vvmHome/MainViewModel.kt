package com.example.contactrandom.vvmHome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactrandom.model.Contact
import com.example.contactrandom.model.FilterOrder
import com.example.contactrandom.repo.RequestContact
import com.example.contactrandom.utils.GenderEnum
import com.example.contactrandom.utils.filterByGender
import com.example.contactrandom.utils.sorteByAge
import com.example.contactrandom.utils.sorteByLastName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by marlon on 28/5/23. **/
@HiltViewModel
class MainViewModel @Inject constructor(private val requestContact: RequestContact) : ViewModel() {
    var listContact : MutableLiveData<ArrayList<Contact>> = MutableLiveData()
    var textContactNumer : MutableLiveData<String> = MutableLiveData()
    var isfilterListProcess : MutableLiveData<Boolean> = MutableLiveData()

    var filterOrder: FilterOrder = FilterOrder(
        filterByGender = GenderEnum.NONE.nameE, orderByAge = false,
        orderByName = false, orderDefaul = true
    )

    private var defaulList = ArrayList<Contact>()

    fun getContact(isFirstData: Boolean){
        viewModelScope.launch(Dispatchers.Default) {
            val numerber  = if(isFirstData) 20 else 25
            val list = requestContact.getContactList(numerber)
            defaulList.addAll(list)
            filterOrderContact(false)
        }
    }

    fun setFilterOrderContact(filterOrder: FilterOrder){ this.filterOrder = filterOrder }

    fun filterOrderContact(isFilterByUser:Boolean){
        if (filterOrder.orderDefaul){
            setList(defaulList)
        }else{
            processFilter()
            isfilterListProcess.postValue(isFilterByUser)
        }
    }

    private fun processFilter(){
        var listF = ArrayList<Contact>(defaulList)
        if (filterOrder.orderByName)
            listF = listF.sorteByLastName()

        if (filterOrder.orderByAge)
            listF = listF.sorteByAge()

        if (filterOrder.filterByGender != GenderEnum.NONE.nameE)
            listF = listF.filterByGender(filterOrder.filterByGender)

        setList(listF)
    }

    private fun setList(list: ArrayList<Contact>){
        listContact.postValue(list)
        textContactNumer.postValue("${list.size}/${defaulList.size}")
    }


    override fun onCleared() {
        super.onCleared()
    }
}