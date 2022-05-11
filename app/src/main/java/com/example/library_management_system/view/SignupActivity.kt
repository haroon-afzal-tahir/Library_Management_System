package com.example.library_management_system.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.signup_activity)


	}
}