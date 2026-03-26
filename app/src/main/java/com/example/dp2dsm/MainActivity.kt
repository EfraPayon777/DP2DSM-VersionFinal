package com.example.dp2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dp2dsm.databinding.ActivityMainBinding
import com.example.dp2dsm.models.Destino
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var listaDestinos: MutableList<Destino>
    private lateinit var adapter: DestinoAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Inicializar Firebase Auth y verificar sesión activa
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            irAlLogin()
            return
        }

        // 2. Inicializar referencia a la base de datos "destinos"
        database = FirebaseDatabase.getInstance().getReference("destinos")
        listaDestinos = mutableListOf()

        // 3. Configurar la interfaz de usuario (RecyclerView)
        setupRecyclerView()

        // 4. Iniciar la escucha de datos en tiempo real
        obtenerDestinos()

        // 5. Evento para navegar a la pantalla de registro de nuevo destino
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddDestinoActivity::class.java))
        }

        // 6. Lógica para cerrar sesión
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            irAlLogin()
        }
    }

    private fun irAlLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // Limpiamos el stack de actividades para que el usuario no pueda regresar con el botón atrás
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setupRecyclerView() {
        // Inicializamos el adaptador con nuestra lista mutable
        adapter = DestinoAdapter(listaDestinos)
        binding.rvDestinos.layoutManager = LinearLayoutManager(this)
        binding.rvDestinos.adapter = adapter
    }

    private fun obtenerDestinos() {
        // Listener que se activa cada vez que hay un cambio en el nodo "destinos"
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaDestinos.clear()
                for (data in snapshot.children) {
                    // Firebase convierte el JSON automáticamente al modelo Destino
                    val destino = data.getValue(Destino::class.java)
                    destino?.let {
                        // Importante: El ID se toma de la clave del nodo en Firebase
                        it.id = data.key
                        listaDestinos.add(it)
                    }
                }
                // Notificamos al adaptador que los datos han cambiado para refrescar la vista
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al cargar datos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}