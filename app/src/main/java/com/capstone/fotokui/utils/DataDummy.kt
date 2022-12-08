package com.capstone.fotokui.utils

import com.capstone.fotokui.domain.Photographer

object DataDummy {
    fun generatePhotographers(size: Int = 10): List<Photographer> {
        return (1..size).map { id ->
            Photographer(
                id = "photographer $id",
                photo = "https://cdn.lorem.space/images/face/.cache/150x150/jurica-koletic-7YVZYZeITc8-unsplash.jpg",
                name = "Layla El Faouly",
                experience = "1.$id",
                yearOrMonthExperience = if (id % 3 == 0) "Tahun" else "Bulan",
                price = 50,
                isFavorite = id % 4 == 0 || id % 3 == 0,
                promo = if (id % 4 == 0) 4F else 0.0F
            )
        }.shuffled()
    }

    fun generatePhotographerPromos(size: Int = 20): List<Photographer> {
        return generatePhotographers(size).filter { photographer -> photographer.promo > 0 }
    }

    fun generateFavoritePhotographer(size: Int = 20): List<Photographer> {
        return generatePhotographers(size).filter { photographer -> photographer.isFavorite }
    }
}