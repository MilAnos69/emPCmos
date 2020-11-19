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
import com.example.empcmos.ui.Adapters.AdapterProductoIndex
import com.example.empcmos.ui.Adapters.AdapterProductoListaVendedor
import com.example.empcmos.ui.Modelo.EProducto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ListarProductosVendedores : Fragment() , ItemSeleccionado {

    lateinit var adapterProducto : AdapterProductoListaVendedor
    lateinit var recyclerView : RecyclerView
    var manage1 : LinearLayoutManager = LinearLayoutManager(context)
    var item1 = ArrayList<EProducto>()

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
        var view = inflater.inflate(R.layout.fragment_listar_productos_vendedor, container, false)
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
        item1.clear()
        item1.addAll(interfaceComunicar.llenarProductosVendedor())
    }

    fun mostrarDatos(){
        manage1.orientation = LinearLayoutManager.VERTICAL
        adapterProducto = AdapterProductoListaVendedor(context, item1, this@ListarProductosVendedores)
        recyclerView.layoutManager = manage1
        recyclerView.adapter = this.adapterProducto
        recyclerView.adapter!!.notifyDataSetChanged()

        adapterProducto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                interfaceComunicar.enviarProductoLista(item1.get(recyclerView.getChildAdapterPosition(v)),v)
            }
        })
    }

    override fun eliminar(posi: Int) {
        interfaceComunicar.eliminarlistavendedor(item1.get(posi))
        item1.removeAt(posi)
        recyclerView!!.adapter!!.notifyDataSetChanged()
    }

}