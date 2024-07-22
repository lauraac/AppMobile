package com.example.appmobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appmobile.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            // Aquí puedes agregar lógica para el registro, como validaciones y envío a un servidor
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
        }

        binding.backToLoginButton.setOnClickListener {
            finish()
        }
    }
}
