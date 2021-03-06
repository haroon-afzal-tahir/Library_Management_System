package com.example.library_management_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.adapter.AdminHomeUserViewAdapter
import com.example.library_management_system.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private var userView: RecyclerView? = null
	private var librarianView: RecyclerView? = null

	private lateinit var originalUsersList: ArrayList<User>
	private lateinit var originalLibrariansList: ArrayList<User>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		var usersList: ArrayList<User> = ArrayList()
		var librariansList: ArrayList<User> = ArrayList()

		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "User").get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				val doc = task.result
				if (doc.documents.isNotEmpty()) {
					for (document in doc.documents) {
						usersList.add(User(document.get("Email").toString(), document.get("Name").toString(), document.get("Type").toString()))
					}
				}
				originalUsersList = usersList
				userView = getView()?.findViewById(R.id.userView)
				userView?.adapter = AdminHomeUserViewAdapter(getView()?.context, usersList)
				userView?.layoutManager = LinearLayoutManager(getView()?.context)
			}
		}

		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "Librarian").get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				val doc = task.result
				if (doc.documents.isNotEmpty()) {
					for (document in doc.documents) {
						librariansList.add(User(document.get("Email").toString(), document.get("Name").toString(), "Librarian"))
					}
				}
				originalLibrariansList = librariansList
				librarianView = getView()?.findViewById(R.id.librariansView)
				librarianView?.adapter = AdminHomeUserViewAdapter(getView()?.context, librariansList)
				librarianView?.layoutManager = LinearLayoutManager(getView()?.context)
			}
		}

		getView()?.findViewById<ImageButton>(R.id.admin_home_search_btn)?.setOnClickListener {
			val name = getView()?.findViewById<TextInputEditText>(R.id.admin_home_search)?.text.toString()

			if (originalUsersList.size != 0 && name.isNotEmpty()) {
				usersList = ArrayList()
				for (user in originalUsersList) {
					if (user.getName().contains(name, ignoreCase = true)) {
						usersList.add(user)
					}
				}
			}
			else {
				usersList = originalUsersList
			}
			userView?.adapter = AdminHomeUserViewAdapter(getView()?.context, usersList)
			userView?.layoutManager = LinearLayoutManager(getView()?.context)

			if (originalLibrariansList.size != 0 && name.isNotEmpty()) {
				librariansList = ArrayList()
				for (librarian in originalLibrariansList) {
					if (librarian.getName().contains(name, ignoreCase = true)) {
						librariansList.add(librarian)
					}
				}
			}
			else {
				librariansList = originalLibrariansList
			}
			librarianView?.adapter = AdminHomeUserViewAdapter(getView()?.context, librariansList)
			librarianView?.layoutManager = LinearLayoutManager(getView()?.context)
		}
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment HomeFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			HomeFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}