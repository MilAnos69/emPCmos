package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.empcmos.ui.Modelo.EProducto
import com.squareup.picasso.Picasso

class Detalles_Productos : Fragment() {
    lateinit var nombreDetalle : TextView
    lateinit var precio : TextView
    lateinit var imagenDetalle : ImageView
    lateinit var bt : Button

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
        precio = view.findViewById(R.id.precio_producto)
        bt = view.findViewById(R.id.btnlistapc)

        var objetoProducto : Bundle? = arguments
        var producto : EProducto? = null
        if (objetoProducto!=null){
            producto =objetoProducto.getSerializable("objeto") as EProducto
            nombreDetalle.setText(producto.tituloProducto)
            precio.setText(producto.precioProducto.toString())
            Picasso.get().load(producto.imagenId).into(imagenDetalle)
        }

        bt.setOnClickListener {
            view.findNavController().navigate(R.id.action_detalles_Productos_to_lista_pc)
        }


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_detalles_Productos_to_index)
            }
        })
        var bundleEnvio: Bundle=Bundle()
        bundleEnvio.putSerializable("objeto", producto)

        val motherBoard = DetallesMotherBoard()
        motherBoard.arguments = bundleEnvio
        val ram = DetallesRam()
        ram.arguments = bundleEnvio
        val procesador = DetallesProcesador()
        procesador.arguments = bundleEnvio
        val refrigeracion = DetallesRefrigeracion()
        refrigeracion.arguments = bundleEnvio
        val tarjetaGrafica = DetallesTarjetaGrafica()
        tarjetaGrafica.arguments = bundleEnvio
        val discoDuro = DetallesDiscoDuro()
        discoDuro.arguments = bundleEnvio
        val ssdM2 = DetallesSSDM2()
        ssdM2.arguments = bundleEnvio
        val fuente = DetallesFuente()
        fuente.arguments = bundleEnvio
        val caja = DetallesCaja()
        caja.arguments = bundleEnvio
        when (producto?.parte){
            "Mother Board" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, motherBoard)?.addToBackStack(null)?.commit()
            "Ram" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, ram)?.addToBackStack(null)?.commit()
            "Procesador" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, procesador)?.addToBackStack(null)?.commit()
            "Refrigeracion" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, refrigeracion)?.addToBackStack(null)?.commit()
            "Tarjeta Grafica" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, tarjetaGrafica)?.addToBackStack(null)?.commit()
            "Disco Duro" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, discoDuro)?.addToBackStack(null)?.commit()
            "SSD M2" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, ssdM2)?.addToBackStack(null)?.commit()
            "Fuente" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, fuente)?.addToBackStack(null)?.commit()
            "Caja" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, caja)?.addToBackStack(null)?.commit()

        }
        return view
        }
    }
