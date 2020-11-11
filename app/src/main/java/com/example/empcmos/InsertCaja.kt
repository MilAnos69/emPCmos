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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listPlacas = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_caja, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipoPlaca = S_TipoPlaca
        cargarVista()

        /*IB_Imagen.setOnClickListener{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType
        }

        B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var puertosUSB:Number = Integer.parseInt(TB_Puertos.text.toString())
            var ventiladores:Number = Integer.parseInt(TB_Ventiladores.text.toString())
            var tamaño:String = TB_Tamano.text.toString()
            var cantidad:Number = Integer.parseInt(TB_Cantidad.text.toString())
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var estado: Boolean = true
            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(puertosUSB.toString())
                && !TextUtils.isEmpty(ventiladores.toString()) && !TextUtils.isEmpty(valor.toString()) && !TextUtils.isEmpty(tamaño)
                && !TextUtils.isEmpty(cantidad.toString())){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                val db = FirebaseFirestore.getInstance()
                val motherBoard = ECaja(
                    nombre, descripcion, marca, valor, imagen, estado, cantidad, puertosUSB,
                    tamaño, ventiladores, placa
                )
                var userProductsRef = db.collection("Productos").document("Caja").collection("fgMeKpjGmZVXh7Yp2rLp")
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
