package com.example.dp2dsm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.animation.with
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dp2dsm.models.Destino

class DestinoAdapter(private val listaDestinos: List<Destino>) :
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
        val destino = listaDestinos[position]
        holder.tvNombre.text = destino.nombre
        holder.tvPaisPrecio.text = "${destino.pais} - $${destino.precio}"
        holder.tvDescripcion.text = destino.descripcion

        Glide.with(holder.itemView.context)
            .load(destino.imageUrl)
            .into(holder.ivDestino)
    }

    override fun getItemCount() = listaDestinos.size
}