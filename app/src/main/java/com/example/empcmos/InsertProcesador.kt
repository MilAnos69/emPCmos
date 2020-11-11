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
import com.example.empcmos.ui.Modelo.Partes.EProcesador
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_procesador.*

/**
 * A simple [Fragment] subclass.
 */
class InsertProcesador : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipo: Spinner? = null
    lateinit var listTipos: ArrayList<String>
    private var tipo: String = ""

    private var s_generacion: Spinner? = null
    lateinit var listGeneracion: ArrayList<String>
    private var generacion: String = ""

    private var s_socket: Spinner? = null
    lateinit var listSocket: ArrayList<String>
    private var socket: String = ""

    private lateinit var interfazComunicarFragmentos: ComunicarFragmentos
    private lateinit var activity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listTipos = ArrayList<String>()
        listGeneracion = ArrayList<String>()
        listSocket = ArrayList<String>()

        return inflater.inflate(R.layout.fragment_insert_procesador, container, false)
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
        s_tipo = S_Tipo
        s_generacion = S_Generacion
        s_socket = S_Socket

        cargarVista()

        /*imageButton.setOnClickListener{
            interfazComunicarFragmentos.galeria()
        }

        B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var voltaje:Number = Integer.parseInt(TB_Voltaje.text.toString())
            var cantidad:Number = Integer.parseInt(TB_Cantidad.text.toString())
            var estado: Boolean = true

            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(voltaje.toString())
                && !TextUtils.isEmpty(valor.toString()) && !TextUtils.isEmpty(cantidad.toString()) && !TextUtils.isEmpty(interfazComunicarFragmentos.foto)){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

               // mStorageRef = FirebaseStorage.getInstance().getReference();
                val motherBoard = EProcesador(
                    nombre, descripcion, marca, generacion, valor, voltaje, tipo, estado, interfazComunicarFragmentos.foto, socket, cantidad,
                    "fgMeKpjGmZVXh7Yp2rLp", "Procesador"
                )
                //val storageRef = Firebase.storage.reference.child("images/"+interfazComunicarFragmentos.foto)
                var userProductsRef = db.collection("Productos")
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
        cargarGeneracion()
        cargarSocket()
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Procesador-")
        var userProductsRef =  db.collection("Compatibilidad").document("Procesador").collection("Marca")
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
                listSocket = ArrayList<String>()
                listTipos = ArrayList<String>()
                listTipos.add("-Tipo Procesador-")
                cargarTipo()
            }
        }
    }

    fun cargarTipo(){
        var userProductsRef =  db.collection("Compatibilidad").document("Procesador").collection("Tipo").whereEqualTo("Procesador", marca)
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
                listSocket = ArrayList<String>()
                listGeneracion = ArrayList<String>()
                cargarSocket()
                cargarGeneracion()
            }
        }
    }

    fun cargarSocket(){
        listSocket.add("-Tipo Socket-")
        var userProductsRef =  db.collection("Compatibilidad").document("Procesador").collection("Socket").whereEqualTo("Procesador", marca)
        userProductsRef.get().addOnSuccessListener { sockets ->
            for(socket in sockets) {
                listSocket.add(socket?.getString("Socket").toString())
                s_socket?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listSocket) }
            }
        }
        s_socket?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                socket = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarGeneracion(){
        listGeneracion.add("-Genecaración Procesador-")
        var userProductsRef =  db.collection("Compatibilidad").document("Procesador").collection("Generacion").whereEqualTo("Procesador", marca)
        userProductsRef.get().addOnSuccessListener { generaciones ->
            for(generacion in generaciones) {
                listGeneracion.add(generacion?.getString("Generacion").toString())
                s_generacion?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listGeneracion) }
            }
        }
        s_generacion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                generacion = parent?.getItemAtPosition(position).toString()
            }
        }
    }


}
