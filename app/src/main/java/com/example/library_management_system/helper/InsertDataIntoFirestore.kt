package com.example.library_management_system.helper

import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

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

	fun insertUserWithBookIssue(email: String, name: String, password: String, type: String, bookName: String) {
		val db = FirebaseFirestore.getInstance()
		db.collection("Account").document(name).get().addOnSuccessListener { task ->
			val books = (task.get("Borrowed Books") as Map<*, *>).toMutableMap()

			var dt = Date()
			val c = Calendar.getInstance()
			c.time = dt
			c.add(Calendar.DATE, 7)
			dt = c.time

			books[bookName] = Timestamp(dt)

			val account = hashMapOf(
				"Borrowed Books" to books,
				"Total Books Ordered" to books.size
			)

			db.collection("Account").document(name).set(account, SetOptions.merge())
		}
	}

	fun cleanUserAccount(name: String, bookName: String) {
		val db = FirebaseFirestore.getInstance()
		db.collection("Account").document(name).get().addOnSuccessListener { task ->
			val books = (task.get("Borrowed Books") as Map<*, *>).toMutableMap()
			books.remove(bookName)

			val account = hashMapOf(
				"Borrowed Books" to books,
				"Total Books Ordered" to books.size
			)

			db.collection("Account").document(name).set(account, SetOptions.merge())
		}
	}

	fun cleanUserAccount(name: String) {
		val db = FirebaseFirestore.getInstance()

		val map: Map<*, *> = mapOf<String, Timestamp>()

		val account = hashMapOf(
			"Borrowed Books" to map,
			"Total Books Ordered" to 0
		)

		db.collection("Account").document(name).delete()
		db.collection("Account").document(name).set(account)
	}
}