package com.example.empcmos.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.EProducto

class AdapterProducto(mContext: Context?, listaProducto: ArrayList<EProducto>) : RecyclerView.Adapter<AdapterProducto.ViewHolder>(), View.OnClickListener  {

    var inglaterr : LayoutInflater

    var Items = ArrayList<EProducto>()

    lateinit var listener : View.OnClickListener

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.Items = listaProducto
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nombre : TextView
        var precio : TextView
        var imagen : ImageView


        init {
            nombre = itemView.findViewById(R.id.txt_Nombre)
            precio = itemView.findViewById(R.id.txt_Precio)
            imagen = itemView.findViewById(R.id.imagenProducto)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View =  inglaterr.inflate(R.layout.lista_productos, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var nombre : String = Items.get(position).tituloProducto
        var precio : String = Items.get(position).precioProducto
        var imagen : Int = Items.get(position).imagenId
        holder.nombre.setText(nombre)
        holder.precio.setText(precio)
        holder.imagen.setImageResource(imagen)
    }

    override fun getItemCount(): Int {
        return Items.size
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