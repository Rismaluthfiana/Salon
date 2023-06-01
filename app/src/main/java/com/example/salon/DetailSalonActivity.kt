package com.example.salon

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.salon.databinding.ActivityDetailSalonBinding
import com.example.salon.room.Reservation
import com.example.salon.room.Salon
import com.example.salon.room.SalonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailSalonActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailSalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = Preferences(this)
        val id = preferences.getSession()

        val salon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("salon", Salon::class.java)
        } else {
            intent.getSerializableExtra("salon") as Salon
        }

        if (salon != null) {
            Glide.with(this).load(salon.salonImg).placeholder(R.drawable.placeholder).into(binding.salonImg)
            binding.salonNameTxt.text = salon.salonName
            binding.salonAddressTxt.text = salon.salonAddress
            binding.salonOpenTxt.text = salon.salonOpenTime
            binding.salonDescTxt.text = salon.salonDesc
        }

        binding.dateEdt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in"))
                binding.dateEdt.setText(simpleDateFormat.format(calendar.time))

            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }

        binding.reservationBtn.setOnClickListener {
            val name = binding.nameEdt.text.toString()
            val people = binding.peopleEdt.text.toString()
            val date = binding.dateEdt.text.toString()

            if (name.isBlank() || people.isBlank() || date.isBlank()) {
                Toast.makeText(this, "Field not filled", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val reservation = salon?.salonName?.let { it1 ->
                        salon.salonImg.let { it2 ->
                            Reservation(0, id, name, date,
                                it1, people.toInt(), it2
                            )
                        }
                    }
                    if (reservation != null) {
                        SalonDatabase.getSaveInstance(this@DetailSalonActivity)?.reservationDao()?.addReservation(reservation)
                        finish()
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(this@DetailSalonActivity, "Reservasi Berhasil", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}