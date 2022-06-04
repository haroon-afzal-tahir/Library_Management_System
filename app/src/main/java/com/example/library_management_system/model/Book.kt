package com.example.library_management_system.model

class Book(title: String, subtitle: String, authors: ArrayList<String>, publisher: String, publishedDate: String, description: String, pageCount: Int, thumbnail: String?, previewLink: String, infoLink : String?, buyLink: String?) {
	private var title: String
	private var subtitle: String
	private var authors: ArrayList<String>
	private var publisher: String
	private var publishedDate: String
	private var description: String
	private var pageCount: Int = 0
	private var thumbnail: String?
	private var previewLink: String
	private var infoLink: String?
	private var buyLink: String?

	fun getTitle() : String { return title }
	fun getSubtitle() : String { return subtitle }
	fun getAuthors() : ArrayList<String> { return authors }
	fun getPublisher() : String { return publisher }
	fun getPublishedDate() : String { return publishedDate }
	fun getDescription() : String { return description }
	fun getPageCount() : Int { return pageCount }
	fun getThumbnail() : String? { return thumbnail }
	fun getPreviewLink() : String { return previewLink }
	fun getInfoLink() : String? { return infoLink }
	fun getBuyLink() : String? { return buyLink }

	fun setTitle(title : String) { this.title = title }
	fun setSubtitles(subtitle : String) { this.subtitle = subtitle }
	fun setAuthors(authors : ArrayList<String>) { this.authors = authors }
	fun setPublisher(publisher : String) { this.publisher = publisher }
	fun setPublishedDate(publishedDate : String) { this.publishedDate = publishedDate }
	fun setDescription(description : String) { this.description = description }
	fun setPageCount(pageCount : Int) { this.pageCount = pageCount }
	fun setThumbnail(thumbnail : String) { this.thumbnail = thumbnail }
	fun setPreviewLink(previewLink : String) { this.previewLink = previewLink }
	fun setBuyLink(buyLink : String) { this.buyLink = buyLink }

	init {
		this.title = title
		this.subtitle = subtitle
		this.authors = authors
		this.publisher = publisher
		this.publishedDate = publishedDate
		this.description = description
		this.pageCount = pageCount
		this.thumbnail = thumbnail
		this.previewLink = previewLink
		this.infoLink = infoLink
		this.buyLink = buyLink
	}


}