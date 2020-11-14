package com.example.empcmos.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.empcmos.R
import com.smarteist.autoimageslider.SliderViewAdapter

class AdapterItemImage(mContext: Context?, listaImagenes: List<Int>) : SliderViewAdapter<AdapterItemImage.Holder>() {

    val imagenes : List<Int>
    var inglaterr : LayoutInflater
    val context : Context? = mContext

    init {
        this.inglaterr = LayoutInflater.from(mContext)
        this.imagenes = listaImagenes
    }


    class Holder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        lateinit var imageView : ImageView

        init {
            if (itemView != null) {
                imageView = itemView.findViewById(R.id.imagen_novedades)
            }
        }
    }

    override fun getCount(): Int {
        return imagenes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): Holder {
        var view : View =  inglaterr.inflate(R.layout.item_imagen, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(viewHolder: Holder?, position: Int) {
        var img : Int = imagenes.get(position)
        if (viewHolder != null) {
            viewHolder.imageView.setImageResource(img)
            viewHolder.imageView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                }
            })
        }
    }
}