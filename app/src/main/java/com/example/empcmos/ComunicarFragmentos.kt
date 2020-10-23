package com.example.empcmos

import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EProducto

interface ComunicarFragmentos {
    fun enviarProductos(producto: EProducto) : Fragment;
}
