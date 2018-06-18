package com.example.kamil.imageprocessing.service

import android.graphics.Bitmap
import com.example.kamil.imageprocessing.model.FileImageBitmap
import com.example.kamil.imageprocessing.model.HistogramTO
import com.example.kamil.imageprocessing.model.ProcessingImage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * Created by kamil on 20.05.18.
 */

interface RetrofitService {

    @Multipart
    @POST("histogram")
    fun histogram(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/negative")
    fun filterNegative(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/shadesOfGrey")
    fun shadesOfGrey(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/RGBSelection")
    fun rgbSelection(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/brightness")
    fun brightness(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/contrast")
    fun contrast(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("filter/gamma")
    fun gamma(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("rotation")
    fun rotation(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

    @Multipart
    @POST("reflection")
    fun reflection(@Part filePart : MultipartBody.Part, @Part("option") option : RequestBody, @Part("proc") proc : RequestBody): Call<ResponseBody>

}
