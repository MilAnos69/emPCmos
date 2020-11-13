package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.empcmos.ui.Modelo.EProducto

/**
 * A simple [Fragment] subclass.
 */
class Detalles_Productos : Fragment() {
    lateinit var nombreDetalle : TextView
    lateinit var imagenDetalle : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        var view = inflater.inflate(R.layout.fragment_detalles_producto, container, false)
        nombreDetalle = view.findViewById(R.id.nombre_producto)
        imagenDetalle = view.findViewById(R.id.imagen_producto)

        var objetoProducto : Bundle? = arguments
        var producto : EProducto? = null
        if (objetoProducto!=null){
            producto =objetoProducto.getSerializable("objeto") as EProducto
            nombreDetalle.setText(producto.tituloProducto)
            imagenDetalle.setImageResource(producto.imagenId)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_detalles_Productos_to_index)
            }
        })

        return view
    }
}
