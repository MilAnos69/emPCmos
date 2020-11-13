package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Adapters.AdapterVendedor
import com.example.empcmos.ui.Modelo.EVendedores
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class ListVendedores : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var adapterVendedor : AdapterVendedor
    lateinit var recyclerView : RecyclerView
    lateinit var listVendedores: ArrayList<EVendedores>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list_vendedores, container, false)
        recyclerView = view.findViewById(R.id.LV_Vendedores)
        listVendedores = ArrayList<EVendedores>()
        cargarVista()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    //ERROR AL CARGAR DATOSS
    fun cargarVista(){
        var userProductsRef =  db.collection("User").whereEqualTo("rol","Vendedor")

        userProductsRef.get().addOnSuccessListener { users ->
            for(user in users) {
                listVendedores.add(
                    EVendedores(user?.getString("nombre").toString(),user?.getString("apellido").toString(),
                        user?.getString("correo").toString(),user?.getString("usuario").toString(),
                        user?.getString("password").toString(),user?.getString("telefono").toString(),
                        user?.getString("direccion").toString(),user?.getBoolean("estado"),user?.getString("rol").toString())
                )

            }
            mostrarDatos()
        }
    }

    private fun mostrarDatos() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapterVendedor = AdapterVendedor(context, listVendedores)
        recyclerView.adapter = this.adapterVendedor
    }

}
