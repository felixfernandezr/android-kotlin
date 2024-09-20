package com.example.instadogs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.instadogs.databinding.ItemdogBinding
import com.squareup.picasso.Picasso

class DogViewHolder (view: View):RecyclerView.ViewHolder(view){

    //2 crear variable
    private val binding = ItemdogBinding.bind(view)
    //1 crear metodo
    fun bind(fotoDog:String){
    // 3 obtener imagenes de las url. utilizar Picazzo
        Picasso.get().load(fotoDog).into(binding.ImageDog)
    }
}