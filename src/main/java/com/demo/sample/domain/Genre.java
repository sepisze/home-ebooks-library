package com.demo.sample.domain;

public class Genre {
	public Long ge_id;
	public String ge_name;

	public Genre() {
	}

	@Override
	public String toString() {
		return "Genre [ge_id=" + ge_id + ", ge_name=" + ge_name + "]";
	}
}
