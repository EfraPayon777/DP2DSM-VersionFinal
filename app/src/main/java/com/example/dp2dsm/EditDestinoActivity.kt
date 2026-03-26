package com.example.dp2dsm

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dp2dsm.databinding.ActivityEditDestinoBinding
import com.google.firebase.database.FirebaseDatabase

class EditDestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDestinoBinding
    private val database = FirebaseDatabase.getInstance().getReference("destinos")
    private var destinoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        destinoId = intent.getStringExtra("ID")
        binding.etNombre.setText(intent.getStringExtra("NOMBRE"))
        binding.etPrecio.setText(intent.getDoubleExtra("PRECIO", 0.0).toString())
        binding.etDescripcion.setText(intent.getStringExtra("DESC"))
        binding.etImageUrl.setText(intent.getStringExtra("URL"))

        val paisActual = intent.getStringExtra("PAIS")
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.paises_array,
            android.R.layout.simple_spinner_item
        )
        binding.spnPais.setSelection(adapter.getPosition(paisActual))

        binding.btnUpdate.setOnClickListener { validarYActualizar() }
        binding.btnDelete.setOnClickListener { confirmarEliminacion() }
    }

    private fun validarYActualizar() {
        val nombre = binding.etNombre.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val desc = binding.etDescripcion.text.toString().trim()
        val url = binding.etImageUrl.text.toString().trim()
        val pais = binding.spnPais.selectedItem.toString()

        if (nombre.isEmpty() || precioStr.isEmpty() || desc.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: 0.0
        if (precio <= 0) {
            binding.etPrecio.error = "Precio debe ser mayor a 0"
            return
        }

        if (desc.length < 20) {
            binding.etDescripcion.error = "Mínimo 20 caracteres"
            return
        }

        val map = mapOf(
            "nombre" to nombre,
            "pais" to pais,
            "precio" to precio,
            "descripcion" to desc,
            "imageUrl" to url
        )

        destinoId?.let {
            database.child(it).updateChildren(map).addOnSuccessListener {
                Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun confirmarEliminacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar")
            .setMessage("¿Desea eliminar este destino?")
            .setPositiveButton("Eliminar") { _, _ ->
                destinoId?.let {
                    database.child(it).removeValue().addOnSuccessListener {
                        finish()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}