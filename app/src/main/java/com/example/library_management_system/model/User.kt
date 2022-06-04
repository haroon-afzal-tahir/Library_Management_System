package com.example.library_management_system.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class User(email: String, name: String, type: String) {
	private var email: String
	private var name: String
	private var type: String
	private var borrowedBooks: Int = 0
	private var totalBooksOrdered: Int = 0
	private var returnedBooks: Int = 0


	fun getEmail() : String { return email }
	fun getName() : String { return name }
	fun getType() : String { return type }

	fun getBorrowedBooks() : Int { return borrowedBooks }
	fun getTotalBooksOrdered() : Int { return totalBooksOrdered }
	fun getBooksReturned() : Int { return returnedBooks }

	init {
		this.email = email
		this.name = name
		this.type = type

		val db = (FirebaseFirestore.getInstance()).collection("Account").document(name)

		db.get().addOnSuccessListener { task ->
			borrowedBooks = (task.get("Borrowed Books") as Map<*, *>).size
			totalBooksOrdered = task.get("Total Books Ordered").toString().toInt()
			returnedBooks = task.get("Total Books Returned").toString().toInt()

			Log.d("Borrowed Books", borrowedBooks.toString())
			Log.d("Total Books", totalBooksOrdered.toString())
			Log.d("Returned Books", returnedBooks.toString())
			return@addOnSuccessListener
		}
	}
}