package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.*

class pago : Fragment() {
    lateinit var fecha : TextView
    lateinit var precio : TextView
    lateinit var codigo : TextView

    lateinit var activity1 : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity1 = context  as Activity
            this.interfaceComunicar = this.activity as ComunicarFragmentos
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_pago, container, false)
        fecha = view.findViewById(R.id.fecha_final)
        precio = view.findViewById(R.id.precio_final)
        codigo = view.findViewById(R.id.codigo_final)


        fecha.setText(SimpleDateFormat("dd-MM-yyyy").format(Date()).toString())
        precio.setText(interfaceComunicar.precio().toString())
        codigo.setText("5431820")


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_pago_to_index)
            }
        })

        return view
    }


}