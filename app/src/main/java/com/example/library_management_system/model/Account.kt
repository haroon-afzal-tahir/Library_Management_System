package com.example.library_management_system.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class Account {
	private var borrowedBooks: Int = 0

	fun setBorrowedBooks(borrowedBooks: Int) { this.borrowedBooks = borrowedBooks }
	fun getBorrowedBooks() : Int { return borrowedBooks }
	fun calculateFine(name: String, bookName: String) : Int {
		val firebaseFirestore = FirebaseFirestore.getInstance()
		val info = firebaseFirestore.collection("Account").document(name)
		var days: Long = 0
		info.get().addOnSuccessListener { task ->

			val arrays: Map<String, Timestamp> = task.get("Borrowed Books") as Map<String, Timestamp>
			val submissionDate = (arrays[bookName] as Timestamp).toDate()
			val todayDate = Timestamp.now().toDate()

			if (todayDate > submissionDate) {
				days = TimeUnit.MILLISECONDS.toDays(todayDate.time - submissionDate.time)
			}
		}
		return days.toInt()
	}
}