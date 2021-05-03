package com.hellu.netflixcollection.utils

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun ImageView.show(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun RecyclerView.autoFitColumns(columnWidth: Int) {
    val displayMetrics = this.context.resources.displayMetrics
    val noOfColumns = ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
    this.layoutManager = GridLayoutManager(this.context, noOfColumns)
}