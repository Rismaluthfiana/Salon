package com.example.salon.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon.databinding.FragmentReservationBinding
import com.example.salon.room.Reservation
import com.example.salon.room.SalonDatabase
import com.example.salon.ui.reservation.DeleteClick
import com.example.salon.ui.reservation.ReservationAdapter
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class ReservationFragment : Fragment(), DeleteClick {

    private var _binding: FragmentReservationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = context?.let { com.example.salon.Preferences(it) }

        binding.reservationRv.layoutManager = LinearLayoutManager(context)

        SalonDatabase.getSaveInstance(context)?.reservationDao()?.getAllReservation(preferences?.getSession())?.observe(viewLifecycleOwner) {
            binding.reservationRv.adapter = ReservationAdapter(it, this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: Int) {
        lifecycleScope.launch {
            SalonDatabase.getSaveInstance(context)?.reservationDao()?.delete(id)
        }
    }
}