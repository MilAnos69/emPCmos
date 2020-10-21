package com.example.empcmos

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EUsuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_users.*


/**
 * A simple [Fragment] subclass.
 */
class InsertUsers : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insert_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Btn_Agregar.setOnClickListener{
            var nombre:String = Tb_Nombre.text.toString()
            var apellido: String = Tb_Apellido.text.toString()
            var correo: String = Tb_Correo.text.toString();
            var usuario: String = Tb_Usuario.text.toString()
            var password: String = Tb_Password.text.toString();
            var confirmPassword: String = Tb_ConfirmarPassword.text.toString();
            var telefono: String = Tb_Telefono.text.toString();
            var estado: Boolean = true


            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(
                    correo
                )
                && !TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    confirmPassword
                ) && !TextUtils.isEmpty(telefono)
            ) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()

                        val db = FirebaseFirestore.getInstance()
                        val usuario = EUsuarios(
                            nombre,
                            apellido,
                            correo,
                            usuario,
                            password,
                            telefono,
                            estado
                        )
                        var userProductsRef = db.collection("User")
                        userProductsRef.add(usuario).addOnCompleteListener { task ->
                            if (task.isComplete) {
                                Toast.makeText(
                                    activity, "Usuario creado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity, "Error al usuario",
                                    Toast.LENGTH_LONG
                                ).show()
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


}
