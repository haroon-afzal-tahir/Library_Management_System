package com.example.library_management_system

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.library_management_system.helper.InsertDataIntoFirestore
import com.example.library_management_system.view.user.PayActivity
import com.example.library_management_system.view.user.ViewBooks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUserHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUserHome : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

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
		return inflater.inflate(R.layout.fragment_user_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val db = FirebaseFirestore.getInstance()
		db.collection("Account").document(activity?.intent?.getStringExtra("Name").toString()).get().addOnSuccessListener { task ->
			val map = task.get("Borrowed Books") as Map<*, *>
			val returnBooks =  task.get("Total Books Ordered").toString().toInt()
			getView()?.findViewById<TextView>(R.id.issuedBooks)?.text = map.size.toString()
			getView()?.findViewById<TextView>(R.id.returnedBooks)?.text = task.get("Total Books Returned").toString()
		}

		getView()?.findViewById<CardView>(R.id.info_user)?.setOnClickListener {
			val intent = Intent(getView()?.context, PayActivity::class.java)
			intent.putExtra("Name", activity?.intent?.getStringExtra("Name").toString())
			startActivity(intent)
		}

		getView()?.findViewById<Button>(R.id.view_books)?.setOnClickListener {
			if (InsertDataIntoFirestore.getFine() <= 200) {
				val intent = Intent(getView()?.context, ViewBooks::class.java)
				startActivity(intent)
			} else {
				Toast.makeText(getView()?.context, "First, Pay Fine", Toast.LENGTH_SHORT).show()
			}
		}
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment FragmentUserHome.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			FragmentUserHome().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}