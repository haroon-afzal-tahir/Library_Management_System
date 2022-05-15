package com.example.library_management_system.model

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class User(email: String, name: String, type: String) {
	private var email: String
	private var name: String
	private var type: String
	private var fine: Int = 0

	private lateinit var account: Account

	fun getEmail() : String { return email }
	fun getName() : String { return name }
	fun getType() : String { return type }
	fun getAccount() : Account { return account }

	fun setEmail(email: String) { this.email = email }
	fun setName(name: String) { this.name = name }
	fun setType(type: String) { this.type = type }

	init {
		this.email = email
		this.name = name
		this.type = type

		account = Account()

		val db = (FirebaseFirestore.getInstance()).collection("Account").document(name)

		db.get().addOnSuccessListener { task ->
			account.setBorrowedBooks((task.get("Borrowed Books") as Map<String, Timestamp>).size)
			account.setTotalBooksOrdered(task.get("Total Books Ordered").toString().toInt())

			Log.d("Borrowed Books", account.getBorrowedBooks().toString())
			Log.d("Total Books", account.getTotalBooksOrdered().toString())
		}
	}
}