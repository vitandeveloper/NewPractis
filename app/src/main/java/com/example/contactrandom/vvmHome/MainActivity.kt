package com.example.contactrandom.vvmHome

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactrandom.R
import com.example.contactrandom.databinding.ActivityMainBinding
import com.example.contactrandom.events.EventReturnBoolean
import com.example.contactrandom.events.EventReturnData
import com.example.contactrandom.model.Contact
import com.example.contactrandom.model.FilterOrder
import com.example.contactrandom.utils.CONTACT_EXTRA
import com.example.contactrandom.utils.scrollLogicPag
import com.example.contactrandom.viewAdapter.AdapterContact
import com.example.contactrandom.vvmContact.ContactActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val  viewModel : MainViewModel by viewModels()
    private val adapContact: AdapterContact = AdapterContact(object : EventReturnData<Contact> {
        override fun callBack(data: Contact) {
            goToActivityContact(data)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabeButtom.setOnClickListener { view ->
           showDialogfilter()
        }

        initAdapterContact()
        initLiveData()
        viewModel.getContact(isFirstData = true)
    }

    private fun initLiveData(){
        viewModel.listContact.observe(this) { it?.let { setListToAdapter(it) } }
        viewModel.isfilterListProcess.observe(this) { it?.let { if (it) isFilterList()}}
        viewModel.textContactNumer.observe(this) { it?.let { setTextContactNumber(it) }}
    }


    private fun initAdapterContact(){
        val layoutManagerRecyclerCurrent = LinearLayoutManager(this)
        layoutManagerRecyclerCurrent.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerContact.layoutManager = layoutManagerRecyclerCurrent
        binding.recyclerContact.setHasFixedSize(true)
        binding.recyclerContact.scrollLogicPag(
            layoutManagerRecyclerCurrent, object : EventReturnBoolean{
            override fun selection(isYes: Boolean) {
                if (isYes) loadMoreContact()
            }
        })
    }

    private fun loadMoreContact(){
        if (adapContact.listItems.last().name != null) {
            adapContact.addItemLoader()
            viewModel.getContact(false)
        }
    }

    private fun setListToAdapter(list: ArrayList<Contact>){
        adapContact.listItems = list
        if (binding.recyclerContact.adapter == null ){
            binding.recyclerContact.adapter = adapContact
            val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_zoom_in)
            binding.recyclerContact.layoutAnimation = animation
        }
    }

    private fun isFilterList(){
        binding.recyclerContact.smoothScrollToPosition(0)
        binding.recyclerContact.adapter?.notifyDataSetChanged()
    }

    private fun  setTextContactNumber(text:String){
        binding.textContactNumber.text = text
    }

    private fun showDialogfilter(){
        val dialog = DialgoFilterContact(
            viewModel.filterOrder,
            object : EventReturnData<FilterOrder>{
                override fun callBack(data: FilterOrder) {
                    viewModel.setFilterOrderContact(data)
                    viewModel.filterOrderContact(true)
                }
            })
        dialog.show(supportFragmentManager,"dialog")
    }

    private fun goToActivityContact(contact: Contact){
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra(CONTACT_EXTRA, Gson().toJson(contact)) //to be serializable

        startActivity(intent)
    }
}