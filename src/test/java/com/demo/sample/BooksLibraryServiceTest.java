package com.demo.sample;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.sample.dao.BooksLibraryDao;
import com.demo.sample.domain.Book;
import com.demo.sample.services.BooksLibraryService;

@ExtendWith(MockitoExtension.class)
public class BooksLibraryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(BooksLibraryServiceTest.class);

	@Mock
	private BooksLibraryDao booksInfoDao;

	@Test
	void testGetBookByUid() {
		logger.debug("BooksLibraryServiceTest.testGetBookByUid(): start");

		String instanceId = UUID.randomUUID().toString();
		String bookUid1 = "f33e282a-e8f1-4b82-b184-293dc4731550";
		String bookUid2 = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";

		Book expBookInfo1 = new Book();
		expBookInfo1.uid = bookUid1;
		expBookInfo1.mainTitle = "Pan Tadeusz";
		Mockito.when(booksInfoDao.getBookByUid(instanceId, bookUid1)).thenReturn(expBookInfo1);
		Mockito.when(booksInfoDao.getBookByUid(instanceId, bookUid2)).thenReturn(null);

		BooksLibraryService booksLibraryService = new BooksLibraryService(booksInfoDao);
		Book bookInfo1 = booksLibraryService.getBookByUid(instanceId, bookUid1);
		Book bookInfo2 = booksLibraryService.getBookByUid(instanceId, bookUid2);
		logger.debug("BooksLibraryServiceTest.testGetBookByUid(): bookInfo1=[" + bookInfo1 + "]");
		logger.debug("BooksLibraryServiceTest.testGetBookByUid(): bookInfo2=[" + bookInfo2 + "]");

		assertNotNull(bookInfo1);
		assertNull(bookInfo2);
	}

}
