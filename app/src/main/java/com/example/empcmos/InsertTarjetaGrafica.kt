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
import com.example.empcmos.ui.Modelo.Partes.ETarjetaGrafica
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_tarjeta_grafica.*

/**
 * A simple [Fragment] subclass.
 */
class InsertTarjetaGrafica : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipoSalida: Spinner? = null
    lateinit var listSalidas: ArrayList<String>
    private var tipoSalida: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listSalidas = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_tarjeta_grafica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipoSalida = S_TipoSalidas
        cargarVista()

        /*B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var vram:Number = Integer.parseInt(TB_VRam.text.toString())
            var voltaje:Number = Integer.parseInt(TB_Voltaje.text.toString())
            var cantidad:Number = Integer.parseInt(TB_Cantidad.text.toString())
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var estado: Boolean = true
            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(vram.toString())
                && !TextUtils.isEmpty(voltaje.toString()) && !TextUtils.isEmpty(valor.toString()) && !TextUtils.isEmpty(cantidad.toString())){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                val db = FirebaseFirestore.getInstance()
                val motherBoard = ETarjetaGrafica(
                    nombre, descripcion, marca,  valor, voltaje, vram, tipoSalida,
                    estado, imagen, cantidad
                )
                var userProductsRef = db.collection("Productos").document("MotherBoard").collection("fgMeKpjGmZVXh7Yp2rLp")
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
        cargarSalidas()
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Tarjeta Grafica-")
        var userProductsRef =  db.collection("Compatibilidad").document("Tarjeta Grafica").collection("Marca")
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

    fun cargarSalidas(){
        listSalidas.add("-Tipo Salida-")
        var userProductsRef =  db.collection("Compatibilidad").document("Tarjeta Grafica").collection("Tipo Salida")
        userProductsRef.get().addOnSuccessListener { salidas ->
            for(salida in salidas) {
                listSalidas.add(salida?.getString("Salida").toString())
                s_tipoSalida?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listSalidas) }
            }
        }
        s_tipoSalida?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoSalida = parent?.getItemAtPosition(position).toString()
            }
        }
    }

}
