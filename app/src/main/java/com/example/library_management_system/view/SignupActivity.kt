package com.example.library_management_system.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.helper.InsertDataIntoFirestore
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
				if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

					mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
						val isNewUser : Boolean? = task.result.signInMethods?.isEmpty()
						if (isNewUser == true) {
							mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener	 { task1 ->
								if (task1.isSuccessful) {
									Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_LONG).show()
									val intent = Intent(this, AdminHome::class.java)
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

									InsertDataIntoFirestore().insertUser(email, name, password, "User")

									startActivity(intent)
								} else {
									Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_LONG).show()
								}
							}
						}
						else {
							Toast.makeText(this, "This User Already Has an Account", Toast.LENGTH_SHORT).show()
						}
					}
				}
			}
		}
	}
}