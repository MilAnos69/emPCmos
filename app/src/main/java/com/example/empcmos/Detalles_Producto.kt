package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class Detalles_Producto : Fragment() {

    lateinit var nombreDetalle : TextView
    lateinit var imagenDetalle : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        var view = inflater.inflate(R.layout.fragment_detalles__producto, container, false)
        nombreDetalle = view.findViewById(R.id.nombre_producto)
        imagenDetalle = view.findViewById(R.id.imagen_producto)

        var objetoProducto : Bundle? = arguments
        var producto : Producto? = null
        if (objetoProducto!=null){
            producto =objetoProducto.getSerializable("objeto") as Producto
            nombreDetalle.setText(producto.tituloProducto)
            imagenDetalle.setImageResource(producto.imagenId)
        }

        return view
    }
}
