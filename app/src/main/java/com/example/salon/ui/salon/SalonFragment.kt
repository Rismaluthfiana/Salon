package com.example.salon.ui.salon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon.MainActivity
import com.example.salon.Preferences
import com.example.salon.databinding.FragmentSalonBinding
import com.example.salon.room.SalonDatabase

class SalonFragment : Fragment() {

    private var _binding: FragmentSalonBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSalonBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = SalonDatabase.getSaveInstance(context)?.salonDao()

        val preferences = context?.let { Preferences(it) }

        binding.hiTxt.text = "Hi, " + preferences?.getUsername()

        binding.salonRv.layoutManager = LinearLayoutManager(context)

        dao?.getAllSalon()?.observe(viewLifecycleOwner) {
            binding.salonRv.adapter = SalonAdapter(it)
        }

        binding.logoutTxt.setOnClickListener {
            preferences?.deleteSession()
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}