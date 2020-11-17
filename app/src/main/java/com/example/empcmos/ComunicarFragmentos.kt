package com.example.empcmos

import android.view.View
import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EProducto

interface ComunicarFragmentos {
    fun enviarProductos(producto: EProducto, view: View);
    fun enviarProductoLista(producto: EProducto, view: View);
    fun galeria()
    fun subirImagen(idUser: String, nombre:String): String
    fun foto(): Boolean
    fun llenarProductos() : ArrayList<EProducto>
    fun listaProductosFiltrado(parteEscogida: String, view: View)
    fun llenarProductosFiltrados() : ArrayList<EProducto>
    fun back()

    fun llenarDatosPC (parteEscogida : String)

    fun llenarListaFinal(string: String) : ArrayList<EProducto>
    fun agregarListaFinal(producto: EProducto)
    fun eliminarLista(producto: EProducto)

    fun listafinalFiltro(string: String) : Boolean
}
