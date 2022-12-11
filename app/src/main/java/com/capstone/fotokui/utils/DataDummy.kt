package com.capstone.fotokui.utils

import com.capstone.fotokui.domain.Photographer
import java.text.DecimalFormat
import kotlin.random.Random

object DataDummy {
    fun generatePhotographers(size: Int = 10): List<Photographer> {
        return (1..size).map { id ->
            Photographer(
                id = "photographer $id",
                photo = "https://cdn.lorem.space/images/face/.cache/150x150/jurica-koletic-7YVZYZeITc8-unsplash.jpg",
                email = "laylael@gmail.com",
                name = "Layla El Faouly",
                experience = id.toFloat(),
                yearOrMonthExperience = if (id % 3 == 0) "Tahun" else "Bulan",
                price = 50F,
                description= "You can configure your app to draw its content behind the system bars. Together, the status bar and the navigation bar are called the system bars.",
                photos = emptyList(),
                isFavorite = id % 4 == 0 || id % 3 == 0,
                promo = if (id % 4 == 0) 4F else 0.0F
            )
        }.shuffled()
    }

    fun generateFavoritePhotographer(size: Int = 20): List<Photographer> {
        return generatePhotographers(size).filter { photographer -> photographer.isFavorite }
    }

    fun generateDummyDistance(): String {
        val randomDistance = Random.nextDouble(from = 0.2, until = 2.4)
        return DecimalFormat("#.#").format(randomDistance)
    }
}