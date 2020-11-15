package com.example.empcmos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_insert_producto.*

/**
 * A simple [Fragment] subclass.
 */
class fragment_insert_producto : Fragment(){

    private var spinner: Spinner ? = null
    val insertMotherBoard = InsertMotherBoard() ;

    private var itemList = arrayOf(
        "MotherBoard",
        "Ram",
        "Procesador",
        "Refrigeración",
        "Tarjeta Grafica",
        "Disco duro",
        "SSD M.2",
        "Fuente",
        "Caja")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner = TipoProducto
        spinner?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, itemList) }
        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                val motherBoard = InsertMotherBoard()
                val ram = InsertRam()
                val procesador = InsertProcesador()
                val refrigeracion = InsertRefrigeracion()
                val tarjetaGrafica = InsertTarjetaGrafica()
                val discoDuro = InsertDiscoDuro()
                val ssdM2 = InsertSSDM2()
                val fuente = InsertFuente()
                val caja = DetallesCaja()
                when (type){
                    "MotherBoard" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, motherBoard)?.addToBackStack(null)?.commit()
                    "Ram" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, ram)?.addToBackStack(null)?.commit()
                    "Procesador" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, procesador)?.addToBackStack(null)?.commit()
                    "Refrigeración" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, refrigeracion)?.addToBackStack(null)?.commit()
                    "Tarjeta Grafica" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, tarjetaGrafica)?.addToBackStack(null)?.commit()
                    "Disco duro" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, discoDuro)?.addToBackStack(null)?.commit()
                    "SSD M.2" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, ssdM2)?.addToBackStack(null)?.commit()
                    "Fuente" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, fuente)?.addToBackStack(null)?.commit()
                    "Caja" -> fragmentManager?.beginTransaction()?.replace(R.id.container_fragments, caja)?.addToBackStack(null)?.commit()

                }
            }

        }
    }

}
