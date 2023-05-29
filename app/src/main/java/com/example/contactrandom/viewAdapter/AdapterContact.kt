package com.example.contactrandom.viewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactrandom.R
import com.example.contactrandom.databinding.AdapterContactBinding
import com.example.contactrandom.events.EventReturnData
import com.example.contactrandom.model.Contact
import com.example.contactrandom.utils.fullName

/** Created by marlon on 28/5/23. **/
class AdapterContact (private val eventTouch: EventReturnData<Contact>) : BaseAdapter<Contact>(){
    override fun getItemViewTypeIsLoader(position: Int): Boolean {
        return listItems[position].gender.isNullOrEmpty()
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = AdapterContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun addItemLoader() {
        val newResult = listItems  + Contact(
            gender = null, name = null, location = null,email = null,
            login = null,phone = null, cell = null,picture = null,
            dob = null
        )
        listItems = newResult
    }

    override fun removeItemLoader() {
        if (listItems.isNotEmpty()) {
            if (listItems.last().name == null) {
                listItems = listItems.dropLast(1)
            }
        }
    }

    override fun addNewItemsType(list: MutableList<Contact>) {
        val newResult = listItems + list
        listItems = newResult
    }

    override fun diffUtilAreItemsTheSame(old: Contact, new: Contact): Boolean {
        return old.login?.uuid == new.login?.uuid
    }

    override fun setBindViewHolder(holder: RecyclerView.ViewHolder, data: Contact) {
        if (holder is ItemViewHolder) {
            holder.initViewItem(data,eventTouch)
        }
    }


    private class ItemViewHolder(val binding: AdapterContactBinding) : RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context

        fun initViewItem(contact: Contact,eventTouch: EventReturnData<Contact>) {
            binding.textName.text = contact.name?.fullName()
            binding.textCell.text = contact.cell

            Glide
                .with(context)
                .load(contact.picture?.large)
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.img_usuario)
                .into(binding.imageContact)


           changeColorItem()

            binding.root.setOnClickListener {
                eventTouch.callBack(contact)
            }
        }

        private fun changeColorItem(){
            if (layoutPosition % 2 == 0)
                binding.containerAdapter.setBackgroundColor(context.getColor(R.color.gray_adapter))
            else
                binding.containerAdapter.setBackgroundColor(context.getColor(R.color.white))
        }
    }

}