package com.example.contactrandom.vvmContact

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.contactrandom.R
import com.example.contactrandom.databinding.ActivityContactBinding
import com.example.contactrandom.model.Contact
import com.example.contactrandom.utils.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


/** Created by marlon on 28/5/23. **/
@AndroidEntryPoint
class ContactActivity : AppCompatActivity() {
    @Inject
    lateinit var permissionManager: PermissionManager
    @Inject
    lateinit var imageManager: ImageManager

    private lateinit var binding: ActivityContactBinding
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(CONTACT_EXTRA)?.let {
            val contact = Gson().fromJson(it,Contact::class.java) as Contact
            this.contact = contact
            putDataContact()
        }


        initView()
    }

    private fun initView() {
        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonDownloadImg.setOnClickListener {
            if (permissionManager.havePermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))){
                dowloadImage()
            }else{
                permissionManager.requestPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    CODE_PERMISSION_FILE
                )
            }
        }

        binding.buttonSendEmail.setOnClickListener {
            sendEmail()
        }

        binding.buttonCallPhone.setOnClickListener {
            if (permissionManager.havePermission(arrayOf(Manifest.permission.CALL_PHONE))){
                makeCall()
            }else{
                permissionManager.requestPermission(
                    this,
                    Manifest.permission.CALL_PHONE,
                    CODE_PERMISSION_CALL
                )
            }
        }
    }

    private fun sendEmail(){
        val mailto = contact.email.plus("?cc=").plus("malronvian@gmal").
            plus("&subject=").plus("Prueba tec").plus("&body=").
            plus("hola mundo :)")

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${mailto}"))
        startActivity(Intent.createChooser(intent, "Selecciona el cliente de correo"))
    }

    private fun makeCall(){
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:${contact.phone}")
        startActivity(phoneIntent)
    }

    private fun dowloadImage(){
        lifecycleScope.launch (Dispatchers.IO){

            if (imageManager.saveImage(contact.picture?.large?: ""))
                lifecycleScope.launch {
                    Toast.makeText(
                        this@ContactActivity,
                        getText(R.string.dowload_succces),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            else
                lifecycleScope.launch {
                    Toast.makeText(
                        this@ContactActivity,
                        getString(R.string.dowload_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun putDataContact(){
        Glide
            .with(this)
            .load(contact.picture?.large)
            .fitCenter()
            .circleCrop()
            .placeholder(R.drawable.img_usuario)
            .into(binding.imageContact)

        binding.textUserName.text = contact.login?.username
        binding.textFullName.text = contact.name?.fullName()
        binding.textEmail.text = contact.email
        binding.textAddress.text = contact.location?.fullAddress()
        binding.textPhone.text = contact.phone
        binding.textGender.text = contact.gender
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.e("RESULTADO PERMISO",grantResults.toString())
        when(requestCode){
            CODE_PERMISSION_CALL -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                )
                    makeCall()
            }
            CODE_PERMISSION_FILE ->{
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                )
                dowloadImage()

            }
        }
    }
}