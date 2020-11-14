package com.example.empcmos

import android.view.View
import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EProducto

interface ComunicarFragmentos {
    fun enviarProductos(producto: EProducto, view: View);
    fun galeria()
    fun subirImagen(idUser: String, nombre:String): String
    fun foto(): Boolean
    fun llenarProductos() : ArrayList<EProducto>
}
