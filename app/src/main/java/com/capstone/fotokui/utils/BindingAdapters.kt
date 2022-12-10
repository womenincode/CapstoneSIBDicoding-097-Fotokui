package com.capstone.fotokui.utils

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.capstone.fotokui.R
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imageUrl", "placeholder", "error")
fun loadImages(
    circleImageView: CircleImageView,
    url: String?,
    placeholder: Drawable?,
    error: Drawable?
) {
    circleImageView.load(url) {
        placeholder(placeholder)
        crossfade(true)
        error(error)
    }
}

@BindingAdapter("icon")
fun loadIcon(
    textView: TextView,
    icon: Drawable?
) {
    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)
}

@BindingAdapter("setupWelcomeMessage")
fun setupWelcomeMessage(textView: TextView, name: String?) {
    val firstName = name?.split(" ")
    firstName?.let {
        textView.text = textView.context.getString(R.string.home_header_welcome_message_format, it[0])
    }
}

