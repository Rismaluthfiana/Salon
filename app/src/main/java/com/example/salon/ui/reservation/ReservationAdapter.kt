package com.example.salon.ui.reservation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.salon.DetailSalonActivity
import com.example.salon.R
import com.example.salon.databinding.ReservationItemBinding
import com.example.salon.databinding.SalonItemBinding
import com.example.salon.room.Reservation
import com.example.salon.room.Salon

class ReservationAdapter(val list: List<Reservation>, val deleteClick: DeleteClick) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationAdapter.ViewHolder {
        val binding = ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationAdapter.ViewHolder, position: Int) {
        val reservation = list[position]

        with(holder) {
            Glide.with(itemView).load(reservation.salonImg).placeholder(R.drawable.placeholder).into(binding.salonImg)
            binding.salonNameTxt.text = reservation.salonName
            binding.nameTxt.text = reservation.name
            binding.peopleTxt.text = reservation.people.toString()
            binding.timeTxt.text = reservation.time

            binding.deleteImg.setOnClickListener {
                deleteClick.onClick(reservation.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ReservationItemBinding): RecyclerView.ViewHolder(binding.root)
}