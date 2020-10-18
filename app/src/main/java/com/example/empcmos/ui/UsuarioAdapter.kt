package com.example.empcmos.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.empcmos.R
import com.example.empcmos.ui.Modelo.EUsuarios
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.usuario.view.*

class UsuarioAdapter(private val mContext: Context, private val LV_Usuarios: List<EUsuarios>) : ArrayAdapter<EUsuarios>(mContext, 0, LV_Usuarios) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.usuario, parent, false)

        val user = LV_Usuarios[position]

        var db = FirebaseFirestore.getInstance()


        layout.name.text = user.nombre + user.apellido
        layout.userName.text = user.usuario

        return layout
    }
}