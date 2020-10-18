package com.example.empcmos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.empcmos.ui.Modelo.EUsuarios
import com.example.empcmos.ui.UsuarioAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list_users.*


/**
 * A simple [Fragment] subclass.
 */
class listUsers : Fragment() {

    //private val db = FirebaseFirestore.getInstance()
    //private lateinit var listUsuarios: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_users, container, false)
    }

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUsuarios = LV_Usuarios;
        //this.listView()
    }
    private fun listView() {
        val listItems = arrayListOf<EUsuarios>()

        var userProductsRef =  db.collection("User")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("Users")

        userProductsRef.get().addOnSuccessListener { users ->
            for(user in users) {
                listItems.add(user.toObject(EUsuarios::class.java))
            }

            var adapter = activity?.let { UsuarioAdapter(it, listItems) }
            listUsuarios.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.w("Error", "e: ", exception)
        }
    }*/
}
