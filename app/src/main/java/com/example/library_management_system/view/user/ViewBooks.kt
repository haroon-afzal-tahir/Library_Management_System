package com.example.library_management_system.view.user

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.library_management_system.R
import com.example.library_management_system.adapter.BookAdapter
import com.example.library_management_system.model.Book
import org.json.JSONException


class ViewBooks : AppCompatActivity() {

	// creating variables for our request queue,
	// array list, progressbar, edittext,
	// image button and our recycler view.
	private lateinit var mRequestQueue: RequestQueue
	private lateinit var bookInfoArrayList: ArrayList<Book>
	private lateinit var progressBar: ProgressBar
	private lateinit var searchEdt: EditText
	private lateinit var searchBtn: ImageButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.fragment_user_book)

		// initializing our views.
		progressBar = findViewById<ProgressBar>(R.id.idLoadingPB)
		searchEdt = findViewById<EditText>(R.id.idEdtSearchBooks)
		searchBtn = findViewById<ImageButton>(R.id.idBtnSearch)

		// initializing on click listener for our button.
		searchBtn.setOnClickListener(View.OnClickListener {
			progressBar.visibility = View.VISIBLE

			// checking if our edittext field is empty or not.
			if (searchEdt.text.toString().isEmpty()) {
				searchEdt.error = "Please enter search query"
				return@OnClickListener
			}
			// if the search query is not empty then we are
			// calling get book info method to load all
			// the books from the API.
			getBooksInfo(searchEdt.text.toString())
		})
	}

	private fun getBooksInfo(query: String) {

		// creating a new array list.
		bookInfoArrayList = ArrayList()

		// below line is use to initialize
		// the variable for our request queue.
		mRequestQueue = Volley.newRequestQueue(this@ViewBooks)

		// below line is use to clear cache this
		// will be use when our data is being updated.
		mRequestQueue.cache.clear()

		// below is the url for getting data from API in json format.
		val url = "https://www.googleapis.com/books/v1/volumes?q=$query"

		// below line we are  creating a new request queue.
		val queue = Volley.newRequestQueue(this@ViewBooks)


		// below line is use to make json object request inside that we
		// are passing url, get method and getting json object.
		val booksObjRequest = JsonObjectRequest(
			Request.Method.GET, url, null,
			{ response ->
				progressBar.visibility = View.GONE
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
						val thumbnail = imageLinks?.optString("thumbnail")
						val previewLink = volumeObj.optString("previewLink")
						val infoLink = volumeObj.optString("infoLink")
						val saleInfoObj = itemsObj.optJSONObject("saleInfo")
						val buyLink = saleInfoObj?.optString("buyLink")
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
						bookInfoArrayList.add(bookInfo)

						// below line is use to pass our
						// array list in adapter class.
						val adapter = BookAdapter(this@ViewBooks, bookInfoArrayList)

						// below line is use to add linear layout
						// manager for our recycler view.
						val linearLayoutManager =
							LinearLayoutManager(this@ViewBooks, RecyclerView.VERTICAL, false)
						val mRecyclerView = findViewById<View>(R.id.idRVBooks) as RecyclerView

						// in below line we are setting layout manager and
						// adapter to our recycler view.
						mRecyclerView.layoutManager = linearLayoutManager
						mRecyclerView.adapter = adapter
					}
				} catch (e: JSONException) {
					e.printStackTrace()
					// displaying a toast message when we get any error from API
					Toast.makeText(this@ViewBooks, "No Data Found$e", Toast.LENGTH_SHORT).show()
				}
			}) { error -> // also displaying error message in toast.
			Toast.makeText(this@ViewBooks, "Error found is $error", Toast.LENGTH_SHORT).show()
		}
		// at last we are adding our json object
		// request in our request queue.
		queue.add(booksObjRequest)
	}
}