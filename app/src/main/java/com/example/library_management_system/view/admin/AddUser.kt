package com.example.library_management_system.view.admin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.helper.InsertDataIntoFirestore
import com.example.library_management_system.view.librarian.LibrarianHome
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AddUser : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_user)

		findViewById<MaterialButton>(R.id.add_user_add_user).setOnClickListener {
			val name = findViewById<TextInputEditText>(R.id.user_sign_up_name).text.toString()
			val email = findViewById<TextInputEditText>(R.id.user_signup_email).text.toString()
			val password = findViewById<TextInputEditText>(R.id.user_signup_password).text.toString()

			if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show()
			}
			else {
				val auth = FirebaseAuth.getInstance()

				auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task1 ->
					val isNewUser : Boolean? = task1.result.signInMethods?.isEmpty()
					if (isNewUser == true) {
						auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task2 ->
							if (task2.isSuccessful) {
								Toast.makeText(this, "Librarian Successfully Added", Toast.LENGTH_SHORT).show()

//								var intent: Intent = Intent(this, AdminHome::class.java)
//								intent = if (getIntent().getStringExtra("Type").toString() == "Admin") {
//									Intent(this, AdminHome::class.java)
//								} else {
//									Intent(this, LibrarianHome::class.java)
//								}
//								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

								InsertDataIntoFirestore().insertUser(email, name, password, "User")

//								startActivity(intent)
								super.onBackPressed()
							} else {
								Toast.makeText(this, "Librarian Couldn't Be Added Due To Some Issue", Toast.LENGTH_SHORT).show()
							}

						}
					}
				}
			}
		}
	}
}