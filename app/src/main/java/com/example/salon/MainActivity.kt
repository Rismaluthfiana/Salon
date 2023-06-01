package com.example.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.salon.databinding.ActivityMainBinding
import com.example.salon.room.SalonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SalonDatabase.getSaveInstance(this)?.authDao()
        val preferences = Preferences(this)

        if (preferences.getSession() != -0) {
            val intent = Intent(this@MainActivity, BottomNavActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val username = binding.usernameEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Field Not Filled", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    dao?.checkUsername(username).also {
                        if (it == true) {
                            val auth = dao?.login(username)
                            if (auth?.password == password) {
                                preferences.saveSession(auth.id, username)
                                val intent = Intent(this@MainActivity, BottomNavActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(this@MainActivity, "Wrong password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@MainActivity, "User not exist", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}