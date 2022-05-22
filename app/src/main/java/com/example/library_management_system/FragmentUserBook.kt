package com.example.library_management_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.library_management_system.adapter.BookAdapter
import com.example.library_management_system.model.Book
import org.json.JSONException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUserBook.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUserBook : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private var requestQueue: RequestQueue? = null
	private var bookInfoArrayList: ArrayList<Book>? = null
	private var progressBar: ProgressBar? = null
	private var searchEdt: EditText? = null
	private var searchBtn: ImageButton? = null


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
		return inflater.inflate(R.layout.fragment_user_book, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		progressBar = getView()?.findViewById(R.id.idLoadingPB)
		searchEdt = getView()?.findViewById(R.id.idEdtSearchBooks)
		searchBtn = getView()?.findViewById(R.id.idBtnSearch)

		searchBtn?.setOnClickListener {
			progressBar?.visibility = View.VISIBLE

			if (searchEdt?.text.toString().isEmpty()) {
				searchEdt?.error = "Please enter search query";
				return@setOnClickListener;
			}

			getBooksInfo(searchEdt?.text.toString())
		}
	}

	fun getBooksInfo(query: String) {
		bookInfoArrayList = ArrayList()

		requestQueue = Volley.newRequestQueue(activity)
		requestQueue?.cache?.clear()

		val url = "https://www.googleapis.com/books/v1/volumes?1=$query"

		val queue = Volley.newRequestQueue(activity)
		val booksObjRequest = JsonObjectRequest(
			Request.Method.GET, url, null,
			{ response ->
				progressBar!!.visibility = View.GONE
				// inside on response method we are extracting all our json data.
				try {
					val itemsArray = response.getJSONArray("items")
					for (i in 0 until itemsArray.length()) {
						val itemsObj = itemsArray.getJSONObject(i)
						val volumeObj = itemsObj.getJSONObject("volumeInfo")
						val title = volumeObj.optString("title")
						val subtitle = volumeObj.optString("subtitle")
						val authorsArray = volumeObj.getJSONArray("authors")
						val publisher = volumeObj.optString("publisher")
						val publishedDate = volumeObj.optString("publishedDate")
						val description = volumeObj.optString("description")
						val pageCount = volumeObj.optInt("pageCount")
						val imageLinks = volumeObj.optJSONObject("imageLinks")
						val thumbnail = imageLinks.optString("thumbnail")
						val previewLink = volumeObj.optString("previewLink")
						val infoLink = volumeObj.optString("infoLink")
						val saleInfoObj = itemsObj.optJSONObject("saleInfo")
						val buyLink = saleInfoObj.optString("buyLink")
						val authorsArrayList: ArrayList<String> = ArrayList()
						if (authorsArray.length() != 0) {
							for (j in 0 until authorsArray.length()) {
								authorsArrayList.add(authorsArray.optString(i))
							}
						}
						// after extracting all the data we are
						// saving this data in our modal class.
						val bookInfo = Book(
							title,
							subtitle,
							authorsArrayList,
							publisher,
							publishedDate,
							description,
							pageCount,
							thumbnail,
							previewLink,
							infoLink,
							buyLink
						)

						// below line is use to pass our modal
						// class in our array list.
						bookInfoArrayList!!.add(bookInfo)

						// below line is use to pass our
						// array list in adapter class.
						val adapter = view?.let { BookAdapter(it.context, bookInfoArrayList!!) }

						// below line is use to add linear layout
						// manager for our recycler view.
						val linearLayoutManager =
							LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
						val mRecyclerView = view?.findViewById(R.id.idRVBooks) as RecyclerView

						// in below line we are setting layout manager and
						// adapter to our recycler view.
						mRecyclerView.layoutManager = linearLayoutManager
						mRecyclerView.adapter = adapter
					}
				} catch (e: JSONException) {
					e.printStackTrace()
					// displaying a toast message when we get any error from API
					Toast.makeText(activity, "No Data Found$e", Toast.LENGTH_SHORT).show()
				}
			}) { error -> // also displaying error message in toast.
			Toast.makeText(activity, "Error found is $error", Toast.LENGTH_SHORT).show()
		}

		queue.add(booksObjRequest)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment FragmentUserBook.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			FragmentUserBook().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}