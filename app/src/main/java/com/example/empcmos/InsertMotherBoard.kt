package com.example.empcmos

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.empcmos.ui.Modelo.Partes.EMotherBoard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_mother_board.*

/**
 * A simple [Fragment] subclass.
 */
class InsertMotherBoard : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var s_marca: Spinner? = null
    lateinit var listMarcas: ArrayList<String>
    private var marca: String = ""

    private var s_tipoProcesador: Spinner? = null
    lateinit var listProcesador: ArrayList<String>
    private var procesador: String = "Intel"

    private var s_socket: Spinner? = null
    lateinit var listSocket: ArrayList<String>
    private var socket: String = ""

    private var s_tamaño: Spinner? = null
    lateinit var listTamaño: ArrayList<String>
    private var tamaño: String = ""

    private var s_tamañoM2: Spinner? = null
    lateinit var listTamañoM2: ArrayList<String>
    private var tamañoM2: String = ""

    private var s_tipoRam: Spinner? = null
    lateinit var listRam: ArrayList<String>
    private var ram: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMarcas = ArrayList<String>()
        listProcesador = ArrayList<String>()
        listSocket = ArrayList<String>()
        listTamaño = ArrayList<String>()
        listTamañoM2 = ArrayList<String>()
        listRam = ArrayList<String>()
        return inflater.inflate(R.layout.fragment_insert_mother_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_marca = S_Marca
        s_tipoProcesador = S_TipoProcesador
        s_socket = S_Socket
        s_tamaño = S_Tamano
        s_tamañoM2 = S_TamañoM2
        s_tipoRam = S_TipoRam
        cargarVista()

        /*IB_Imagen.setOnClickListener{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType
        }

        B_Agregar.setOnClickListener {
            var nombre:String = Tb_Nombre.text.toString()
            var descripcion:String = Tb_Descripcion.text.toString()
            var chipset:String = TB_Chipset.text.toString()
            var voltaje:Number = Integer.parseInt(TB_Voltaje.text.toString())
            var puertoM2:Number = Integer.parseInt(TB_PuertosM_2.text.toString())
            var puertosDiscoDuro:Number = Integer.parseInt(TB_PuertosDiscoDuro.text.toString())
            var cantidad:Number = Integer.parseInt(Tb_Cantidad.text.toString())
            var valor:Number = Integer.parseInt(TB_Valor.text.toString())
            var estado: Boolean = true
            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(chipset)
                && !TextUtils.isEmpty(voltaje.toString()) && !TextUtils.isEmpty(puertoM2.toString()) && !TextUtils.isEmpty(puertosDiscoDuro.toString())
                && !TextUtils.isEmpty(valor.toString()) && !TextUtils.isEmpty(cantidad.toString())){
                Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                val db = FirebaseFirestore.getInstance()
                val motherBoard = EMotherBoard(
                    nombre, descripcion, marca, procesador, valor, socket, chipset, voltaje, tamaño,
                    puertoM2, puertosDiscoDuro, tamañoM2, estado, ram, imagen, cantidad
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
        cargarProcesador()
        cargarSocket()
        cargarTamaño()
        cargarTamañoM2()
        cargarRam()
    }

    fun cargarMarca(){
        listMarcas.add("-Marca Mother Board-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("Marca")
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

    fun cargarProcesador(){
        listProcesador.add("-Marca Procesador-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("TipoProcesador")
        userProductsRef.get().addOnSuccessListener { procesadores ->
            for(procesador in procesadores) {
                listProcesador.add(procesador?.getString("Procesador").toString())
                s_tipoProcesador?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listProcesador) }
            }
        }
        s_tipoProcesador?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                procesador = parent?.getItemAtPosition(position).toString()
                listSocket = ArrayList<String>()
                cargarSocket()
            }
        }
    }

    fun cargarSocket(){
        listSocket.add("-Tipo Socket-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("Socket").whereEqualTo("Procesador", procesador)
        userProductsRef.get().addOnSuccessListener { sockets ->
            for(socket in sockets) {
                listSocket.add(socket?.getString("Socket").toString())
                s_socket?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listSocket) }
            }
        }
        s_socket?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                socket = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarTamaño(){
        listTamaño.add("-Tamaño Mother Board-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("Tamaño")
        userProductsRef.get().addOnSuccessListener { tamaños ->
            for(tamaño in tamaños) {
                listTamaño.add(tamaño?.getString("Tamaño").toString())
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

    fun cargarTamañoM2(){
        listTamañoM2.add("-Tamaño SSD M2-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("TamañoM2")
        userProductsRef.get().addOnSuccessListener { tamañosM2 ->
            for(tamañoM2 in tamañosM2) {
                listTamañoM2.add(tamañoM2?.getString("Tamaño").toString())
                s_tamañoM2?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listTamañoM2) }
            }
        }
        s_tamañoM2?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tamañoM2 = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    fun cargarRam(){
        listRam.add("-Tipo Ram-")
        var userProductsRef =  db.collection("Compatibilidad").document("MotherBoard").collection("TipoRam")
        userProductsRef.get().addOnSuccessListener { tamañosM2 ->
            for(tamañoM2 in tamañosM2) {
                listRam.add(tamañoM2?.getString("Tipo").toString())
                s_tipoRam?.adapter =activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item, listRam) }
            }
        }
        s_tipoRam?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ram = parent?.getItemAtPosition(position).toString()
            }
        }
    }

}
