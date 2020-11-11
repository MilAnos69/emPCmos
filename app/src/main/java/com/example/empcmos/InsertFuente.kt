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
import com.example.empcmos.ui.Modelo.Partes.EFuente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_fuente.*

/**
 * A simple [Fragment] subclass.
 */
class InsertFuente : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipo: Spinner? = null
    lateinit var listTipos: ArrayList<String>
    private var tipo: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listTipos = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_fuente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipo = S_Tipo
        cargarVista()

        /*B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var voltaje:Number = Integer.parseInt(TB_Voltaje.text.toString())
            var cantidad:Number = Integer.parseInt(TB_Cantidad.text.toString())
            var tamaño:String = TB_Tamano.text.toString()
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var estado: Boolean = true
            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(tamaño)
                && !TextUtils.isEmpty(voltaje.toString()) && !TextUtils.isEmpty(valor.toString())
                && !TextUtils.isEmpty(cantidad.toString())){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                val db = FirebaseFirestore.getInstance()
                val motherBoard = EFuente(
                    nombre, descripcion, marca, valor, imagen, estado, cantidad, tipo,
                    voltaje, tamaño
                )
                var userProductsRef = db.collection("Productos").document("Fuente").collection("fgMeKpjGmZVXh7Yp2rLp")
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
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Fuente-")
        var userProductsRef =  db.collection("Compatibilidad").document("Fuente").collection("Marca")
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
        listTipos.add("-Tipo Fuente-")
        var userProductsRef =  db.collection("Compatibilidad").document("Fuente").collection("Tipo")
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

}
