package com.android.akash.newsearch;

public class Book {
	
	private String title;
	private String author;
	private String publication;
	private String[] categories;
	private String id;
	
	public Book(String title,String author,String publication,String id ){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
		this.id=id;
	}
	
	public Book(){
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
