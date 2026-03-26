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

        // 1. Obtener los datos enviados desde el Adaptador
        destinoId = intent.getStringExtra("ID")
        val nombre = intent.getStringExtra("NOMBRE")
        val pais = intent.getStringExtra("PAIS")
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        val desc = intent.getStringExtra("DESC")
        val url = intent.getStringExtra("URL")

        // 2. Llenar los campos con la información actual
        binding.etNombre.setText(nombre)
        binding.etPrecio.setText(precio.toString())
        binding.etDescripcion.setText(desc)
        binding.etImageUrl.setText(url)

        // Configurar el Spinner del país
        val adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises_array, android.R.layout.simple_spinner_item)
        binding.spnPais.setSelection(adapterPaises.getPosition(pais))

        // 3. Botón Actualizar
        binding.btnUpdate.setOnClickListener { validarYActualizar() }

        // 4. Botón Eliminar
        binding.btnDelete.setOnClickListener { mostrarDialogoConfirmacion() }
    }

    private fun validarYActualizar() {
        val id = destinoId ?: return
        val nom = binding.etNombre.text.toString().trim()
        val preStr = binding.etPrecio.text.toString().trim()
        val des = binding.etDescripcion.text.toString().trim()
        val url = binding.etImageUrl.text.toString().trim()
        val pai = binding.spnPais.selectedItem.toString()

        if (nom.isEmpty() || preStr.isEmpty() || des.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (des.length < 20) {
            binding.etDescripcion.error = "Mínimo 20 caracteres"
            return
        }

        val pre = preStr.toDoubleOrNull() ?: 0.0

        // Crear mapa con los nuevos datos
        val updates = mapOf(
            "nombre" to nom,
            "pais" to pai,
            "precio" to pre,
            "descripcion" to des,
            "imageUrl" to url
        )

        // Guardar en Firebase
        database.child(id).updateChildren(updates).addOnSuccessListener {
            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Destino")
            .setMessage("¿Estás seguro de que deseas borrar este destino? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                destinoId?.let {
                    database.child(it).removeValue().addOnSuccessListener {
                        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}