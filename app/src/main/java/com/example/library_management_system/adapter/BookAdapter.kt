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
	class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		var nameTV: TextView? = null
		var publisherTV:TextView? = null
		var pageCountTV:TextView? = null
		var dateTV:TextView? = null
		var bookIV: ImageView? = null

		init {
			nameTV = itemView.findViewById(R.id.idTVBookTitle)
			publisherTV = itemView.findViewById<TextView>(R.id.idTVpublisher)
			pageCountTV = itemView.findViewById<TextView>(R.id.idTVPageCount)
			dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
			bookIV = itemView.findViewById(R.id.idIVbook)
		}
	}

	private var context = context
	private var bookInfo = bookInfo
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
		return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_view, parent, false))
	}

	override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
		val book = bookInfo[position]
		holder.nameTV?.text = book.getTitle()
		holder.publisherTV?.text = book.getPublisher()
		holder.pageCountTV?.text = "No of Pages : ${book.getPageCount()}"
		holder.dateTV?.text = book.getPublishedDate()
		Picasso.get().load(book.getThumbnail()).into(holder.bookIV)

		holder.itemView.setOnClickListener {
			val intent = Intent(context, BookDetails::class.java)
			intent.putExtra("Title", book.getTitle())
			intent.putExtra("Subtitle", book.getSubtitle())
			intent.putExtra("Authors", book.getAuthors())
			intent.putExtra("Publisher", book.getPublisher())
			intent.putExtra("Description", book.getDescription())
			intent.putExtra("PageCount", book.getPageCount())
			intent.putExtra("Thumbnail", book.getThumbnail())
			intent.putExtra("PreviewLink", book.getPreviewLink())
			intent.putExtra("InfoLink", book.getInfoLink())
			intent.putExtra("BuyLink", book.getBuyLink())

			context.startActivity(intent)
		}
	}

	override fun getItemCount(): Int {
		return bookInfo.size
	}

}