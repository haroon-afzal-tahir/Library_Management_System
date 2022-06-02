package com.example.library_management_system

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.adapter.LibrarianViewAdapter
import com.example.library_management_system.model.User
import com.example.library_management_system.view.admin.AddLibrarian
import com.example.library_management_system.view.admin.UpdateLibrarian
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LibrarianFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibrarianFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private var recyclerView: RecyclerView? = null
	private lateinit var librarianViewClickListener: LibrarianViewAdapter.LibrarianViewClickListener
	private var originalLibrariansList: ArrayList<User> = ArrayList()

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
		return inflater.inflate(R.layout.fragment_librarian, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		var librarianList = ArrayList<User>()
		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "Librarian").get().addOnSuccessListener { documents ->
			for (document in documents) {
				librarianList.add(User(document.get("Email").toString(), document.get("Name").toString(), document.get("Type").toString()))
			}
			originalLibrariansList = librarianList
			recyclerView = getView()?.findViewById(R.id.fragment_librarian_librarians_list)
			recyclerView?.adapter = LibrarianViewAdapter(getView()?.context, object : LibrarianViewAdapter.LibrarianViewClickListener {
				override fun onClick(view: View?, position: Int) {
					val intent = Intent(getView()?.context, UpdateLibrarian::class.java)
					intent.putExtra("Name", originalLibrariansList[position].getName())
					startActivity(intent)
				}
			}, librarianList)
			recyclerView?.layoutManager = LinearLayoutManager(getView()?.context)
		}

		getView()?.findViewById<MaterialButton>(R.id.fragment_librarian_add_librarian)?.setOnClickListener {
			val intent = Intent(getView()?.context, AddLibrarian::class.java)
			intent.putExtra("Type", "Admin")
			startActivity(intent)
		}

		getView()?.findViewById<ImageButton>(R.id.admin_librarian_search_btn)?.setOnClickListener {
			val name = getView()?.findViewById<TextInputEditText>(R.id.admin_librarian_search)?.text.toString()

			if (originalLibrariansList.size != 0 && name.isNotEmpty()) {
				librarianList = ArrayList()

				for (librarian in originalLibrariansList) {
					if (librarian.getName().contains(name, ignoreCase = true)) {
						librarianList.add(librarian)
					}
				}
			}
			else {
				librarianList = originalLibrariansList
			}
			recyclerView = getView()?.findViewById(R.id.fragment_librarian_librarians_list)
			recyclerView?.adapter = LibrarianViewAdapter(getView()?.context, object : LibrarianViewAdapter.LibrarianViewClickListener {
				override fun onClick(view: View?, position: Int) {
					val intent = Intent(getView()?.context, UpdateLibrarian::class.java)
					intent.putExtra("Name", originalLibrariansList[position].getName())
					startActivity(intent)
				}
			}, librarianList)
			recyclerView?.layoutManager = LinearLayoutManager(getView()?.context)
		}

	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment LibrarianFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			LibrarianFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}