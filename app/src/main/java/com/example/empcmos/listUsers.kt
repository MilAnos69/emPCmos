package com.example.empcmos

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Modelo.EUsuarios
import com.example.empcmos.ui.UsuarioAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list_users.*

class listUsers : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var adapterUsuario : UsuarioAdapter
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
        mostrarDatos()
        return view
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    //ERROR AL CARGAR DATOSS
    fun cargarVista(){
        listUsuarios.add(EUsuarios("nicolas","peralta","hola","nico","123456","12341",true,"1231231"))
        /*var userProductsRef =  db.collection("User")

        userProductsRef.get().addOnSuccessListener { users ->
            for(user in users) {
                Log.d(TAG, user.toObject(EUsuarios::class.java).toString())
                listUsuarios.add(user.toObject(EUsuarios::class.java))
            }
        }*/
    }

    private fun mostrarDatos() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapterUsuario = UsuarioAdapter(context, listUsuarios)
        recyclerView.adapter = this.adapterUsuario
    }
}
