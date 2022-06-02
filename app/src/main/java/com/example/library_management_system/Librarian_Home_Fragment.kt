package com.example.library_management_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
 * Use the [Librarian_Home_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Librarian_Home_Fragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private var userView: RecyclerView? = null
	private lateinit var originalUsersList: ArrayList<User>

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
		return inflater.inflate(R.layout.fragment_librarian_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		var usersList = ArrayList<User>()
		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "User").get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				val doc = task.result.documents
				for (document in doc) {
					usersList.add(User(document.get("Email").toString(), document.get("Name").toString(), document.get("Type").toString()))
				}
				originalUsersList = usersList
				userView = getView()?.findViewById(R.id.userView)
				userView?.adapter = AdminHomeUserViewAdapter(getView()?.context, usersList)
				userView?.layoutManager = LinearLayoutManager(getView()?.context)
			}
		}

		getView()?.findViewById<ImageButton>(R.id.librarian_home_search_btn)?.setOnClickListener {
			val name = getView()?.findViewById<TextInputEditText>(R.id.librarian_home_search)?.text.toString()

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

			userView = getView()?.findViewById(R.id.userView)
			userView?.adapter = AdminHomeUserViewAdapter(getView()?.context, usersList)
			userView?.layoutManager = LinearLayoutManager(getView()?.context)
		}
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment Librarian_Home_Fragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			Librarian_Home_Fragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}