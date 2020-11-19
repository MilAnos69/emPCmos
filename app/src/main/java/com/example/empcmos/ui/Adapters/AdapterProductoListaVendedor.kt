package com.example.empcmos.ui.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ItemSeleccionado
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.EProducto
import com.squareup.picasso.Picasso

class AdapterProductoListaVendedor(mContext: Context?, listaProducto: ArrayList<EProducto>, private val itemSeleccionado: ItemSeleccionado) : RecyclerView.Adapter<AdapterProductoListaVendedor.ViewHolder>() , View.OnClickListener{
    var inglaterr : LayoutInflater

    var ListaItems = ArrayList<EProducto>()

    lateinit var listener : View.OnClickListener

    var con : Context? = mContext

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.ListaItems = listaProducto
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nombre : TextView
        var precio : TextView
        var imagen : ImageView
        var descripcionh : TextView
        var boton : Button

        init {
            nombre = itemView.findViewById(R.id.txt_Nombre)
            precio = itemView.findViewById(R.id.txt_Precio)
            imagen = itemView.findViewById(R.id.imagenProducto)
            descripcionh = itemView.findViewById(R.id.txt_Descripcion)
            boton = itemView.findViewById(R.id.b_eliminar)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View =  inglaterr.inflate(R.layout.lista_productos_listarpc, parent, false)
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
        holder.boton.setOnClickListener {
            itemSeleccionado.eliminar(position)
            Toast.makeText(con, "Producto Eliminado", Toast.LENGTH_SHORT).show()
        }
        Picasso.get().load(imagen).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(100,100).centerCrop().into(holder.imagen)
    }

    override fun getItemCount(): Int {
        return ListaItems.size
    }

    override fun onClick(p0: View?) {
        if (listener!=null){
            listener.onClick(p0)
        }
    }

    fun setOnClickListener(listener : View.OnClickListener){
        this.listener = listener
    }
}