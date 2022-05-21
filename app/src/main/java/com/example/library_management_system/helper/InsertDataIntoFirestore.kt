package com.example.library_management_system.helper

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class InsertDataIntoFirestore {

	fun insertUser(email: String, name: String, password: String, type: String) {
		val user = hashMapOf(
			"Email" to email,
			"Name" to name,
			"Password" to password,
			"Type" to type
		)

		val account = hashMapOf(
			"Borrowed Books" to {},
			"Total Books Ordered" to 0
		)

		val db = FirebaseFirestore.getInstance()
		db.collection("Users").document(name).set(user, SetOptions.merge())
		db.collection("Account").document(name).set(account, SetOptions.merge())
	}
}