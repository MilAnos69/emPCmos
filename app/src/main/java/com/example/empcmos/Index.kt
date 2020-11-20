package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Adapters.AdapterItemCategoria
import com.example.empcmos.ui.Adapters.AdapterItemImage
import com.example.empcmos.ui.Adapters.AdapterProductoIndex
import com.example.empcmos.ui.Modelo.EProducto
import com.example.empcmos.ui.Modelo.Partes.ECategoria
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.util.*
import kotlin.collections.ArrayList


class Index : Fragment() {

    lateinit var activity1 : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos

    lateinit var sliderView : SliderView
    lateinit var adapterImagenes : AdapterItemImage
    var item = ArrayList<Int>()

    lateinit var adapterCategoria : AdapterItemCategoria
    lateinit var recyclerViewCategoria : RecyclerView
    var manage2 : LinearLayoutManager = LinearLayoutManager(context)
    var itemCategoria = ArrayList<ECategoria>()

    lateinit var adapterProducto : AdapterProductoIndex
    lateinit var recyclerView : RecyclerView
    var manage1 : LinearLayoutManager = LinearLayoutManager(context)
    var item1 = ArrayList<EProducto>()
    var item2 = ArrayList<EProducto>()

    lateinit var text1 : TextView
    lateinit var text2 : TextView
    lateinit var text3 : TextView

    lateinit var vista : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity1 = context as Activity
            this.interfaceComunicar = this.activity1 as ComunicarFragmentos
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId
        if (id == R.id.nav_search){
            return true
        }
        if(id == R.id.m_listar_pc){
            interfaceComunicar.limpiarlista()
            vista.findNavController().navigate(R.id.action_index_to_lista_pc)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_index, container, false)
        vista = view
        sliderView = view.findViewById(R.id.imageSlider)
        recyclerViewCategoria = view.findViewById(R.id.listaCategoria)
        recyclerView = view.findViewById(R.id.listaP)
        item = ArrayList<Int>()
        itemCategoria = ArrayList<ECategoria>()
        item1 = ArrayList<EProducto>()

        text1 = view.findViewById(R.id.text_nov)
        text2 = view.findViewById(R.id.text_cate)
        text3 = view.findViewById(R.id.text_pro)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })


        cargarVista()
        mostrarDatos()
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val item : MenuItem = menu!!.findItem(R.id.nav_search)
        if (item != null) {
            var searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        item1.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        item2.forEach {
                            if (it.tituloProducto.toLowerCase(Locale.getDefault())
                                    .contains(search)
                            ) {
                                item1.add(it)
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    } else {
                        item1.clear()
                        item1.addAll(item2)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    ocultar()
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    mostrar()
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun cargarVista(){
        //Cargar Novedades
        item.add(R.drawable.categoria)
        item.add(R.drawable.nuevos_productos)
        item.add(R.drawable.fondo)

        //Cargar Datos Categoria
        itemCategoria.add(ECategoria("Procesador", R.drawable.procesador))
        itemCategoria.add(ECategoria("Mother Board", R.drawable.placa))
        itemCategoria.add(ECategoria("Fuente", R.drawable.fuente))
        itemCategoria.add(ECategoria("Ram", R.drawable.ram))
        itemCategoria.add(ECategoria("Disco Duro", R.drawable.disco))
        itemCategoria.add(ECategoria("SSD M2", R.drawable.ssd))
        itemCategoria.add(ECategoria("Tarjeta Grafica", R.drawable.grafica))
        itemCategoria.add(ECategoria("Refrigeracion", R.drawable.cooler))
        itemCategoria.add(ECategoria("Caja", R.drawable.caja))

        //Cargar Productos
        item2.addAll(interfaceComunicar.llenarProductos())
        item1.addAll(interfaceComunicar.llenarProductos())
    }

    fun mostrarDatos(){
        adapterImagenes = AdapterItemImage(context, item)
        sliderView.setSliderAdapter(adapterImagenes)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        manage2.orientation = LinearLayoutManager.HORIZONTAL
        adapterCategoria = AdapterItemCategoria(context, itemCategoria)
        recyclerViewCategoria.layoutManager = manage2
        recyclerViewCategoria.adapter = this.adapterCategoria

        adapterCategoria.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                var nombre : String = itemCategoria.get(recyclerViewCategoria.getChildAdapterPosition(v)).nombreC
                interfaceComunicar.listaProductosFiltrado(nombre,v)
            }
        })

        manage1.orientation = LinearLayoutManager.HORIZONTAL
        adapterProducto = AdapterProductoIndex(context, item1)
        recyclerView.layoutManager = manage1
        recyclerView.adapter = this.adapterProducto

        adapterProducto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                var fm = interfaceComunicar.enviarProductos(item1.get(recyclerView.getChildAdapterPosition(v)),v)
            }
        })
    }

    fun ocultar(){
        text1.visibility = View.GONE
        text2.visibility = View.GONE
        text3.visibility = View.GONE
        sliderView.visibility = View.GONE
        recyclerViewCategoria.visibility = View.GONE
    }

    fun mostrar(){
        text1.visibility = View.VISIBLE
        text2.visibility = View.VISIBLE
        text3.visibility = View.VISIBLE
        sliderView.visibility = View.VISIBLE
        recyclerViewCategoria.visibility = View.VISIBLE
    }
}
