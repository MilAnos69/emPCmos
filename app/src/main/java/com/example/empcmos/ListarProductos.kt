package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Adapters.AdapterProducto
import com.example.empcmos.ui.Modelo.EProducto

class ListarProductos : Fragment() {

    lateinit var adapterProducto : AdapterProducto
    lateinit var recyclerView : RecyclerView
    var item = ArrayList<EProducto>()

    //Referencias para comunicar fragment

    lateinit var activity : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity = context as Activity
            this.interfaceComunicar = this.activity as ComunicarFragmentos
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_listar_productos, container, false)
        recyclerView = view.findViewById(R.id.listV)
        item = ArrayList<EProducto>()
        cargarVista()
        mostrarDatos()
        return view
    }

    fun mostrarDatos(){
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapterProducto = AdapterProducto(context, item)
        recyclerView.adapter = this.adapterProducto
        Log.e("entro","laputa")

        adapterProducto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                var nombre : String = item.get(recyclerView.getChildAdapterPosition(v)).tituloProducto
                //var fm = interfaceComunicar.enviarProductos(item.get(recyclerView.getChildAdapterPosition(v)))
                //fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, fm)?.addToBackStack(null)?.commit()
            }
        })
    }

    fun cargarVista(){
        item.addAll(interfaceComunicar.llenarProductosFiltrados())
    }
}