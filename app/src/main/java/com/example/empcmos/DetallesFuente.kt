package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.empcmos.ui.Modelo.EProducto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_detalles_fuente.*

/**
 * A simple [Fragment] subclass.
 */
class DetallesFuente : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var imagen : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var objetoProducto : Bundle? = arguments
        var producto : EProducto? = null
        if (objetoProducto!=null){
            producto =objetoProducto.getSerializable("objeto") as EProducto
            imagen = producto.nombreImagen
        }
        llenarDatos(imagen)
        return inflater.inflate(R.layout.fragment_detalles_fuente, container, false)
    }

    fun llenarDatos(imagen: String){
        var userProductsRef =  db.collection("Productos").whereEqualTo("imagen", imagen)

        userProductsRef.get().addOnSuccessListener { users ->
            for (user in users) {
                Tb_Descripcion.setText(user.getString("descripcion").toString())
                Tb_Marca.setText(user.getString("marca").toString())
                TB_Voltaje.setText(user.get("voltaje").toString())
                TB_Tamano.setText(user.getString("tamaño").toString())
            }
        }
    }
}
