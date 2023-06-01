package com.example.salon.ui.salon

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.salon.DetailSalonActivity
import com.example.salon.R
import com.example.salon.databinding.SalonItemBinding
import com.example.salon.room.Salon

class SalonAdapter(val list: List<Salon>) : RecyclerView.Adapter<SalonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalonAdapter.ViewHolder {
        val binding = SalonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalonAdapter.ViewHolder, position: Int) {
        val salon = list[position]

        with(holder) {
            Glide.with(itemView).load(salon.salonImg).placeholder(R.drawable.placeholder).into(binding.salonImg)
            binding.salonNameTxt.text = salon.salonName
            binding.salonAddressTxt.text = salon.salonAddress
            binding.salonOpenTxt.text = salon.salonOpenTime

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailSalonActivity::class.java)
                intent.putExtra("salon", salon)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: SalonItemBinding): RecyclerView.ViewHolder(binding.root)
}