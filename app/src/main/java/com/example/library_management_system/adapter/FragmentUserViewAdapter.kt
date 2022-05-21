package com.example.library_management_system.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.R
import com.example.library_management_system.model.User
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore

class FragmentUserViewAdapter(ct: Context?, user: ArrayList<User>) : RecyclerView.Adapter<FragmentUserViewAdapter.MyViewHolder>() {

	class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private var rootView: View
		var name: String
		init {
			rootView = itemView
			name = itemView.findViewById<TextView>(R.id.crud_user_user_name).text.toString()
			itemView.setOnClickListener {
				Log.d("User: ", "$position User $name")
			}

			itemView.findViewById<Button>(R.id.crud_user_delete_button).setOnClickListener {
				val db = FirebaseFirestore.getInstance()
				db.collection("Users").document(name).delete()
				Log.d("User: ", "User $name")
			}
		}
	}

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
		return MyViewHolder(inflater.inflate(R.layout.crud_user, parent, false))
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.itemView.findViewById<TextView>(R.id.crud_user_user_name).text = users[position].getName()
		holder.name = users[position].getName()
	}

	override fun getItemCount(): Int {
		return users.size
	}
}