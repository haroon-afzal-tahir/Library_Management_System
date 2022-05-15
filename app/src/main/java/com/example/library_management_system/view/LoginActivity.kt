package com.example.library_management_system.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.model.User
import com.example.library_management_system.view.admin.AdminHome
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
//	https://console.firebase.google.com/u/0/project/library-management-syste-842a9/database/library-management-syste-842a9-default-rtdb/data/~2F
	private lateinit var mAuth: FirebaseAuth
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)
		mAuth = FirebaseAuth.getInstance()

		var db = FirebaseFirestore.getInstance()
		var docRef = db.collection("Users").document("Haroon")
		docRef.get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				var doc = task.result
				if (doc.exists()) {
					Log.d("Document: ", doc.data.toString())
					Log.d("Name", doc.get("Name").toString())
					Log.d("Email", doc.get("Email").toString())
					Log.d("Type", doc.get("Type").toString())
				}
				else {
					Log.d("Document: ", "No Data Exists")
				}
			} else {

			}
		}

		findViewById<Button>(R.id.sign_up).setOnClickListener {
			var intent = Intent(this, SignupActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
			startActivity(intent)
		}

		findViewById<MaterialButton>(R.id.sign_in).setOnClickListener {
			var intent = Intent(this, AdminHome::class.java)
			var email = findViewById<TextInputEditText>(R.id.login_email).text.toString()
			var password = findViewById<TextInputEditText>(R.id.login_password).text.toString()
			if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(email)) {
				Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show()
			}
			else {
				mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener	 { task ->
					if (task.isSuccessful) {
						Toast.makeText(this,"Login Successful", Toast.LENGTH_LONG).show()
						docRef.get().addOnSuccessListener { document ->
							if (document != null) {
								if (email == document.get("Email").toString()) {
									// This means the user is admin
									startActivity(intent)
								}
								else {
									// We will get type of the login user
									if (document.get("Type").toString() == "Librarian") {
										// Launch Activity of Librarian
										startActivity(intent)
									}
									else {
										// Launch Activity of User
										startActivity(intent)
									}
								}
							}
						}
					} else {
						Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
					}
				}
			}
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}
}