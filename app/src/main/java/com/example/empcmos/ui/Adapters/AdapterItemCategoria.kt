package com.example.empcmos.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.Partes.ECategoria

class AdapterItemCategoria (mContext: Context?, listaCategoria: ArrayList<ECategoria>) : RecyclerView.Adapter<AdapterItemCategoria.ViewHolder>(), View.OnClickListener{
    var inglaterr : LayoutInflater

    var Items = ArrayList<ECategoria>()

    lateinit var listener : View.OnClickListener

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.Items = listaCategoria
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nombre : TextView
        var imagen : ImageView


        init {
            nombre = itemView.findViewById(R.id.nombre_categoria)
            imagen = itemView.findViewById(R.id.imageView2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View =  inglaterr.inflate(R.layout.item_categoria, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var nombre : String = Items.get(position).nombreC
        var imagen : Int = Items.get(position).imagenid
        holder.nombre.setText(nombre)
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