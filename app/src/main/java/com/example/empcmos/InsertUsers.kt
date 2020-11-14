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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_insert_users.*
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 */
class InsertUsers : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

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


            if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(correo)
                && !TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)
                && !TextUtils.isEmpty(telefono)) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        Toast.makeText(activity, "Registrando", Toast.LENGTH_SHORT).show()
                        val db=FirebaseFirestore.getInstance()
                        val auth=FirebaseAuth.getInstance()
                        auth.createUserWithEmailAndPassword(correo, password)
                            .addOnCompleteListener { task ->
                                if (task.isComplete) {
                                    val user: FirebaseUser?=auth.currentUser

                                    //Correo verificación
                                    user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                        if (task.isComplete) {
                                            Toast.makeText(
                                                activity, "Correo de verificación enviado",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            val usuario=EUsuarios(
                                                nombre,
                                                apellido,
                                                correo,
                                                usuario,
                                                password,
                                                telefono,
                                                estado,
                                                "Usuario"
                                            )
                                            var userProductsRef=db.collection("User").document(user.uid)
                                            userProductsRef.set(usuario)
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        activity, "Usuario creado",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    val intent = Intent(activity, LoginActivity::class.java)
                                                    startActivity(intent)
                                                }
                                                .addOnFailureListener{
                                                    Toast.makeText(
                                                        activity, "Error al crear usuario",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                        } else {
                                            Toast.makeText(
                                                activity,
                                                "Error al enviar el correo de verificación",
                                                Toast.LENGTH_LONG
                                            ).show()
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
}
