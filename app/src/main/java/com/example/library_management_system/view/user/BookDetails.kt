package com.example.library_management_system.view.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.squareup.picasso.Picasso


class BookDetails : AppCompatActivity() {

	private lateinit var title: String
	private lateinit var subtitle: String
	private lateinit var authors: ArrayList<String>
	private lateinit var publisher: String
	private lateinit var publishedDate: String
	private lateinit var description: String
	private var pageCount: Int = 0
	private lateinit var thumbnail: String
	private lateinit var previewLink: String
	private lateinit var infoLink: String
	private lateinit var buyLink: String

	private lateinit var titleTV: TextView
	private lateinit var subtitleTV: TextView
	private lateinit var publisherTV: TextView
	private lateinit var descTV: TextView
	private lateinit var pageTV: TextView
	private lateinit var publishDateTV: TextView
	private lateinit var previewBtn: Button
	private lateinit var buyBtn: Button
	private lateinit var bookIV: ImageView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.book_details)

		titleTV = findViewById(R.id.idTVTitle)
		subtitleTV = findViewById(R.id.idTVSubTitle)
		publisherTV = findViewById(R.id.idTVpublisher)
		descTV = findViewById(R.id.idTVDescription)
		pageTV = findViewById(R.id.idTVNoOfPages)
		publishDateTV = findViewById(R.id.idTVPublishDate)
		previewBtn = findViewById(R.id.idBtnPreview)
		buyBtn = findViewById(R.id.idBtnBuy)
		bookIV = findViewById(R.id.idIVbook)

		// getting the data which we have passed from our adapter class.
		title = intent.getStringExtra("Title").toString()
		subtitle = intent.getStringExtra("Subtitle").toString()
		publisher = intent.getStringExtra("Publisher").toString()
		publishedDate = intent.getStringExtra("PublishedDate").toString()
		description = intent.getStringExtra("Description").toString()
		pageCount = intent.getIntExtra("PageCount", 0).toString().toInt()
		thumbnail = intent.getStringExtra("Thumbnail").toString()
		previewLink = intent.getStringExtra("PreviewLink").toString()
		infoLink = intent.getStringExtra("InfoLink").toString()
		buyLink = intent.getStringExtra("BuyLink").toString()

		titleTV.text = title;
		subtitleTV.text = subtitle;
		publisherTV.text = publisher;
		publishDateTV.text = "Published On : $publishedDate"
		descTV.text = description
		pageTV.text = "No Of Pages : $pageCount"
		Picasso.get().load(thumbnail).into(bookIV);

		previewBtn.setOnClickListener {
			if (previewLink.isEmpty()) {
				Toast.makeText(this, "No preview Link present", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val uri: Uri = Uri.parse(previewLink)
			val i = Intent(Intent.ACTION_VIEW, uri)
			startActivity(i)
		}

		buyBtn.setOnClickListener {
			if (buyLink.isEmpty()) {
				Toast.makeText(this, "No buy page present for this book", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val uri = Uri.parse(buyLink)
			val i = Intent(Intent.ACTION_VIEW, uri)
			startActivity(i)
		}
	}
}