package com.demo.sample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.sample.dao.BooksLibraryDao;
import com.demo.sample.domain.Book;

@Service
public class BooksLibraryService {

	private static final Logger logger = LoggerFactory.getLogger(BooksLibraryService.class);

	private final BooksLibraryDao booksInfoDao;

	public BooksLibraryService(BooksLibraryDao booksInfoDao) {
		this.booksInfoDao = booksInfoDao;
	}

	public Book getBookByUid(String instanceId, String bookUid) {
		long ts = System.currentTimeMillis();
		logger.debug("BooksLibraryService.getBookByUid(" + instanceId + "): start");

		Book bookInfo = booksInfoDao.getBookByUid(instanceId, bookUid);

		logger.debug("BooksLibraryService.getBookByUid(" + instanceId + "): end time=[" + (System.currentTimeMillis() - ts) + "ms]");

		return bookInfo;
	}

}
