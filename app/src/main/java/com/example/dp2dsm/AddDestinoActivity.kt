package com.example.dp2dsm

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.text
import androidx.glance.visibility
import com.example.dp2dsm.databinding.ActivityAddDestinoBinding

class AddDestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDestinoBinding
    private var imageUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                binding.ivPreview.visibility = View.VISIBLE
                binding.ivPreview.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            validarDatos()
        }
    }

    private fun validarDatos() {
        val nombre = binding.etNombre.text.toString().trim()
        val pais = binding.spnPais.selectedItem.toString()
        val precioStr = binding.etPrecio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.spnPais.selectedItemPosition == 0) {
            Toast.makeText(this, "Por favor seleccione un país", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: 0.0
        if (precio <= 0) {
            binding.etPrecio.error = "El precio debe ser mayor a 0"
            return
        }

        if (descripcion.length < 20) {
            binding.etDescripcion.error = "Mínimo 20 caracteres"
            return
        }

        if (imageUri == null) {
            Toast.makeText(this, "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show()
            return
        }

        subirImagenFirebase(nombre, pais, precio, descripcion)
    }

    private fun subirImagenFirebase(
        nombre: String,
        pais: String,
        precio: Double,
        descripcion: String
    ) {
        Toast.makeText(this, "Subiendo destino...", Toast.LENGTH_SHORT).show()
    }
}