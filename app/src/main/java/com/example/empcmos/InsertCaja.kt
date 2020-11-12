package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.empcmos.ui.Modelo.Partes.ECaja
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_caja.*

/**
 * A simple [Fragment] subclass.
 */
class InsertCaja : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipoPlaca: Spinner? = null
    lateinit var listPlacas: ArrayList<String>
    private var placa: String = ""

    private lateinit var interfazComunicarFragmentos: ComunicarFragmentos
    private lateinit var activity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listPlacas = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_caja, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity = context as Activity
            this.interfazComunicarFragmentos = this.activity as ComunicarFragmentos
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipoPlaca = S_TipoPlaca
        cargarVista()

        imageButton.setOnClickListener{
            interfazComunicarFragmentos.galeria()
        }

        B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var puertosUSB:Int
            var ventiladores:Int
            var tamaño:String = TB_Tamano.text.toString()
            var cantidad:Int
            var valor:Int
            var estado: Boolean = true
            var foto:String

            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(TB_Puertos.toString())
                && !TextUtils.isEmpty(TB_Ventiladores.toString()) && !TextUtils.isEmpty(TB_Valor.toString()) && !TextUtils.isEmpty(tamaño)
                && !TextUtils.isEmpty(TB_Cantidad.toString()) && interfazComunicarFragmentos.foto() == true
                && !TextUtils.isEmpty(marca) && !TextUtils.isEmpty(placa)){

                puertosUSB = Integer.parseInt(TB_Puertos.text.toString())
                ventiladores = Integer.parseInt(TB_Ventiladores.text.toString())
                cantidad = Integer.parseInt(TB_Cantidad.text.toString())
                valor = Integer.parseInt(TB_Valor.text.toString())
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()
                foto = interfazComunicarFragmentos.subirImagen("fgMeKpjGmZVXh7Yp2rLp",nombre)

                val db = FirebaseFirestore.getInstance()
                val caja = ECaja(
                    nombre, descripcion, marca, valor, foto, estado, cantidad, puertosUSB,
                    tamaño, ventiladores, placa,"fgMeKpjGmZVXh7Yp2rLp", "Caja"
                )
                var userProductsRef = db.collection("Productos")
                userProductsRef.add(caja).addOnCompleteListener { task ->
                    if (task.isComplete) {
                        Toast.makeText(
                            activity, "Producto creado",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            activity, "Error al crear el producto",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }else{
                Toast.makeText(
                    activity, "Ingrese todos los datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun cargarVista(){
        cargarMarca()
        cargarTipo()
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Caja-")
        var userProductsRef =  db.collection("Compatibilidad").document("Caja").collection("Marca")
        userProductsRef.get().addOnSuccessListener { marcas ->
            for(marca in marcas) {
                listMarcas.add(marca?.getString("Nombre").toString())
                s_marca?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listMarcas) }
            }
        }
        s_marca?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                marca = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarTipo(){
        listPlacas.add("-Tamaño de Placa-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("Tamaño")
        userProductsRef.get().addOnSuccessListener { procesadores ->
            for(procesador in procesadores) {
                listPlacas.add(procesador?.getString("Tamaño").toString())
                s_tipoPlaca?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listPlacas) }
            }
        }
        s_tipoPlaca?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                placa = parent?.getItemAtPosition(position).toString()
            }
        }
    }

}
