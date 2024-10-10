package com.example.proyecto41

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto41.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //1. Declaramos la variable binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //2. Inicializamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //3. Configuramos el botón para Iniciar Sesión
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                //Aqui se entiende que venería la lógica de autenticación
                //Para este ejemplo, asumiremos que cualquier usuario y contraseña es correcto
                val intent = Intent(this, SecondActvity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            }

        }

    }
}