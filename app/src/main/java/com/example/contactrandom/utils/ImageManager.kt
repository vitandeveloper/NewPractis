package com.example.contactrandom.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.inject.Inject

/** Created by marlon on 28/5/23. **/
class ImageManager @Inject constructor(val  context: Context){

    suspend fun saveImage(url: String) : Boolean {
        val imageBitmap = downloadImage(url)
        val filename = "${System.currentTimeMillis()}.jpg"

        if (imageBitmap != null ){
            var fos: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)

                fos = withContext(Dispatchers.IO) {
                    FileOutputStream(image)
                }
            }

            return if (fos != null){
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos!!.flush()
                fos!!.close()
                true
            }else {
                false
            }
        }else {
            return false
        }

    }

    private suspend fun downloadImage(urlImage: String): Bitmap? {
        val inputStream = withContext(Dispatchers.IO) {
            URL(urlImage).openStream()
        }

        BitmapFactory.decodeStream(inputStream)
        val url = URL(urlImage)
        val imageData = url.readBytes()
        return try {
            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            bitmap
        }catch (e: Error){
            null
        }
    }
}