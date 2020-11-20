package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.empcmos.R.id.I_Caja
import com.example.empcmos.ui.Modelo.EProducto
import com.squareup.picasso.Picasso

class Compra : Fragment() {

    var listafinala = ArrayList<EProducto>()

    lateinit var procesaror : ImageView
    lateinit var ram1 : ImageView
    lateinit var grafica : ImageView
    lateinit var discod : ImageView
    lateinit var dicoss : ImageView
    lateinit var cooler : ImageView
    lateinit var case : ImageView
    lateinit var mother : ImageView
    lateinit var fut : ImageView

    lateinit var costoTotal : TextView

    lateinit var botonComprar : Button

    var discoCan : Int = 0
    var discoCans : Int = 0


    lateinit var activity1 : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity1 = context  as Activity
            this.interfaceComunicar = this.activity as ComunicarFragmentos
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_compra, container, false)
        procesaror = view.findViewById(R.id.I_Procesador)
        ram1 = view.findViewById(R.id.I_Ram)
        grafica = view.findViewById(R.id.I_TarjetaGrafica)
        discod = view.findViewById(R.id.I_DiscoDuro)
        dicoss = view.findViewById(R.id.I_SSDM2)
        cooler = view.findViewById(R.id.I_Cooler)
        case = view.findViewById(I_Caja)
        mother = view.findViewById(R.id.I_Mother)
        fut = view.findViewById(R.id.I_Fuente)

        costoTotal = view.findViewById(R.id.textView13)

        botonComprar = view.findViewById(R.id.Comprar)


        listafinala.addAll(interfaceComunicar.listatotal())

        llenarimg("Procesador",procesaror)
        llenarimg("Mother Board",mother)
        llenarimg("Fuente",fut)
        llenarimg("Ram",ram1)
        llenarimg("Disco Duro",discod)
        llenarimg("SSD M2",dicoss)
        llenarimg("Tarjeta Grafica",grafica)
        llenarimg("Refrigeracion",cooler)
        llenarimg("Caja",case)
        preciototal()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_compra_to_lista_pc)
            }
        })

        botonComprar.setOnClickListener {
            view.findNavController().navigate(R.id.action_compra_to_pago)
            interfaceComunicar.limpiarlista()
        }

        return view
    }


    fun llenarimg(string: String, imageView: ImageView){
        listafinala.forEach {
            it.imagenId
            if (it.parte==string && imageView != case && imageView != dicoss && imageView != dicoss){
                Picasso.get().load(it.imagenId).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(131,129).centerCrop().into(imageView)
            }
            if(it.parte==string && imageView == case){
                Picasso.get().load(it.imagenId).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(417,455).centerCrop().into(imageView)
            }
            if(it.parte==string && imageView == discod && discoCan == 0){
                Picasso.get().load(it.imagenId).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(131,129).centerCrop().into(imageView)
                discoCan = 1
            }
            if(it.parte==string && imageView == dicoss && discoCans == 0){
                Picasso.get().load(it.imagenId).placeholder(R.drawable.progress_animation).error(R.drawable.logo_empcmos).resize(131,129).centerCrop().into(imageView)
                discoCans = 1
            }
        }
    }

    fun preciototal(){
        var acum : Int = 0
        listafinala.forEach {
            it.precioProducto
            acum = acum + it.precioProducto.toInt()
        }
        costoTotal.setText(acum.toString())
        interfaceComunicar.setprecio(acum)
    }

    override fun onStart() {
        super.onStart()
    }
}
