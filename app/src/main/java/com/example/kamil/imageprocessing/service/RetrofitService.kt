package com.example.kamil.imageprocessing.service

import com.example.kamil.imageprocessing.model.HistogramTO
import com.example.kamil.imageprocessing.model.ProcessingImage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



/**
 * Created by kamil on 20.05.18.
 */

interface RetrofitService {

    @POST("histogram")
    fun histogram(@Body image: ProcessingImage): Call<HistogramTO>

    @POST("filter/negative")
    fun filterNegative(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("filter/shadesOfGrey")
    fun shadesOfGrey(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("filter/RGBSelection")
    fun rgbSelection(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("filter/brightness")
    fun brightness(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("filter/contrast")
    fun contrast(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("filter/gamma")
    fun gamma(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("rotation")
    fun rotation(@Body user: ProcessingImage): Call<ProcessingImage>

    @POST("reflection")
    fun reflection(@Body user: ProcessingImage): Call<ProcessingImage>

}
