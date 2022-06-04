package com.example.library_management_system.model

class Account {
	private var borrowedBooks: Int = 0
	private var totalBooksOrdered: Int = 0
	private var booksReturned: Int = 0

	fun setBorrowedBooks(borrowedBooks: Int) { this.borrowedBooks = borrowedBooks }
	fun setTotalBooksOrdered(totalBooksOrdered: Int) { this.totalBooksOrdered = totalBooksOrdered }
	fun setBooksReturned(booksReturned: Int) { this.booksReturned = booksReturned }

	fun getBorrowedBooks() : Int { return borrowedBooks }
	fun getTotalBooksOrdered() : Int { return totalBooksOrdered }
	fun getBooksReturned() : Int  { return booksReturned }
}