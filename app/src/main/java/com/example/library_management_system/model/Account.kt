package com.example.library_management_system.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class Account {
	private var borrowed_books: Int = 0
	private var reserved_books: Int = 0
	private var returned_books: Int = 0
	private var lost_books: Int = 0


	fun setBorrowedBooks(borrowed_books: Int) { this.borrowed_books = borrowed_books }
	fun setReservedBooks(reserved_books: Int) { this.reserved_books = reserved_books }
	fun setReturnedBooks(returned_books: Int) { this.returned_books = returned_books }
	fun setLostBooks(lost_books: Int) { this.lost_books = lost_books }

	fun getBorrowedBooks() : Int { return borrowed_books }
	fun getReservedBooks() : Int { return reserved_books }
	fun getReturnedBooks() : Int { return returned_books }
	fun getLostBooks() : Int { return lost_books }

	fun calculateFine(name: String) : Int {
		var firebaseFirestore = FirebaseFirestore.getInstance()
		var info = firebaseFirestore.collection("Account").document(name)
		info.get().addOnSuccessListener { task ->
			task.get("Date of Submission")
		}
		return 0
	}
}