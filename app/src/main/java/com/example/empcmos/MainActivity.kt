package com.example.empcmos

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EProducto
import com.example.empcmos.ui.Modelo.EUsuarios
import com.example.empcmos.ui.Modelo.Partes.ECaja
import com.example.empcmos.ui.Modelo.Partes.ECategoria
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.item_categoria.*
import java.io.IOException
import java.time.LocalDateTime

class MainActivity() : AppCompatActivity(), ComunicarFragmentos {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val SELECT_PICTURE=2
    internal var storage:FirebaseStorage?=null
    internal var storageReferencia:StorageReference?=null
    private var filepath: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    lateinit var listUsuarios: ArrayList<EUsuarios>
    private  var rol: String=""

    //ListaPartes
    lateinit var listaFiltrada : ArrayList<EProducto>

    //Datos Producto
    var listaPcs = ArrayList<EProducto>()
    var imgPcs = ArrayList<Uri>()

    lateinit var listaFinal : ArrayList<EProducto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listUsuarios = ArrayList<EUsuarios>()
        listaFiltrada = ArrayList<EProducto>()
        listaPcs = ArrayList<EProducto>()
        listaFinal = ArrayList<EProducto>()
        cargarImagenes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun enviarProductos(producto: EProducto, view: View){
        var bundleEnvio: Bundle=Bundle()
        bundleEnvio.putSerializable("objeto", producto)
        view.findNavController().navigate(R.id.action_index_to_detalles_Productos, bundleEnvio)
    }

    override fun enviarProductoLista(producto: EProducto, view: View){
        var bundleEnvio: Bundle=Bundle()
        bundleEnvio.putSerializable("objeto", producto)
        view.findNavController().navigate(R.id.action_listarProductos_to_detalles_Productos, bundleEnvio)
    }

    override fun galeria() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagen"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            try {
                filepath = data.data!!;
                val img : ImageView = findViewById(R.id.imageView)
                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
                img.setImageBitmap(bitmap)
                if (filepath != null){
                    foto()
                }
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun subirImagen(idUser: String, nombre:String): String {
        val current = LocalDateTime.now().toString()
        val imagen: String = current.toString() + "_" + idUser + "_" + nombre
        if(filepath!=null){
            var referenciaImagen = storageReferencia!!.child("images/"+ imagen)
            referenciaImagen.putFile(filepath!!)
            return imagen
        }else{
            return ""
        }

    }

    override fun foto(): Boolean{
        if (filepath != null){
            return true
        }else{
            return false
        }
    }



    fun cargarVista(){
        val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
        var userProductsRef =  db.collection("User").whereEqualTo("correo",userEmail)

         userProductsRef.get().addOnSuccessListener { users ->
            for (user in users) {
                rol = user.getString("rol").toString()
            }
             llenarOnCreate()
        }

    }

    fun getCaja(){
        var ProductsRef =  db.collection("Productos")
        var nombre : String
        var valor : Int
        var imagenC : String
        var nombreImage : String
        var parte: String
        var descripcion : String
        ProductsRef.get().addOnSuccessListener {p->
            for (productos in p) {
                parte =  productos.getString("parte").toString()
                nombre = productos.getString("nombre").toString()
                valor = Integer.parseInt(productos.get("valor").toString())
                nombreImage =  productos.get("imagen").toString()
                descripcion = productos.getString("descripcion").toString()
                imagenC = "images/"+ productos.get("imagen").toString()
                imgPcs.forEach {
                    if(imagenC == it.lastPathSegment){
                        listaPcs.add(EProducto(parte,nombre,valor,nombreImage,descripcion,it.toString()))
                    }
                }
            }
        }
        cargarVista()
    }



    fun cargarImagenes(){
        storage = FirebaseStorage.getInstance()
        storageReferencia = storage!!.reference.child("images")
        val imagelist : Task<ListResult> = storageReferencia!!.listAll()
        imagelist.addOnCompleteListener {
            val items : List<StorageReference> = it.result!!.items
            items.forEachIndexed { index, it ->
                it.downloadUrl.addOnSuccessListener {
                    imgPcs.add(it)
                }
            }
        }.addOnCompleteListener {
            getCaja()
        }
    }

    override fun llenarProductos(): ArrayList<EProducto> {
        return listaPcs
    }

    override fun listaProductosFiltrado(parteEscogida: String, view: View){
        listaFiltrada.clear()
        listaPcs.forEach {
            if (it.parte==parteEscogida){
                listaFiltrada.add(it)
            }
        }
        view.findNavController().navigate(R.id.action_index_to_listarProductos)
    }

    override fun llenarProductosFiltrados(): ArrayList<EProducto> {
        return listaFiltrada
    }

    override fun back() {
        listaFiltrada.clear()
    }

    override fun llenarDatosPC(parteEscogida: String) {
        listaFiltrada.clear()
        listaPcs.forEach {
            if (it.parte==parteEscogida){
                listaFiltrada.add(it)
            }
        }
    }

    override fun llenarListaFinal(string: String): ArrayList<EProducto>{
        var lista = ArrayList<EProducto>()
        listaFinal.forEach {
            if(it.parte == string){
                lista.add(it)
            }
        }
        return lista
    }

    override fun agregarListaFinal(producto: EProducto) {
        listaFinal.add(producto)
    }

    override fun eliminarLista(producto: EProducto) {
        listaFinal.remove(producto)
    }

    override fun listafinalFiltro(string: String): Boolean {
        listaFinal.forEach {
            if(it.parte == string){
                return true
            }
        }
        return false
    }




    fun llenarOnCreate(){
        if(rol == "Vendedor"){
            setContentView(R.layout.activity_main_vendedor)
        }else if(rol == "Usuario"){
            setContentView(R.layout.activity_main_usuario)
        }else if(rol == ""){
            setContentView(R.layout.activity_main)
        }


        val toolbar: Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout=findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController=findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration=AppBarConfiguration(
            setOf(
                R.id.editUsuario,
                R.id.editVendedores,
                R.id.listVendedores,
                R.id.listUsers,
                R.id.listarProductos,
                R.id.insertProducto,
                R.id.index,
                R.id.detalles_Productos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        storage = FirebaseStorage.getInstance()
        storageReferencia=storage!!.reference
    }

}