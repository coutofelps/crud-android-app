package com.example.crud_android_app.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud_android_app.R
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Register: AppCompatActivity() {
    // Creating variables to edit text, for the textview, the Firebase auth system, button and progress bar
    private var edtUsername: TextInputEditText? = null
    private var edtPassword: TextInputEditText? = null
    private var edtConfirmPassword: TextInputEditText? = null
    private var tvUserLogin: TextView? = null
    private var btnRegister: Button? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var pbLoading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initializing database
        firebaseAuth = FirebaseAuth.getInstance()

        // Initializing variables
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        tvUserLogin = findViewById(R.id.tvUserLogin)
        btnRegister = findViewById(R.id.btnRegister)
        pbLoading = findViewById(R.id.pbLoading)

        // Adding listener to tvUserLogin
        tvUserLogin?.setOnClickListener(View.OnClickListener {
            val i = Intent(this@Register, Login::class.java)
            startActivity(i)
        })

        // Adding action when you press the register button
        btnRegister?.setOnClickListener(View.OnClickListener {
            // Hiding our progress bar
            pbLoading?.visibility = View.GONE

            val username: String = edtUsername?.text.toString()
            val password: String = edtPassword?.text.toString()
            val passwordConfirmation: String = edtConfirmPassword?.text.toString()

            // Checking if both passwords is equal
            if(password != passwordConfirmation) {
                Toast.makeText(this@Register, "Certifique-se de que os dois campos de senha são iguais.", Toast.LENGTH_SHORT).show()
            }

            // Checking if the text edit fields is empty
            else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(passwordConfirmation)) {
                Toast.makeText(this@Register, "Certifique-se que todos os campos estão preenchidos corretamente.", Toast.LENGTH_SHORT).show()
            }

            // If everything is ok
            else {
                firebaseAuth?.createUserWithEmailAndPassword(username, password)?.addOnCompleteListener() {
                    fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful) {
                            pbLoading?.visibility = View.GONE
                            Toast.makeText(this@Register, "Usuário registrado.", Toast.LENGTH_SHORT).show()
                            val i = Intent(this@Register, Login::class.java)
                            startActivity(i)
                            finish()
                        }

                        else {
                            pbLoading?.visibility = View.GONE
                            Toast.makeText(this@Register, "Falha ao registrar usuário.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }
}