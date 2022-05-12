package com.example.library_management_system.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

	private lateinit var mAuth: FirebaseAuth

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

			if (TextUtils.isEmpty(email) &&
				TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(email)) {
				Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show()
			}
			else {
				mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener	 { task ->
					if (task.isSuccessful) {
						Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_LONG).show()
						var intent = Intent(this, Dashboard::class.java)
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
						startActivity(intent)
					} else {
						Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_LONG).show()
					}
				}
			}
		}
	}
}