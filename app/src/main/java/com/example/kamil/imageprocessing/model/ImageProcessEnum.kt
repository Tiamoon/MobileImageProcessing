package com.example.kamil.imageprocessing.model

/**
 * Created by kamil on 20.05.18.
 */

enum class ImageProcessEnum (
        val type : String,
        val description: String
) {
    HISTOGRAM("Histogram", "Dodatkowe parametry: brak"),
    NEGATIVE("Negatyw", "Dodatkowe parametry: brak"),
    SHADES_OF_GREY("Odcienie szarości", "Dodatkowe parametry: brak"),
    RGB("RGB", "Wybór składowej RGB: 0-R, 1-G, 2-B"),
    BRIGHTNESS("Jasność", "Stopień rozjaśnienia: (-255;255)"),
    CONTRAST("Kontrast", "Poziom kontrastu: (0,1;10)"),
    GAMMA("Gamma", "Wartość parametru gamma: (0,1;10)"),
    ROTATION("Obrót", "Stopień obrotu: 0-90st, 1-180st, 2-270st"),
    REFLECTION("Odbicie lustrzane", "odbicie: 0-pionowe, 1-poziome")
}