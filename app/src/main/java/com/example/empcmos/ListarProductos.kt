package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListarProductos : Fragment() {

    lateinit var adapterProducto : AdapterProducto
    lateinit var recyclerView : RecyclerView
    var item = ArrayList<Producto>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_listar_productos, container, false)
        recyclerView = view.findViewById(R.id.listV)
        item = ArrayList<Producto>()
        cargarVista()
        mostrarDatos()
        return view
    }

    fun cargarVista(){
        item.add(Producto("camara","100.0", R.drawable.logo_empcmos))
        item.add(Producto("camara2","150.0", R.drawable.logo_empcmos))
    }

    fun mostrarDatos(){
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapterProducto = AdapterProducto(context, item)
        recyclerView.adapter = this.adapterProducto

        adapterProducto.setOnClickListener(View.OnClickListener {
            fun onClick(v: View) {
                var nombre : String = item.get(recyclerView.getChildAdapterPosition(v)).tituloProducto
                Toast.makeText(context, "Selecciono: " + nombre,Toast.LENGTH_SHORT).show()
            }
        })
    }
}