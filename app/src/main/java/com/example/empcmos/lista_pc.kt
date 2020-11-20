package com.example.empcmos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empcmos.ui.Adapters.AdapterProducto
import com.example.empcmos.ui.Adapters.AdapterProductoIndex
import com.example.empcmos.ui.Adapters.AdapterProductoListaPc
import com.example.empcmos.ui.Modelo.EProducto


/**
 * A simple [Fragment] subclass.
 */
class lista_pc : Fragment(), ItemSeleccionado{

    lateinit var adapterProducto : AdapterProductoIndex
    lateinit var adapterEmsamble : AdapterProductoListaPc

    var recliclerF : RecyclerView? = null

    lateinit var recyclerView0 : RecyclerView
    lateinit var recyclerView1 : RecyclerView
    lateinit var recyclerView2 : RecyclerView
    lateinit var recyclerView3 : RecyclerView
    lateinit var recyclerView4 : RecyclerView
    lateinit var recyclerView5 : RecyclerView
    lateinit var recyclerView6 : RecyclerView
    lateinit var recyclerView7 : RecyclerView
    lateinit var recyclerView8 : RecyclerView

    lateinit var bto0 : Button
    lateinit var bto1 : Button
    lateinit var bto2 : Button
    lateinit var bto3 : Button
    lateinit var bto4 : Button
    lateinit var bto5 : Button
    lateinit var bto6 : Button
    lateinit var bto7 : Button
    lateinit var bto8 : Button

    lateinit var modelarBto : Button


    var manage1 : LinearLayoutManager = LinearLayoutManager(context)
    var manage2 : LinearLayoutManager = LinearLayoutManager(context)
    var manage3 : LinearLayoutManager = LinearLayoutManager(context)
    var manage4 : LinearLayoutManager = LinearLayoutManager(context)
    var manage5 : LinearLayoutManager = LinearLayoutManager(context)
    var manage6 : LinearLayoutManager = LinearLayoutManager(context)
    var manage7 : LinearLayoutManager = LinearLayoutManager(context)
    var manage8 : LinearLayoutManager = LinearLayoutManager(context)
    var manage9 : LinearLayoutManager = LinearLayoutManager(context)

    lateinit var l1 : LinearLayout
    lateinit var l2 : LinearLayout
    lateinit var l3 : LinearLayout
    lateinit var l4 : LinearLayout
    lateinit var l5 : LinearLayout
    lateinit var l6 : LinearLayout
    lateinit var l7 : LinearLayout
    lateinit var l8 : LinearLayout
    lateinit var l9 : LinearLayout

    lateinit var Ebto0 : Button
    lateinit var Ebto1 : Button
    lateinit var Ebto2 : Button
    lateinit var Ebto3 : Button
    lateinit var Ebto4 : Button
    lateinit var Ebto5 : Button
    lateinit var Ebto6 : Button
    lateinit var Ebto7 : Button
    lateinit var Ebto8 : Button

    lateinit var Pbto0 : Button
    lateinit var Pbto1 : Button
    lateinit var Pbto2 : Button
    lateinit var Pbto3 : Button
    lateinit var Pbto4 : Button
    lateinit var Pbto5 : Button
    lateinit var Pbto6 : Button
    lateinit var Pbto7 : Button
    lateinit var Pbto8 : Button

    var item1 = ArrayList<EProducto>()

    var item2 = ArrayList<EProducto>()

    var validarItems = ArrayList<String>()

    //Referencias para comunicar fragment

    lateinit var activity1 : Activity
    lateinit var interfaceComunicar : ComunicarFragmentos

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity){
            this.activity1 = context as Activity
            this.interfaceComunicar = this.activity as ComunicarFragmentos
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_lista_pc, container, false)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_lista_pc_to_index)
            }
        })

        validarItems.add("Procesador")
        validarItems.add("Mother Board")
        validarItems.add("Fuente")
        validarItems.add("Ram")
        validarItems.add("Disco Duro")
        validarItems.add("SSD M2")
        validarItems.add("Tarjeta Grafica")
        validarItems.add("Refrigeracion")
        validarItems.add("Caja")

        recyclerView0 = view.findViewById(R.id.list0)
        recyclerView1 = view.findViewById(R.id.list1)
        recyclerView2 = view.findViewById(R.id.list2)
        recyclerView3 = view.findViewById(R.id.list3)
        recyclerView4 = view.findViewById(R.id.list4)
        recyclerView5 = view.findViewById(R.id.list5)
        recyclerView6 = view.findViewById(R.id.list6)
        recyclerView7 = view.findViewById(R.id.list7)
        recyclerView8 = view.findViewById(R.id.list8)

        bto0 = view.findViewById(R.id.bte0)
        bto1 = view.findViewById(R.id.bte1)
        bto2 = view.findViewById(R.id.bte2)
        bto3 = view.findViewById(R.id.bte3)
        bto4 = view.findViewById(R.id.bte4)
        bto5 = view.findViewById(R.id.bte5)
        bto6 = view.findViewById(R.id.bte6)
        bto7 = view.findViewById(R.id.bte7)
        bto8 = view.findViewById(R.id.bte8)

        modelarBto = view.findViewById(R.id.modelarBte)

        manage1.orientation = LinearLayoutManager.HORIZONTAL

        l1 = view.findViewById(R.id.botones1)
        l2 = view.findViewById(R.id.botones2)
        l3 = view.findViewById(R.id.botones3)
        l4 = view.findViewById(R.id.botones4)
        l5 = view.findViewById(R.id.botones5)
        l6 = view.findViewById(R.id.botones6)
        l7 = view.findViewById(R.id.botones7)
        l8 = view.findViewById(R.id.botones8)
        l9 = view.findViewById(R.id.botones9)

        Ebto0 = view.findViewById(R.id.Ebte0)
        Ebto1 = view.findViewById(R.id.Ebte1)
        Ebto2 = view.findViewById(R.id.Ebte2)
        Ebto3 = view.findViewById(R.id.Ebte3)
        Ebto4 = view.findViewById(R.id.Ebte4)
        Ebto5 = view.findViewById(R.id.Ebte5)
        Ebto6 = view.findViewById(R.id.Ebte6)
        Ebto7 = view.findViewById(R.id.Ebte7)
        Ebto8 = view.findViewById(R.id.Ebte8)

        Pbto0 = view.findViewById(R.id.Pbte0)
        Pbto1 = view.findViewById(R.id.Pbte1)
        Pbto2 = view.findViewById(R.id.Pbte2)
        Pbto3 = view.findViewById(R.id.Pbte3)
        Pbto4 = view.findViewById(R.id.Pbte4)
        Pbto5 = view.findViewById(R.id.Pbte5)
        Pbto6 = view.findViewById(R.id.Pbte6)
        Pbto7 = view.findViewById(R.id.Pbte7)
        Pbto8 = view.findViewById(R.id.Pbte8)

        bto0.setOnClickListener {
            llenarDatos(
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                manage1,
                "Mother Board",
                l1,
                Pbto0,
                Ebto0
            )
        }

        bto1.setOnClickListener {
            llenarDatos(
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                manage2,
                "Procesador",
                l2,
                Pbto1,
                Ebto1
            )
        }

        bto2.setOnClickListener {
            llenarDatos(
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                manage3,
                "Ram",
                l3,
                Pbto2,
                Ebto2
            )
        }

        bto3.setOnClickListener {
            llenarDatos(
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                manage4,
                "Disco Duro",
                l4,
                Pbto3,
                Ebto3
            )
        }

        bto4.setOnClickListener {
            llenarDatos(
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                manage5,
                "SSD M2",
                l5,
                Pbto4,
                Ebto4
            )
        }

        bto5.setOnClickListener {
            llenarDatos(
                recyclerView5,
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                manage6,
                "Tarjeta Grafica",
                l6,
                Pbto5,
                Ebto5
            )
        }

        bto6.setOnClickListener {
            llenarDatos(
                recyclerView6,
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                manage7,
                "Refrigeracion",
                l7,
                Pbto6,
                Ebto6
            )
        }

        bto7.setOnClickListener {
            llenarDatos(
                recyclerView7,
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                manage8,
                "Fuente",
                l8,
                Pbto7,
                Ebto7
            )
        }

        bto8.setOnClickListener {
            llenarDatos(
                recyclerView8,
                recyclerView0,
                recyclerView1,
                recyclerView2,
                recyclerView3,
                recyclerView4,
                recyclerView5,
                recyclerView6,
                recyclerView7,
                manage9,
                "Caja",
                l9,
                Pbto8,
                Ebto8

            )
        }

        modelarBto.setOnClickListener {
            item2.addAll(interfaceComunicar.listatotal())
            if(item2.isNotEmpty() && item2.size >= 9){
                view.findNavController().navigate(R.id.action_lista_pc_to_compra)
            }else{
                Toast.makeText(activity, "Ingrese todo los Productos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    fun llenarDatos(
        recycler1: RecyclerView,
        recycler2: RecyclerView,
        recycler3: RecyclerView,
        recycler4: RecyclerView,
        recycler5: RecyclerView,
        recycler6: RecyclerView,
        recycler7: RecyclerView,
        recycler8: RecyclerView,
        recycler9: RecyclerView,
        manager: LinearLayoutManager,
        string: String,
        linear : LinearLayout,
        bton1 : Button,
        bton2 : Button
    ){
        agregarRecicler(recycler1)
        if( recycler2.visibility.toString() == "8" && recycler3.visibility.toString() == "8" &&
            recycler4.visibility.toString() == "8" && recycler5.visibility.toString() == "8" &&
            recycler6.visibility.toString() == "8" && recycler7.visibility.toString() == "8" &&
            recycler8.visibility.toString() == "8" && recycler9.visibility.toString() == "8"){
            if (recycler1.visibility.toString() == "8"){
                if(interfaceComunicar.listafinalFiltro("Mother Board") == true || string == "Mother Board"){
                    if(string=="Procesador"){
                        interfaceComunicar.validarDatosProcesadorPC(string)
                    }else if(string=="Ram"){
                        interfaceComunicar.validarDatosRamPC(string)
                    }else if(string=="Disco Duro"){
                        interfaceComunicar.llenarDatosPC(string)
                    }else if(string=="SSD M2"){
                        interfaceComunicar.validarDatosSsdMdosPC(string)
                    }else if(string=="Caja"){
                        interfaceComunicar.validarDatosCajaPC(string)
                    }else if(string=="Fuente"){
                        interfaceComunicar.validarDatosFuentePC(string)
                    }else{
                        interfaceComunicar.llenarDatosPC(string)
                    }
                    item1.clear()
                    item2.clear()
                    item1.addAll(interfaceComunicar.llenarProductosFiltrados())
                    visibleItem(linear,recycler1)
                    cargarVista(recycler1,manager,string,bton1,bton2)
                }
            } else {
                recycler1.visibility = View.GONE
                linear.visibility = View.GONE
            }

        }
    }

    fun visibleItem(linear : LinearLayout, recycler: RecyclerView){
        linear.visibility = View.VISIBLE
        recycler.visibility = View.VISIBLE
    }

    fun cargarVista(
        recyclerView: RecyclerView,
        manager: LinearLayoutManager,
        string: String,
        bt0 : Button, //BOTON PRODUCTOS
        bt1 : Button //BOTON ENSAMBLE
    ){
        adapterProducto = AdapterProductoIndex(context, item1)
        recyclerView.layoutManager = manager
        recyclerView.adapter = this.adapterProducto

        adapterProducto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View) {
                if(string=="Disco Duro" && interfaceComunicar.listafinalDisco(4,string) == false){
                    Toast.makeText(activity, "Cantidad Total", Toast.LENGTH_SHORT).show()
                }else if(string=="SSD M2" && interfaceComunicar.listafinalDisco(2,string) == false){
                    Toast.makeText(activity, "Cantidad Total", Toast.LENGTH_SHORT).show()
                }else if(interfaceComunicar.listafinalDisco(1,string) == false){
                    Toast.makeText(activity, "Cantidad Total", Toast.LENGTH_SHORT).show()
                }else{
                    interfaceComunicar.agregarListaFinal(item1.get(recyclerView.getChildAdapterPosition(v)))
                    Toast.makeText(activity, "Producto Agregado", Toast.LENGTH_SHORT).show()
                }
            }
        })

        bt0.setOnClickListener {
            item1.clear()
            item1.addAll(interfaceComunicar.llenarProductosFiltrados())
            if(item1.isEmpty() && string=="Procesador"){
                interfaceComunicar.validarDatosProcesadorPC(string)
            }else if(item1.isEmpty() && string=="Ram"){
                interfaceComunicar.validarDatosRamPC(string)
            }else if(item1.isEmpty() && string=="Disco Duro"){
                interfaceComunicar.llenarDatosPC(string)
            }else if(item1.isEmpty() && string=="SSD M2"){
                interfaceComunicar.validarDatosSsdMdosPC(string)
            }else if(item1.isEmpty() && string=="Caja"){
                interfaceComunicar.validarDatosCajaPC(string)
            }else if(item1.isEmpty() && string=="Fuente"){
                interfaceComunicar.validarDatosFuentePC(string)
            }else if(item1.isEmpty()){
                interfaceComunicar.llenarDatosPC(string)
            }
            adapterProducto = AdapterProductoIndex(context, item1)
            recyclerView.layoutManager = manager
            recyclerView.adapter = this.adapterProducto

            adapterProducto.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v : View) {
                    if(string=="Disco Duro" && interfaceComunicar.listafinalDisco(4,string) == false){
                        Toast.makeText(activity, "Cantidad Total", Toast.LENGTH_SHORT).show()
                    }else if(string=="SSD M2" && interfaceComunicar.listafinalDisco(2,string) == false){
                        Toast.makeText(activity, "Cantidad Total", Toast.LENGTH_SHORT).show()
                    }else{
                        interfaceComunicar.agregarListaFinal(item1.get(recyclerView.getChildAdapterPosition(v)))
                        Toast.makeText(activity, "Producto Agregado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        bt1.setOnClickListener {
            item2.clear()
            item2.addAll(interfaceComunicar.llenarListaFinal(string))
            adapterEmsamble = AdapterProductoListaPc(context, item2, this@lista_pc)
            recyclerView.layoutManager = manager
            recyclerView.adapter = this.adapterEmsamble
        }
    }

    fun agregarRecicler(recyclerView: RecyclerView){
        recliclerF = recyclerView
    }

    override fun eliminar(posi: Int) {
        interfaceComunicar.eliminarLista(item2.get(posi))
        item2.removeAt(posi)
        recliclerF!!.adapter!!.notifyDataSetChanged()
    }
}