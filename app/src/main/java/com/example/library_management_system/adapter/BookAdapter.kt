package com.example.library_management_system.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.R
import com.example.library_management_system.model.Book
import com.example.library_management_system.view.user.BookDetails
import com.squareup.picasso.Picasso


class BookAdapter(context: Context, bookInfo: ArrayList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
	// creating variables for arraylist and context.
	private var bookInfoArrayList: ArrayList<Book>
	private var mContext: Context

	// creating constructor for array list and context.
	init {
		this.bookInfoArrayList = bookInfo
		this.mContext = context
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
		// inflating our layout for item of recycler view item.
		val view: View =
			LayoutInflater.from(parent.context).inflate(R.layout.book_view, parent, false)
		return BookViewHolder(view)
	}

	override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

		// inside on bind view holder method we are
		// setting ou data to each UI component.
		val bookInfo: Book = bookInfoArrayList[position]
		holder.nameTV.text = bookInfo.getTitle()
		holder.publisherTV.text = bookInfo.getPublisher()
		holder.pageCountTV.text = "No of Pages : " + bookInfo.getPageCount()
		holder.dateTV.text = bookInfo.getPublishedDate()

		// below line is use to set image from URL in our image view.
		Picasso.get().load(bookInfo.getThumbnail()).into(holder.bookIV)

		// below line is use to add on click listener for our item of recycler view.
		holder.itemView.setOnClickListener { // inside on click listener method we are calling a new activity
			// and passing all the data of that item in next intent.
			val i = Intent(mContext, BookDetails::class.java)
			i.putExtra("Title", bookInfo.getTitle())
			i.putExtra("Subtitle", bookInfo.getSubtitle())
			i.putExtra("Authors", bookInfo.getAuthors())
			i.putExtra("Publisher", bookInfo.getPublisher())
			i.putExtra("PublishedDate", bookInfo.getPublishedDate())
			i.putExtra("Description", bookInfo.getDescription())
			i.putExtra("PageCount", bookInfo.getPageCount())
			i.putExtra("Thumbnail", bookInfo.getThumbnail())
			i.putExtra("PreviewLink", bookInfo.getPreviewLink())
			i.putExtra("InfoLink", bookInfo.getInfoLink())
			i.putExtra("BuyLink", bookInfo.getBuyLink())

			// after passing that data we are
			// starting our new  intent.
			mContext.startActivity(i)
		}
	}

	override fun getItemCount(): Int {
		// inside get item count method we
		// are returning the size of our array list.
		return bookInfoArrayList.size
	}

	class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		// below line is use to initialize
		// our text view and image views.
		var nameTV: TextView
		var publisherTV: TextView
		var pageCountTV: TextView
		var dateTV: TextView
		var bookIV: ImageView

		init {
			nameTV = itemView.findViewById(R.id.idTVBookTitle)
			publisherTV = itemView.findViewById(R.id.idTVpublisher)
			pageCountTV = itemView.findViewById(R.id.idTVPageCount)
			dateTV = itemView.findViewById(R.id.idTVDate)
			bookIV = itemView.findViewById(R.id.idIVbook)
		}
	}

}