package com.demo.sample.domain;

public class Author {
	public Long au_id;
	public String au_last_name;
	public String au_first_name;

	public Author() {
	}

	@Override
	public String toString() {
		return "Author [au_id=" + au_id + ", au_last_name=" + au_last_name + ", au_first_name=" + au_first_name + "]";
	}

}
