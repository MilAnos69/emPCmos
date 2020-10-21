package com.example.empcmos

import com.example.empcmos.ui.Modelo.EProducto

interface ComunicarFragmentos {
    fun enviarProductos(producto: EProducto);
}
