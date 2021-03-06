package com.example.library_management_system.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.R
import com.example.library_management_system.model.User
import com.google.firebase.firestore.FirebaseFirestore

class AdminHomeUserViewAdapter(ct: Context?, user: ArrayList<User>) : RecyclerView.Adapter<AdminHomeUserViewAdapter.MyViewHolder>() {
	class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	private var context: Context? = ct
	private var users: ArrayList<User> = ArrayList()
	private var usersAll: ArrayList<User> = ArrayList()

	init {
		for (i in user.indices) {
			users.add(User(user[i].getEmail(), user[i].getName(), user[i].getType()))
			usersAll.add(users[i])
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val inflater: LayoutInflater = LayoutInflater.from(context)
		if (users[0].getType() == "User") {
			return MyViewHolder(inflater.inflate(R.layout.user_row, parent, false))
		} else {
			return MyViewHolder(inflater.inflate(R.layout.librarian_row, parent, false))
		}
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		if (users[position].getType() == "User") {
			holder.itemView.findViewById<TextView>(R.id.userName).text = users[position].getName()
			holder.itemView.findViewById<TextView>(R.id.booksIssued).text = users[position].getBorrowedBooks().toString()
			holder.itemView.findViewById<TextView>(R.id.booksReturned).text = users[position].getBooksReturned().toString()
		} else {
			holder.itemView.findViewById<TextView>(R.id.librarianName).text = users[position].getName()
		}
	}

	override fun getItemCount(): Int {
		return users.size
	}
}