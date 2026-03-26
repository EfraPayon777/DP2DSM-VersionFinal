package com.example.dp2dsm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dp2dsm.databinding.ActivityAddDestinoBinding
import com.example.dp2dsm.models.Destino
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddDestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDestinoBinding
    private val database = FirebaseDatabase.getInstance().getReference("destinos")
    // Instancia de FirebaseAuth para obtener el ID del usuario logueado
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            validarDatos()
        }
    }

    private fun validarDatos() {
        val nombre = binding.etNombre.text.toString().trim()
        val pais = binding.spnPais.selectedItem.toString()
        val precioStr = binding.etPrecio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()
        val url = binding.etImageUrl.text.toString().trim()

        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.spnPais.selectedItemPosition == 0) {
            Toast.makeText(this, "Por favor, seleccione un país", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: 0.0
        if (precio <= 0) {
            binding.etPrecio.error = "El precio debe ser mayor a 0"
            return
        }

        if (descripcion.length < 20) {
            binding.etDescripcion.error = "La descripción debe tener al menos 20 caracteres"
            return
        }

        guardar(nombre, pais, precio, descripcion, url)
    }

    private fun guardar(nom: String, pa: String, pre: Double, desc: String, url: String) {
        val id = database.push().key
        // Obtenemos el UID del agente que está creando el destino
        val currentUserId = auth.currentUser?.uid

        if (id != null && currentUserId != null) {
            // Creamos el objeto Destino incluyendo el userId del autor
            val destino = Destino(
                id = id,
                nombre = nom,
                pais = pa,
                precio = pre,
                descripcion = desc,
                imageUrl = url,
                userId = currentUserId
            )

            database.child(id).setValue(destino).addOnSuccessListener {
                Toast.makeText(this, "Destino guardado con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error: No se pudo verificar la sesión del usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
