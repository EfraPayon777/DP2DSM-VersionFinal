package com.example.dp2dsm

import android.content.Intent
import android.os.Bundle
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

        // Verificar si el usuario está autenticado
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("destinos")
        listaDestinos = mutableListOf()

        setupRecyclerView()
        obtenerDestinos()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddDestinoActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = DestinoAdapter(listaDestinos)
        binding.rvDestinos.layoutManager = LinearLayoutManager(this)
        binding.rvDestinos.adapter = adapter
    }

    private fun obtenerDestinos() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaDestinos.clear()
                for (data in snapshot.children) {
                    val destino = data.getValue(Destino::class.java)
                    destino?.let {
                        it.id = data.key
                        listaDestinos.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de lectura si es necesario
            }
        })
    }
}