package com.example.empcmos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
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
import java.io.IOException

class MainActivity() : AppCompatActivity(), ComunicarFragmentos {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val SELECT_PICTURE=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout=findViewById(R.id.drawer_layout)
        val navView: NavigationView=findViewById(R.id.nav_view)
        val navController=findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration=AppBarConfiguration(
            setOf(
                R.id.insertUsers,
                R.id.insertVendedores,
                R.id.listVendedores,
                R.id.listUsers,
                R.id.listarProductos,
                R.id.insertProducto
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater=menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun enviarProductos(producto: EProducto): Fragment {
        var detalleProductoFragment=Detalles_Productos()
        var bundleEnvio: Bundle=Bundle()
        bundleEnvio.putSerializable("objeto", producto)
        detalleProductoFragment.arguments=bundleEnvio
        return detalleProductoFragment
    }

    /*override fun galeria() {
        val intent=Intent()
        //intent.type="image/*"
        //intent.action=Intent.ACTION_GET_CONTENT
        //startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK){
            try {
                val uri = data!!.data
                val img : ImageView = findViewById(R.id.imageView)
                img.setImageURI(uri)
                foto = uri.toString()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}*/

     */
}