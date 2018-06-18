package com.example.kamil.imageprocessing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kamil.imageprocessing.model.ImageProcessEnum
import com.example.kamil.imageprocessing.service.RetrofitService
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class MainActivity : AppCompatActivity(), IPickResult {

    var bitmap: Bitmap? = null

    @SuppressLint("ShowToast", "UseValueOf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpinner()

        checkPermissions()

        selectImage.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

        postImage.setOnClickListener {
            postImageToGetImage()
        }
        postImage.isEnabled = false


        responseImage.isDrawingCacheEnabled = true
        saveBtn.setOnClickListener {

            Toast.makeText(this, saveFile(responseImage.drawingCache), Toast.LENGTH_SHORT).show()
        }

        saveBtn.isEnabled = false
    }

    private fun postImageToGetImage() {
        val file = File(applicationContext.filesDir, "file.bmp")
        val os = BufferedOutputStream(FileOutputStream(file))
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()

        val filePart = MultipartBody.Part.createFormData("file", file.name, RequestBody.create(MediaType.parse("image/jpeg"), file))

        val retrofit = Retrofit.Builder().baseUrl("http://" + ipAddress.editableText.toString() + ":1247/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        val proc = procNumber.editableText.toString()
        val option = optionField.editableText.toString()

        var requestOption = RequestBody.create(MediaType.parse("multipart/form-data"), option)
        var requestProc = RequestBody.create(MediaType.parse("multipart/form-data"), proc)


        setProgress(true)
        when (spinner.selectedItem.toString()) {
        //                ImageProcessEnum.HISTOGRAM.type -> service.histogram(filePart, ProcessingImage(1, 1))
            ImageProcessEnum.BRIGHTNESS.type -> {
                var callSync = service.brightness(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.CONTRAST.type -> {
                var callSync = service.contrast(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.GAMMA.type -> {
                var callSync = service.gamma(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Toast.makeText(parent.applicationContext, "failure", Toast.LENGTH_LONG).show()
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.NEGATIVE.type -> {
                var callSync = service.filterNegative(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.REFLECTION.type -> {
                var callSync = service.reflection(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.RGB.type -> {
                var callSync = service.rgbSelection(filePart, requestOption, requestProc)
                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }
            ImageProcessEnum.ROTATION.type -> {
                var callSync = service.rotation(filePart, requestOption, requestProc)

                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }

            ImageProcessEnum.SHADES_OF_GREY.type -> {
                var callSync = service.shadesOfGrey(filePart, requestOption, requestProc)

                callSync.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setProgress(false)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        try {
                            var bm = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            responseImage.setImageBitmap(bm)
                            setProgress(false)
                        } catch (e: Exception) {
                            val k = e
                        }
                    }

                })
            }

            else -> {
                Toast.makeText(this, "error 3", Toast.LENGTH_SHORT).show()
                setProgress(false)
            }
        }
    }

    fun initSpinner() {
        val spinnerList = arrayOf("Negatyw", "Odcienie szarości", "RGB", "Jasność", "Kontrast", "Gamma", "Obrót", "Odbicie lustrzane")

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (spinner.selectedItem.toString()) {
                    ImageProcessEnum.CONTRAST.type -> algorithmInfo.text = "Poziom kontrastu: (0,1;10)"
                    ImageProcessEnum.BRIGHTNESS.type -> algorithmInfo.text = "Stopień rozjaśnienia: (-255;255)"
                    ImageProcessEnum.GAMMA.type -> algorithmInfo.text = "Wartość parametru gamma: (0,1;10)"
                    ImageProcessEnum.HISTOGRAM.type -> algorithmInfo.text = "Dodatkowe parametry: brak"
                    ImageProcessEnum.NEGATIVE.type -> algorithmInfo.text = "Dodatkowe parametry: brak"
                    ImageProcessEnum.REFLECTION.type -> algorithmInfo.text = "odbicie: 0-pionowe, 1-poziome"
                    ImageProcessEnum.RGB.type -> algorithmInfo.text = "Wybór składowej RGB: 0-R, 1-G, 2-B"
                    ImageProcessEnum.ROTATION.type -> algorithmInfo.text = "Stopień obrotu: 0-90st, 1-180st, 2-270st"
                    ImageProcessEnum.SHADES_OF_GREY.type -> algorithmInfo.text = "Dodatkowe parametry: brak"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

                /*Do something if nothing selected*/
            }
        }
    }

    override fun onPickResult(pickResult: PickResult?) {
        if (pickResult != null) {

            currentImage.isDrawingCacheEnabled = true
            currentImage.setImageBitmap(pickResult.bitmap)
            bitmap = pickResult.bitmap
            responseImage.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.no_image))
            postImage.isEnabled = true
        } else {
            Toast.makeText(this, "error 4", Toast.LENGTH_LONG).show()
        }
    }

    fun checkPermissions() {
        val permissionsList = ArrayList<String>()

        if (!isCameraPermissionGranted()) {
            permissionsList.add(Manifest.permission.CAMERA)
        } else if (!isReadPermissionGranted()) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (!isWritePermissionGranted()) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!permissionsList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsList.toTypedArray(), 11)
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun isReadPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun isWritePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            11 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    checkPermissions()
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun setProgress(show: Boolean) {
        if (show) {
            selectImage.visibility = View.GONE
            postImage.visibility = View.GONE
            saveBtn.visibility = View.GONE
            requestProgress.visibility = View.VISIBLE
        } else {
            selectImage.visibility = View.VISIBLE
            postImage.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            requestProgress.visibility = View.GONE
            saveBtn.isEnabled = true
        }
    }

    fun saveFile(bm: Bitmap): String {

        var cw = ContextWrapper(application)
        var dir = cw.getDir(Environment.DIRECTORY_DCIM, Context.MODE_WORLD_WRITEABLE)
        var path = File(dir, "image.bmp")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path)
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close();
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }

        return dir.absolutePath
    }
}
