package com.example.dp2dsm

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dp2dsm.models.Destino
import com.google.firebase.auth.FirebaseAuth

class DestinoAdapter(private val lista: List<Destino>) :
    RecyclerView.Adapter<DestinoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDestino: ImageView = view.findViewById(R.id.ivDestino)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPaisPrecio: TextView = view.findViewById(R.id.tvPaisPrecio)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destino, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val d = lista[position]
        val context = holder.itemView.context

        // Obtener el ID del usuario que tiene la sesión abierta actualmente
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        holder.tvNombre.text = d.nombre
        holder.tvPaisPrecio.text = "${d.pais} - $${d.precio}"
        holder.tvDescripcion.text = d.descripcion

        // Carga de imagen con Glide
        Glide.with(context)
            .load(d.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .centerCrop()
            .into(holder.ivDestino)

        // Lógica de navegación y permisos
        holder.itemView.setOnClickListener {
            // VERIFICACIÓN: Solo el dueño puede entrar a editar
            if (d.userId == currentUserId) {
                val intent = Intent(context, EditDestinoActivity::class.java)
                intent.putExtra("ID", d.id)
                intent.putExtra("NOMBRE", d.nombre)
                intent.putExtra("PAIS", d.pais)
                intent.putExtra("PRECIO", d.precio)
                intent.putExtra("DESC", d.descripcion)
                intent.putExtra("URL", d.imageUrl)
                intent.putExtra("USER_ID", d.userId) // IMPORTANTE: Enviamos el ID del dueño a la siguiente pantalla
                context.startActivity(intent)
            } else {
                // Si no es el dueño, mostramos un mensaje informativo
                Toast.makeText(context, "Solo el autor puede editar este destino", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = lista.size
}