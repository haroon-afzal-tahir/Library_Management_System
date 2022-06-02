package com.example.library_management_system.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class Account {
	private var borrowedBooks: Int = 0
	private var totalBooksOrdered: Int = 0

	fun setBorrowedBooks(borrowedBooks: Int) { this.borrowedBooks = borrowedBooks }
	fun setTotalBooksOrdered(totalBooksOrdered: Int) { this.totalBooksOrdered = totalBooksOrdered }
	fun getBorrowedBooks() : Int { return borrowedBooks }
	fun getTotalBooksOrdered() : Int { return totalBooksOrdered }


}