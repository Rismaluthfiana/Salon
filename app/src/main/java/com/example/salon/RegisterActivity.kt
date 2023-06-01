package com.example.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.salon.databinding.ActivityRegisterBinding
import com.example.salon.room.Auth
import com.example.salon.room.SalonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SalonDatabase.getSaveInstance(this)?.authDao()

        binding.registerBtn.setOnClickListener {
            val username = binding.usernameEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Field Not Filled", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    dao?.checkUsername(username).also {
                        if (it == false) {
                            dao?.register(Auth(0,username, password))
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@RegisterActivity, "User exist", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        binding.loginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}