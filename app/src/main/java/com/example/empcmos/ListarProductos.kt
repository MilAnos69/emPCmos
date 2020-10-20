package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.combineTransform

class ListarProductos : Fragment() {

    lateinit var adapterProducto : AdapterProducto
    lateinit var recyclerView : RecyclerView
    var item = ArrayList<Producto>()

    //Referencias para comunicar fragment
    lateinit var activity : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos

    lateinit var navController : NavController


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


        adapterProducto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                var nombre : String = item.get(recyclerView.getChildAdapterPosition(v)).tituloProducto
                interfaceComunicar.enviarProductos(item.get(recyclerView.getChildAdapterPosition(v)))
                navController.navigate(R.id.detalles_Producto)
                Toast.makeText(context, "Selecciono: " + nombre,Toast.LENGTH_SHORT).show()
            }
        })
    }


    //Metodos aparte
}