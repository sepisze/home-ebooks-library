package com.demo.sample.services;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.sample.dao.BooksLibraryDao;
import com.demo.sample.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BooksLibraryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(BooksLibraryServiceTest.class);

	@Mock
	private BooksLibraryDao booksInfoDao;

	private BooksLibraryService booksLibraryService;

	@Test
	@DisplayName("finds book by UID")
	public void testGetBookByUid001() {
		String instanceId = UUID.randomUUID().toString();
		logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): start");

		// given
		booksLibraryService = new BooksLibraryService(booksInfoDao);
		String bookUid = "f33e282a-e8f1-4b82-b184-293dc4731550";

		Book expBookInfo = new Book();
		expBookInfo.bk_id = 1L;
		expBookInfo.bk_uid = bookUid;
		expBookInfo.bk_main_title = "Pan Tadeusz";
		Mockito.when(booksInfoDao.getBookByUid(instanceId, bookUid)).thenReturn(expBookInfo);

		// when
		Book bookInfo = booksLibraryService.getBookByUid(instanceId, bookUid);

		// then
		//logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): bookInfo=[" + bookInfo + "]");
		verify(booksInfoDao).getBookByUid(anyString(), anyString());
		assertNotNull(bookInfo);

		//logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): end");
	}

	@Test
	@DisplayName("doesn't find book by UID")
	public void testGetBookByUid002() {
		String instanceId = UUID.randomUUID().toString();
		logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): start");

		// given
		booksLibraryService = new BooksLibraryService(booksInfoDao);
		String bookUid = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";

		Mockito.when(booksInfoDao.getBookByUid(instanceId, bookUid)).thenReturn(null);

		// when
		Book bookInfo = booksLibraryService.getBookByUid(instanceId, bookUid);

		// then
		//logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): bookInfo=[" + bookInfo + "]");
		verify(booksInfoDao).getBookByUid(anyString(), anyString());
		assertNull(bookInfo);

		//logger.debug("BooksLibraryServiceTest.testGetBookByUid(" + instanceId + "): end");
	}

}
