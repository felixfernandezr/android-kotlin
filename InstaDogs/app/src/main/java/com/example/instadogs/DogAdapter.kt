package com.example.instadogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DogAdapter(val fotoDog:List<String>):RecyclerView.Adapter<DogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.itemdog, parent,false))
    }

    override fun getItemCount(): Int =fotoDog.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = fotoDog[position]
        holder.bind(item)
    }
}