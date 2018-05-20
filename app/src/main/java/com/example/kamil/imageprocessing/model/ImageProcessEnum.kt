package com.example.kamil.imageprocessing.model

/**
 * Created by kamil on 20.05.18.
 */

enum class ImageProcessEnum (
        val type : String
) {
    HISTOGRAM("histogram"),
    NEGATIVE("negatyw"),
    SHADES_OF_GREY("odcienie szarości"),
    RGB("rgb"),
    BRIGHTNESS("janość"),
    CONTRAST("kontrast"),
    GAMMA("gamma"),
    ROTATION("obrót"),
    REFLECTION("odbicie lustrzane")
}