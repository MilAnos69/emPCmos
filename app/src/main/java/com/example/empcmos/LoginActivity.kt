package com.example.empcmos

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUserEmail: EditText
    private lateinit var txtUserPassword: EditText

    //Cosas de firebase
    private lateinit var auth: FirebaseAuth

    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUserEmail = findViewById(R.id.inputEmail)
        txtUserPassword = findViewById(R.id.inputPassword)

        auth = FirebaseAuth.getInstance()
    }

    fun register(view: View){
        startActivity(Intent(this, RegistroUsuarioActivity::class.java))
    }

    fun registerVendedor(view: View){
        startActivity(Intent(this, RegistroVendedorActivity::class.java))
    }

    fun forgetPassword(view: View){
        //startActivity(Intent(this, ForgetPasswordActivity::class.java))
    }

    fun login(view: View){
        val email:String = txtUserEmail.text.toString()
        val password:String = txtUserPassword.text.toString()

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){

                        val user = FirebaseAuth.getInstance().currentUser

                        //user?.isEmailVerified recupera si está verificado se pone ? para evitar
                        //se cierre si llega vacio
                        if (user?.isEmailVerified == true){
                            //Enviar a vista usuario logeado
                            inputEmail.setText("")
                            inputPassword.setText("")
                            startActivity(Intent(this, MainActivity::class.java))
                        }else{
                            Toast.makeText(
                                this, "Verificar correo",
                                Toast.LENGTH_LONG
                            ).show()

                            //Esto es por si se venció el correo de verificación
                            user?.sendEmailVerification()

                            FirebaseAuth.getInstance().signOut()
                        }

                    }else{
                        Toast.makeText(
                            this, "Credenciales invalidas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }else{
            Toast.makeText(
                this, "Todos los campos deben estar llenos",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
