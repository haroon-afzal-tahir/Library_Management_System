package com.example.library_management_system

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.adapter.FragmentUserViewAdapter
import com.example.library_management_system.model.User
import com.example.library_management_system.view.admin.AddUser
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLibrarianUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLibrarianUserFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private var recyclerView: RecyclerView? = null
	private val usersList: ArrayList<User> = ArrayList()

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
		return inflater.inflate(R.layout.fragment_librarian_user, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val db = FirebaseFirestore.getInstance()
		db.collection("Users").whereEqualTo("Type", "User").get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				val doc = task.result
				for (document in doc.documents) {
					usersList.add(User(document.get("Email").toString(), document.get("Name").toString(), document.get("Type").toString()))
				}

				recyclerView = getView()?.findViewById(R.id.fragment_user_users_list)
				recyclerView?.adapter = FragmentUserViewAdapter(getView()?.context, usersList)
				recyclerView?.layoutManager = LinearLayoutManager(getView()?.context)
			}
		}

		getView()?.findViewById<MaterialButton>(R.id.fragment_user_add_user)?.setOnClickListener {
			val intent = Intent(getView()?.context, AddUser::class.java)
			intent.putExtra("Type", "Librarian")
			startActivity(intent)
		}
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment Fragment_Librarian_User_Fragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			FragmentLibrarianUserFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}