package com.example.empcmos.ui.Adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.EProducto
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AdapterProducto(mContext: Context?, listaProducto: ArrayList<EProducto>) : RecyclerView.Adapter<AdapterProducto.ViewHolder>(), View.OnClickListener{
    var inglaterr : LayoutInflater

    var ListaItems = ArrayList<EProducto>()

    lateinit var listener : View.OnClickListener

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.ListaItems = listaProducto
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nombre : TextView
        var precio : TextView
        var imagen : ImageView
        var descripcionh : TextView


        init {
            nombre = itemView.findViewById(R.id.txt_Nombre)
            precio = itemView.findViewById(R.id.txt_Precio)
            imagen = itemView.findViewById(R.id.imagenProducto)
            descripcionh = itemView.findViewById(R.id.txt_Descripcion)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View =  inglaterr.inflate(R.layout.lista_productos, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var nombre : String = ListaItems.get(position).tituloProducto
        var precio : Number = ListaItems.get(position).precioProducto
        var imagen : String = ListaItems.get(position).imagenId
        var descripcion : String = ListaItems.get(position).descripcion
        holder.nombre.setText(nombre)
        holder.precio.setText(precio.toString())
        holder.descripcionh.setText(descripcion)
        Picasso.get().load(imagen).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(100,100).centerCrop().into(holder.imagen)
    }

    override fun getItemCount(): Int {
        return ListaItems.size
    }


    fun setOnClickListener(listener : View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        if (listener!=null){
            listener.onClick(v)
        }
    }
}