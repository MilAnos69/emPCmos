package com.example.empcmos.ui.Modelo

import java.io.Serializable

class EProducto(
    var parte: String,
    var tituloProducto : String,
    var precioProducto : Number,
    var nombreImagen : String,
    var descripcion : String,
    var imagenId : String
):Serializable