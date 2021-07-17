package com.example.pharmainc.presentation.dataBinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("upLoadImage")
fun ImageView.upLoadImageAdapter(photo: String) {
    upLoadImage(photo)
}