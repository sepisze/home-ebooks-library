package com.demo.sample.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {

	public String uid;
	public String mainTitle;
	public List<String> titles;
	public List<String> authors;

	public Book() {
		this.titles = new ArrayList<>();
		this.authors = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Book [uid=" + uid + ", mainTitle=" + mainTitle + ", titles=" + titles + ", authors=" + authors + "]";
	}

}
