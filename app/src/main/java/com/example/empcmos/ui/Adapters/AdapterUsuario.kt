package com.example.empcmos.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.EUsuarios

class AdapterUsuario(mContext: Context?, listaUsuario: ArrayList<EUsuarios>) : RecyclerView.Adapter<com.example.empcmos.ui.Adapters.AdapterUsuario.ViewHolderU>(), View.OnClickListener  {
    var inglaterr : LayoutInflater

    var Items = ArrayList<EUsuarios>()

    lateinit var listener : View.OnClickListener

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.Items = listaUsuario
    }

    class ViewHolderU(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var usuario : TextView
        var nombre : TextView


        init {
            usuario = itemView.findViewById(R.id.userName)
            nombre = itemView.findViewById(R.id.name)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderU {
        var view : View =  inglaterr.inflate(R.layout.usuario, parent, false)
        view.setOnClickListener(this)
        return ViewHolderU(view)
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

    override fun onBindViewHolder(holder: ViewHolderU, position: Int) {
        var usuario : String = Items.get(position).usuario
        var nombre : String = Items.get(position).nombre  + " "+ Items.get(position).apellido
        holder.usuario.setText(usuario)
        holder.nombre.setText(nombre)
    }
}