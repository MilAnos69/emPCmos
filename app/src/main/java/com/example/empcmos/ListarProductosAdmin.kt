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
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Adapters.AdapterProducto
import com.example.empcmos.ui.Adapters.AdapterProductoAdmin
import com.example.empcmos.ui.Adapters.AdapterProductoIndex
import com.example.empcmos.ui.Modelo.EProducto

class ListarProductosAdmin : Fragment() {

    lateinit var adapterProducto : AdapterProductoAdmin
    lateinit var recyclerView : RecyclerView
    var manage1 : LinearLayoutManager = LinearLayoutManager(context)
    var item1 = ArrayList<EProducto>()
    var item2 = ArrayList<EProducto>()

    //Referencias para comunicar fragment

    lateinit var activity1 : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity1 = context as Activity
            this.interfaceComunicar = this.activity as ComunicarFragmentos
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_listar_productos_admin, container, false)
        recyclerView = view.findViewById(R.id.listV)
        cargarVista()
        mostrarDatos()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    fun cargarVista(){
        item1.addAll(interfaceComunicar.llenarProductos())
        item2.addAll(interfaceComunicar.llenarProductos())
    }

    fun mostrarDatos(){
        manage1.orientation = LinearLayoutManager.VERTICAL
        adapterProducto = AdapterProductoAdmin(context, item1)
        recyclerView.layoutManager = manage1
        recyclerView.adapter = this.adapterProducto
        recyclerView.adapter!!.notifyDataSetChanged()
    }
}