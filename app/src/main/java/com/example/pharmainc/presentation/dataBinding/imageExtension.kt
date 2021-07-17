package com.example.pharmainc.presentation.dataBinding

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.upLoadImage(photo: String) {
    Picasso.get().load(photo).into(this)
}