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
                promo = if (id % 4 == 0) 4F else 0.0F
            )
        }
    }

    fun generatePhotographerPromos(): List<Photographer> {
        return generatePhotographers(20).filter { photographer -> photographer.promo > 0 }
    }
}