package com.example.library_management_system.helper

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

object InsertDataIntoFirestore {

	private lateinit var name: String
	private var fine: Int = 0


	fun getUser() : String {
		return name
	}

	fun getFine() : Int {
		return fine
	}

	fun setFine(fine: Int) {
		this.fine = fine
	}
	fun setUser(name: String) {
		this.name = name
	}

	fun insertUser(email: String, name: String, password: String, type: String) {
		val user = hashMapOf(
			"Email" to email,
			"Name" to name,
			"Password" to password,
			"Type" to type
		)

		val account = hashMapOf(
			"Borrowed Books" to {},
			"Total Books Ordered" to 0,
			"Total Books Returned" to 0
		)

		val db = FirebaseFirestore.getInstance()
		db.collection("Users").document(name).set(user, SetOptions.merge())
		db.collection("Account").document(name).set(account, SetOptions.merge())
	}

	fun insertUserWithBookIssue(bookName: String) {
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
				"Total Books Ordered" to books.size,
				"Total Books Returned" to task.get("Total Books Returned").toString().toInt()
			)

			db.collection("Account").document(name).delete()
			db.collection("Account").document(name).set(account)
		}
	}

	fun cleanUserAccount(name: String, bookName: String) {
		val db = FirebaseFirestore.getInstance()
		db.collection("Account").document(name).get().addOnSuccessListener { task ->
			val books = (task.get("Borrowed Books") as Map<*, *>).toMutableMap()
			books.remove(bookName)

			val account = hashMapOf(
				"Borrowed Books" to books,
				"Total Books Ordered" to books.size,
				"Total Books Returned" to task.get("Total Books Returned").toString().toInt() + 1
			)

			db.collection("Account").document(name).delete()
			db.collection("Account").document(name).set(account)
		}
	}

	fun cleanUserAccount(name: String) {
		val db = FirebaseFirestore.getInstance()
		db.collection("Account").document(name).get().addOnSuccessListener { task ->
			val size = (task.get("Borrowed Books") as Map<*, *>).size + task.get("Total Books Returned").toString().toInt()
			val map: Map<*, *> = mapOf<String, Timestamp>()

			val account = hashMapOf(
				"Borrowed Books" to map,
				"Total Books Ordered" to 0,
				"Total Books Returned" to size
			)

			db.collection("Account").document(name).delete()
			db.collection("Account").document(name).set(account)
		}


	}
}