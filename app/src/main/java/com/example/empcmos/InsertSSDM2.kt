package com.example.empcmos

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
import com.example.empcmos.ui.Modelo.Partes.ESSD_2
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_s_s_d_m2.*

/**
 * A simple [Fragment] subclass.
 */
class InsertSSDM2 : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipo: Spinner? = null
    lateinit var listTipos: ArrayList<String>
    private var tipo: String = ""

    private var s_tamaño: Spinner? = null
    lateinit var listTamaño: ArrayList<String>
    private var tamaño: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listTipos = ArrayList<String>()
        listTamaño = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_s_s_d_m2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipo = S_Tipo
        s_tamaño = S_Tamano
        cargarVista()

        /*B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var voltaje:Number = Integer.parseInt(TB_Voltaje.text.toString())
            var cantidad:Number = Integer.parseInt(TB_Cantidad.text.toString())
            var capacidad:Number = Integer.parseInt(TB_Capacidad.text.toString())
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var estado: Boolean = true
            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(capacidad.toString())
                && !TextUtils.isEmpty(voltaje.toString()) && !TextUtils.isEmpty(valor.toString())
                && !TextUtils.isEmpty(cantidad.toString())){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                val db = FirebaseFirestore.getInstance()
                val motherBoard = ESSD_2(
                    nombre, descripcion, marca, valor, imagen, estado, cantidad, tipo,
                    voltaje, capacidad, tamaño
                )
                var userProductsRef = db.collection("Productos").document("Disco SSD M.2").collection("fgMeKpjGmZVXh7Yp2rLp")
                userProductsRef.add(motherBoard).addOnCompleteListener { task ->
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
            }

        }*/
    }

    fun cargarVista(){
        cargarMarca()
        cargarTipo()
        cargarTamañoM2()
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Disco Duro-")
        var userProductsRef =  db.collection("Compatibilidad").document("Disco Duro").collection("Marca")
        userProductsRef.get().addOnSuccessListener { marcas ->
            for(marca in marcas) {
                listMarcas.add(marca?.getString("Nombre").toString())
                s_marca?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listMarcas) }
            }
        }
        s_marca?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                marca = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarTipo(){
        listTipos.add("-Tipo Disco Duro-")
        var userProductsRef =  db.collection("Compatibilidad").document("Disco Duro").collection("TipoM2")
        userProductsRef.get().addOnSuccessListener { tipos ->
            for(tipo in tipos) {
                listTipos.add(tipo?.getString("Tipo").toString())
                s_tipo?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listTipos) }
            }
        }
        s_tipo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipo = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarTamañoM2(){
        listTamaño.add("-Tamaño SSD M2-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("TamañoM2")
        userProductsRef.get().addOnSuccessListener { tamañosM2 ->
            for(tamañoM2 in tamañosM2) {
                listTamaño.add(tamañoM2?.getString("Tamaño").toString())
                s_tamaño?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listTamaño) }
            }
        }
        s_tamaño?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tamaño = parent?.getItemAtPosition(position).toString()
            }
        }
    }
}
