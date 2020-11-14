package com.example.empcmos

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.empcmos.ui.Modelo.EUsuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_edit_usuario.*

/**
 * A simple [Fragment] subclass.
 */
class EditUsuario : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llenarDatos()


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
                )
                && !TextUtils.isEmpty(telefono)) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        val db = FirebaseFirestore.getInstance()
                        val auth = FirebaseAuth.getInstance().currentUser
                        val usuario = EUsuarios(
                            nombre,
                            apellido,
                            correo,
                            usuario,
                            password,
                            telefono,
                            estado,
                            "Usuario"
                        )
                        val user = FirebaseAuth.getInstance().currentUser?.uid
                        var userProductsRef = user?.let { it1 -> db.collection("User").document(it1) }

                        auth?.updateEmail(correo)?.addOnCompleteListener {
                            auth?.updatePassword(password)?.addOnCompleteListener {
                                //Correo verificación
                                auth?.sendEmailVerification()?.addOnCompleteListener { task ->
                                    if (userProductsRef != null) {
                                        userProductsRef.set(usuario)
                                            .addOnCompleteListener { task ->
                                                if (task.isComplete) {
                                                    Toast.makeText(
                                                        activity, "Usuario actualizado",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        activity, "Error al actualizar usuario",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
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

    fun llenarDatos(){
        var userProductsRef =  db.collection("User").whereEqualTo("correo", userEmail)

        userProductsRef.get().addOnSuccessListener { users ->
            for (user in users) {
                Tb_Nombre.setText(user.getString("nombre").toString())
                Tb_Apellido.setText(user.getString("apellido").toString())
                Tb_Correo.setText(user.getString("correo").toString())
                Tb_Usuario.setText(user.getString("usuario").toString())
                Tb_Password.setText(user.getString("password").toString())
                Tb_ConfirmarPassword.setText(user.getString("password").toString())
                Tb_Telefono.setText(user.getString("telefono").toString())
            }
        }
    }

}
