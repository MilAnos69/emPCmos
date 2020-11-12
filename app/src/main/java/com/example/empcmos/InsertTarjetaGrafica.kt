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

    private lateinit var interfazComunicarFragmentos: ComunicarFragmentos
    private lateinit var activity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listSalidas = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_tarjeta_grafica, container, false)
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
        s_tipoSalida = S_TipoSalidas
        cargarVista()

        imageButton.setOnClickListener{
            interfazComunicarFragmentos.galeria()
        }

        B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var vram:Int
            var voltaje:Int
            var cantidad:Int
            var valor:Int
            var estado: Boolean = true
            var foto:String

            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(TB_VRam.toString())
                && !TextUtils.isEmpty(TB_Voltaje.toString()) && !TextUtils.isEmpty(TB_Valor.toString()) && !TextUtils.isEmpty(TB_Cantidad.toString())
                && interfazComunicarFragmentos.foto() == true && !TextUtils.isEmpty(marca)
                && !TextUtils.isEmpty(tipoSalida)){

                vram = Integer.parseInt(TB_VRam.text.toString())
                voltaje = Integer.parseInt(TB_Voltaje.text.toString())
                cantidad = Integer.parseInt(TB_Cantidad.text.toString())
                valor = Integer.parseInt(TB_Valor.text.toString())
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()
                foto = interfazComunicarFragmentos.subirImagen("fgMeKpjGmZVXh7Yp2rLp",nombre)

                val db = FirebaseFirestore.getInstance()
                val tarjetaGrafica = ETarjetaGrafica(
                    nombre, descripcion, marca,  valor, voltaje, vram, tipoSalida,
                    estado, foto, cantidad, "fgMeKpjGmZVXh7Yp2rLp", "Tarjeta Grafica"
                )
                var userProductsRef = db.collection("Productos")
                userProductsRef.add(tarjetaGrafica).addOnCompleteListener { task ->
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
            }else {
                Toast.makeText(
                    activity, "Ingrese todos los datos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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
