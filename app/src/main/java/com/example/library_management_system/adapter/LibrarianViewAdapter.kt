package com.example.library_management_system.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.R
import com.example.library_management_system.model.User
import com.example.library_management_system.view.admin.UpdateLibrarian
import com.google.firebase.firestore.FirebaseFirestore

class LibrarianViewAdapter(ct: Context?, librarianViewClickListener: LibrarianViewClickListener, librarian: ArrayList<User>) : RecyclerView.Adapter<LibrarianViewAdapter.MyViewHolder>() {
	class MyViewHolder(itemView: View, librarianViewClickListener: LibrarianViewClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
		var name: String
		var librarianViewClickListener: LibrarianViewClickListener
		init {
			this.librarianViewClickListener = librarianViewClickListener
			name = itemView.findViewById<TextView?>(R.id.crud_librarian_librarian_name).text.toString()
			itemView.setOnClickListener(this)

			itemView.findViewById<Button>(R.id.crud_librarian_delete_button).setOnClickListener {
				val db = FirebaseFirestore.getInstance()
				db.collection("Users").document(name).delete()
			}
		}

		override fun onClick(p0: View?) {
			librarianViewClickListener.onClick(p0, adapterPosition)
		}
	}


	private var context: Context? = ct
	private var librarians: ArrayList<User> = ArrayList()
	private var librariansAll: ArrayList<User> = ArrayList()
	var librarianViewClickListener: LibrarianViewClickListener

	init {
		this.librarianViewClickListener = librarianViewClickListener
		for (i in librarian.indices) {
			librarians.add(User(librarian[i].getEmail(), librarian[i].getName(), librarian[i].getType()))
			librariansAll.add(librarians[i])
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val inflater: LayoutInflater = LayoutInflater.from(context)
		return MyViewHolder(inflater.inflate(R.layout.crud_librarian, parent, false), librarianViewClickListener)
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.itemView.findViewById<TextView>(R.id.crud_librarian_librarian_name).text = librarians[position].getName()
		holder.name = librarians[position].getName()
		holder.librarianViewClickListener = librarianViewClickListener
	}

	override fun getItemCount(): Int {
		return librarians.size
	}

	interface LibrarianViewClickListener {
		fun onClick(view: View?, position: Int)
	}
}