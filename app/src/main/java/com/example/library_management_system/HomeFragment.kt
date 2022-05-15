package com.example.library_management_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.adapter.UserViewAdapter
import com.example.library_management_system.model.User
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

	private lateinit var recyclerView: RecyclerView
	private lateinit var myAdapter: UserViewAdapter

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
		var view = inflater.inflate(R.layout.fragment_home, container, false)

		recyclerView = view.findViewById(R.id.userView)

		var usersList: ArrayList<User> = ArrayList()

		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "User").get().addOnSuccessListener { documents ->
			for (document in documents) {
				usersList.add(User(document.get("Email").toString(), document.get("Name").toString(), document.get("Type").toString()))
			}
		}

		recyclerView.adapter = UserViewAdapter(view.context, usersList)
		recyclerView.layoutManager = LinearLayoutManager(view.context)

		return view
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