package com.example.library_management_system.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.view.admin.AdminHome
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SignupActivity : AppCompatActivity() {

	private lateinit var mAuth: FirebaseAuth


	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.signup_activity)

		mAuth = FirebaseAuth.getInstance()

		findViewById<MaterialButton>(R.id.real_sign_up).setOnClickListener {
			var intent = Intent(this, LoginActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
			startActivity(intent)
		}

		findViewById<MaterialButton>(R.id.real_sign_up).setOnClickListener {
			var email = findViewById<TextInputEditText>(R.id.signup_email).text.toString()
			var password = findViewById<TextInputEditText>(R.id.signup_password).text.toString()
			var name = findViewById<TextInputEditText>(R.id.sign_up_name).text.toString()
			if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(name)) {
				Toast.makeText(this, "Please Enter Credentials", Toast.LENGTH_LONG).show()
			}
			else {
				mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener	 { task ->
					if (task.isSuccessful) {
						Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_LONG).show()
						var intent = Intent(this, AdminHome::class.java)
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

						var db =  FirebaseFirestore.getInstance()

						val user = hashMapOf(
							"Email" to email,
							"Name" to name,
							"Type" to "User"
						)

						db.collection("Users").document(name).set(user, SetOptions.merge())
							.addOnCompleteListener { Log.d("Document: " ,"Successfully Added") }
							.addOnFailureListener { Log.d("Document: ", "Error") }

						startActivity(intent)
					} else {
						Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_LONG).show()
					}
				}
			}
		}
	}
}