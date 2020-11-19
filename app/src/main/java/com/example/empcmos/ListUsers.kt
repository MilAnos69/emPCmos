package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Modelo.EUsuarios
import com.example.empcmos.ui.Adapters.AdapterUsuario
import com.google.firebase.firestore.FirebaseFirestore

class ListUsers : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var adapterUsuario : AdapterUsuario
    lateinit var recyclerView : RecyclerView
    lateinit var listUsuarios: ArrayList<EUsuarios>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list_users, container, false)
        recyclerView = view.findViewById(R.id.LV_Usuarios)
        listUsuarios = ArrayList<EUsuarios>()
        cargarVista()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
        return view
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    //ERROR AL CARGAR DATOSS
    fun cargarVista(){
       var userProductsRef =  db.collection("User").whereEqualTo("rol","Usuario").whereEqualTo("estado",true)

        userProductsRef.get().addOnSuccessListener { users ->
            for(user in users) {
                listUsuarios.add(EUsuarios(user?.getString("nombre").toString(),user?.getString("apellido").toString(),
                    user?.getString("correo").toString(),user?.getString("usuario").toString(),
                    user?.getString("password").toString(),user?.getString("telefono").toString(),
                    user?.getBoolean("estado"),user?.getString("rol").toString()))
            }
            mostrarDatos()
        }
    }

    private fun mostrarDatos() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapterUsuario = AdapterUsuario(context, listUsuarios)
        recyclerView.adapter = this.adapterUsuario
    }
}
