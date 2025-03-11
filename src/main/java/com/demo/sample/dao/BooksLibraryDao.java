package com.demo.sample.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.sample.domain.Book;

import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class BooksLibraryDao {

	private static final Logger logger = LoggerFactory.getLogger(BooksLibraryDao.class);

	private final DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public BooksLibraryDao(DataSource dataSource) {
		this.dataSource = dataSource;

		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}

	// DEBUG:
	private List<Book> initTestData() {
		List<Book> lst = new ArrayList<>();

		Book bookInfo1 = new Book();
		bookInfo1.uid   = "f33e282a-e8f1-4b82-b184-293dc4731550";
		bookInfo1.mainTitle = "Pan Tadeusz";
		bookInfo1.authors.add("Adam Mickiewicz");
		Book bookInfo2 = new Book();
		bookInfo2.uid   = "821e4ca9-fa2a-4f64-957e-860a02e548be";
		bookInfo2.mainTitle = "Odniem i mieczem";
		bookInfo2.authors.add("Henryk Sienkiewicz");
		Book bookInfo3 = new Book();
		bookInfo3.uid   = "6f2dfe94-473f-421d-8a80-ba1425f77955";
		bookInfo3.mainTitle = "Potop";
		bookInfo3.authors.add("Henryk Sienkiewicz");

		lst.add(bookInfo1);
		lst.add(bookInfo2);
		lst.add(bookInfo3);

		return lst;
	}

	public Book getBookByUid(String instanceId, String bookUid) {
		long ts = System.currentTimeMillis();
		logger.debug("BooksLibraryDao.getBookByUid(" + instanceId + "): start");

		Book bookInfo = null;
		if (bookUid != null && !bookUid.isEmpty()) {
			// TODO: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			//List<String> lst1 = jdbcTemplate.query("SELECT * FROM book WHERE uid=?", (rs, rowNum) -> {
			//	return rs.getString("title");
			//}, bookUid);
			//logger.debug("lst1=[" + lst1 + "]");

			List<Book> lst = initTestData(); // DEBUG:
			bookInfo = lst.stream().filter(item -> item.uid.equals(bookUid)).findFirst().orElse(null); // DEBUG:
		}

		logger.debug("BooksLibraryDao.getBookByUid(" + instanceId + "): end time=[" + (System.currentTimeMillis() - ts) + "ms]");

		return bookInfo;
	}

}
