package com.example.library_management_system.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
//	https://console.firebase.google.com/u/0/project/library-management-syste-842a9/database/library-management-syste-842a9-default-rtdb/data/~2F
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		var auth = FirebaseAuth.getInstance()
	}

	override fun onStart() {
		super.onStart()
	}
}