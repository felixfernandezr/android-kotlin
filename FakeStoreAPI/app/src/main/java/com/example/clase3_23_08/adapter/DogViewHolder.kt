package com.example.clase3_23_08.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.clase3_23_08.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind(image:String){
        //  Llamo a la librería, le paso el contexto de la view (pantalla) donde lo quiero, la url, y le digo dónde lo va a pintar
        Picasso.with(itemView.context).load(image).into(binding.ivDog)
    }
}