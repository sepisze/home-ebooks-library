package com.demo.sample.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Book {

	public Long bk_id;
	public String bk_uid;
	public String bk_main_title;
	public List<Title> titles;
	public List<Author> authors;
	public List<Genre> genres;
	public String bk_cover; // plik graficzny zakodowany w Base64
	public String bk_description;
	public Language language;
	public String bk_url;
	public BigDecimal bk_score;
	public BigDecimal bk_my_score;
	public BigDecimal bk_pages;
	public BigDecimal bk_progress;
	public Status status;

	public Book() {
		this.titles = new ArrayList<>();
		this.authors = new ArrayList<>();
		this.genres = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Book [bk_id=" + bk_id + ", bk_uid=" + bk_uid + ", bk_main_title=" + bk_main_title + ", titles=" + titles
				+ ", authors=" + authors + "]";
	}

}
