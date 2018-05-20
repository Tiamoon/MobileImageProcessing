package com.example.kamil.imageprocessing

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kamil.imageprocessing.model.ImageProcessEnum
import com.example.kamil.imageprocessing.model.ProcessingImage
import com.example.kamil.imageprocessing.service.RetrofitService
import com.vansuita.pickimage.bean.PickResult
import kotlinx.android.synthetic.main.activity_main.*
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import retrofit2.Retrofit
import java.util.ArrayList


class MainActivity : AppCompatActivity(), IPickResult {

    @SuppressLint("ShowToast", "UseValueOf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpinner()

        val retrofit = Retrofit.Builder().baseUrl("http://localhost:1247/").build()
        val service = retrofit.create(RetrofitService::class.java)

        checkPermissions()

        selectImage.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

        postImage.setOnClickListener {
            when (spinner.selectedItem.toString()){
                ImageProcessEnum.HISTOGRAM.type -> service.histogram(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.BRIGHTNESS.type -> service.brightness(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.CONTRAST.type -> service.contrast(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.GAMMA.type -> service.gamma(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.NEGATIVE.type -> service.filterNegative(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.REFLECTION.type -> service.reflection(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.RGB.type -> service.reflection(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.ROTATION.type -> service.rotation(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))
                ImageProcessEnum.SHADES_OF_GREY.type -> service.shadesOfGrey(ProcessingImage(currentImage.drawingCache, 0, Integer(procNumber.text.toString())))

                else -> {
                    Toast.makeText(this, "erroro", Toast.LENGTH_SHORT)
                }
            }
        }

    }

    fun initSpinner(){
        val spinnerList = arrayOf("histogram", "negatyw", "odcienie szarości", "rgb", "jasność", "kontrast", "gamma", "obrót", "odbicie lustrzane")

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = spinnerAdapter
    }

    override fun onPickResult(pickResult: PickResult?) {
        if (pickResult != null) {

            currentImage.isDrawingCacheEnabled = true
            currentImage.setImageBitmap(pickResult.bitmap)

            //Image path
            //r.getPath();
        } else {
            Toast.makeText(this, "słaby pickResult", Toast.LENGTH_LONG).show()
        }
    }

    fun checkPermissions() {
        val permissionsList = ArrayList<String>()

        if (!isCameraPermissionGranted()) {
            permissionsList.add(Manifest.permission.CAMERA)
        } else if (!isReadPermissionGranted()){
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (!isWritePermissionGranted()){
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(!permissionsList.isEmpty()){
            ActivityCompat.requestPermissions(this, permissionsList.toTypedArray(),11)
        }
    }

    private fun isCameraPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun isReadPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun isWritePermissionGranted() : Boolean {
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

}
