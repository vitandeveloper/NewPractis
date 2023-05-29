package com.example.contactrandom.vvmHome

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.contactrandom.R
import com.example.contactrandom.databinding.DialogFilterBinding
import com.example.contactrandom.events.EventReturnData
import com.example.contactrandom.model.FilterOrder
import com.example.contactrandom.utils.GenderEnum

/** Created by marlon on 28/5/23. **/
class DialgoFilterContact(
    private var filterOrder: FilterOrder,
    private val event: EventReturnData<FilterOrder>
) : DialogFragment()
{

    private lateinit var binding: DialogFilterBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFilterBinding.inflate(layoutInflater)
        initView()
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun initView(){

        binding.checkLastName.isChecked = filterOrder.orderByName
        binding.checkLastName.setOnCheckedChangeListener { buttonView, isChecked ->
            filterOrder.orderByName = isChecked
        }

        binding.checkAge.isChecked = filterOrder.orderByAge
        binding.checkAge.setOnCheckedChangeListener { buttonView, isChecked ->
            filterOrder.orderByAge = isChecked
        }

        when(filterOrder.filterByGender){
            GenderEnum.FEMALE.nameE -> {
                binding.radioGroupFilter.check(R.id.radioFemale)
            }
            GenderEnum.MALE.nameE -> {
                binding.radioGroupFilter.check(R.id.radioMale)
            }
            GenderEnum.NONE.nameE -> {
                binding.radioGroupFilter.check(R.id.radioNone)
            }
        }

        binding.radioGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioFemale -> {
                    filterOrder.filterByGender = GenderEnum.FEMALE.nameE
                }
                R.id.radioMale -> {
                    filterOrder.filterByGender = GenderEnum.MALE.nameE
                }
                R.id.radioNone -> {
                    filterOrder.filterByGender = GenderEnum.NONE.nameE
                }
            }
        }

        binding.buttonProcess.setOnClickListener {
            dismiss()
            filterOrder.orderDefaul = (filterOrder.filterByGender == GenderEnum.NONE.nameE
                    && !filterOrder.orderByName && !filterOrder.orderByAge)

            event.callBack(filterOrder)
        }
    }

}