package com.demo.sample.controllers;

/*
 * https://datatracker.ietf.org/doc/html/rfc9457
 */
public class HttpErrorResponse {

	public String type;     // 3.1.1
	public Integer status;  // 3.1.2
	public String title;    // 3.1.3
	public String detail;   // 3.1.4
	public String instance; // 3.1.5

	public HttpErrorResponse() {
	}

	@Override
	public String toString() {
		return "HttpErrorResponse [type=" + type + ", status=" + status + ", title=" + title + ", detail=" + detail
				+ ", instance=" + instance + "]";
	}

}
