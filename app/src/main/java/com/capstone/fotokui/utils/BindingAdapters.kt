package com.capstone.fotokui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import com.capstone.fotokui.R
import java.text.NumberFormat
import java.util.*

@BindingAdapter("imageUrl", "placeholder", "error")
fun loadImages(
    circleImageView: ImageView,
    url: String?,
    placeholder: Drawable?,
    error: Drawable?
) {
    circleImageView.load(url) {
        placeholder(placeholder)
        error(error)
    }
}

@BindingAdapter("icon")
fun loadIcon(
    textView: TextView,
    @DrawableRes icon: Int
) {
    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)
}

@BindingAdapter("setupWelcomeMessage")
fun setupWelcomeMessage(textView: TextView, name: String?) {
    val firstName = name?.split(" ")
    firstName?.let {
        textView.text = textView.context.getString(R.string.home_header_welcome_message_format, it[0])
    }
}

@BindingAdapter("priceFormat")
fun priceFormat(textView: TextView, price: Int?) {
    try {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        currencyFormat.maximumFractionDigits = 0
        val formattedPrice = currencyFormat.format(price?.toDouble())
        textView.text = formattedPrice
    } catch (exception: Exception) {
        textView.text = textView.context.getString(R.string.price_format, "0")
    }
}

