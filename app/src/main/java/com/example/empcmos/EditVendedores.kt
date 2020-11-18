package com.example.empcmos

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.empcmos.ui.Modelo.EVendedores
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_edit_vendedores.*
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Btn_Agregar
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Apellido
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_ConfirmarPassword
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Correo
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Direccion
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Nombre
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Password
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Telefono
import kotlinx.android.synthetic.main.fragment_edit_vendedores.Tb_Usuario
import kotlinx.android.synthetic.main.fragment_vendedores.*

/**
 * A simple [Fragment] subclass.
 */
class EditVendedores : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance().currentUser
    private val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
    private lateinit var oldPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_vendedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llenarDatos()

        Btn_Agregar.setOnClickListener {
            var nombre: String = Tb_Nombre.text.toString()
            var apellido: String = Tb_Apellido.text.toString()
            var correo: String = Tb_Correo.text.toString();
            var usuario: String = Tb_Usuario.text.toString()
            var password: String = Tb_Password.text.toString();
            var confirmPassword: String = Tb_ConfirmarPassword.text.toString();
            var direccion: String = Tb_Direccion.text.toString();
            var telefono: String = Tb_Telefono.text.toString();
            var estado: Boolean = true

            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(
                    correo
                )
                && !TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    confirmPassword
                ) && !TextUtils.isEmpty(telefono) && !TextUtils.isEmpty(direccion)
            ) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        val vendedores = EVendedores(
                            nombre,
                            apellido,
                            correo,
                            usuario,
                            password,
                            telefono,
                            direccion,
                            estado,
                            "Vendedor"
                        )
                        val user = FirebaseAuth.getInstance().currentUser?.uid
                        var userProductsRef =
                            user?.let { it1 -> db.collection("User").document(it1) }
                        if (correo == userEmail && password == oldPassword){
                            if (userProductsRef != null) {
                                datosNuevos(vendedores,userProductsRef)
                            }
                        }else if(password != oldPassword && correo == userEmail){
                            auth?.updatePassword(password)?.addOnCompleteListener {
                                if (userProductsRef != null) {
                                    datosNuevos(vendedores,userProductsRef)
                                }
                            }

                        }else if(correo != userEmail && password == oldPassword){
                            auth?.updateEmail(correo)?.addOnCompleteListener {
                                auth?.sendEmailVerification()?.addOnCompleteListener { task ->
                                    if (userProductsRef != null) {
                                        datosNuevos(vendedores,userProductsRef)
                                    }
                                }
                            }
                        }else if(correo != userEmail && password != oldPassword){
                            auth?.updateEmail(correo)?.addOnCompleteListener {
                                auth?.updatePassword(password)?.addOnCompleteListener {
                                    auth?.sendEmailVerification()?.addOnCompleteListener { task ->
                                        if (userProductsRef != null) {
                                            datosNuevos(vendedores,userProductsRef)
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity, "Las contraseñas no coinciden",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        activity, "La contraseña debe tener mínimo 6 caracteres",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                Toast.makeText(
                    activity, "Todos los campos deben ser completados",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun llenarDatos() {
        var userProductsRef = db.collection("User").whereEqualTo("correo", userEmail).whereEqualTo("estado",true)

        userProductsRef.get().addOnSuccessListener { users ->
            for (user in users) {
                Tb_Nombre.setText(user.getString("nombre").toString())
                Tb_Apellido.setText(user.getString("apellido").toString())
                Tb_Correo.setText(user.getString("correo").toString())
                Tb_Usuario.setText(user.getString("usuario").toString())
                Tb_Password.setText(user.getString("password").toString())
                oldPassword = user.getString("password").toString()
                Tb_ConfirmarPassword.setText(user.getString("password").toString())
                Tb_Telefono.setText(user.getString("telefono").toString())
                Tb_Direccion.setText(user.getString("direction").toString())
            }
        }
    }

    fun datosNuevos(vendedores: EVendedores, userProductsRef: DocumentReference){
        if (userProductsRef != null) {
            userProductsRef.set(vendedores)
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        Toast.makeText(
                            activity, "Vendedor actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            activity, "Error al actualizar Vendedor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }
    }

}