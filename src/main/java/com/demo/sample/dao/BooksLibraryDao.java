package com.demo.sample.dao;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.sample.domain.Author;
import com.demo.sample.domain.Book;
import com.demo.sample.domain.Genre;
import com.demo.sample.domain.Language;
import com.demo.sample.domain.Status;
import com.demo.sample.domain.Title;
import com.demo.sample.utils.DbUtils;

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

	public Book getBookByUid(String instanceId, String bookUid) {
		long ts = System.currentTimeMillis();
		logger.debug("BooksLibraryDao.getBookByUid(" + instanceId + "): start");

		Book bookInfo = null;
		if (bookUid != null && !bookUid.isEmpty()) {
			List<Book> lstBooks = jdbcTemplate.query(""
				+ "SELECT * "
				+ "FROM books "
				+ "LEFT JOIN languages ON (bk_la_id=la_id) "
				+ "LEFT JOIN status ON (st_id=bk_st_id) "
				+ "WHERE bk_uid=?",
				(rs, rowNum) -> {
					Language lang = new Language();
					lang.la_id = DbUtils.getLong("la_id", rs);
					lang.la_name = DbUtils.getString("la_name", rs);

					Status status = new Status();
					status.st_id = DbUtils.getLong("st_id", rs);
					status.st_name = DbUtils.getString("st_name", rs);

					Book rec = new Book();
					rec.bk_id = DbUtils.getLong("bk_id", rs);
					rec.bk_uid = DbUtils.getString("bk_uid", rs);
					rec.bk_main_title = DbUtils.getString("bk_main_title", rs);
					rec.bk_cover = DbUtils.getString("bk_cover", rs);
					rec.bk_description = DbUtils.getString("bk_description", rs);
					rec.language = lang;
					rec.bk_url = DbUtils.getString("bk_url", rs);
					rec.bk_score = DbUtils.getBigDecimal("bk_score", rs);
					rec.bk_pages = DbUtils.getBigDecimal("bk_pages", rs);
					rec.bk_progress = DbUtils.getBigDecimal("bk_progress", rs);
					rec.status = status;
					return rec;
				}, bookUid);
			if (!lstBooks.isEmpty()) {
				bookInfo = lstBooks.get(0);
				// pobranie tytułów
				List<Title> lstTitle = jdbcTemplate.query("SELECT * FROM books_titles WHERE bt_bk_id=? ORDER BY bt_title", (rs, rowNum) -> {
					Title rec = new Title();
					rec.bt_id = DbUtils.getLong("bt_id", rs);
					rec.bt_bk_id = DbUtils.getLong("bt_bk_id", rs);
					rec.bt_title = DbUtils.getString("bt_title", rs);
					return rec;
				}, bookInfo.bk_id);
				if (!lstTitle.isEmpty()) {
					bookInfo.titles.addAll(lstTitle);
				}
				// pobranie autorów
				List<Author> lstAuthor = jdbcTemplate.query("SELECT * FROM books_authors JOIN authors ON (ba_au_id=au_id) WHERE ba_bk_id=? ORDER BY au_first_name, au_last_name", (rs, rowNum) -> {
					Author rec = new Author();
					rec.au_id = DbUtils.getLong("au_id", rs);
					rec.au_last_name = DbUtils.getString("au_last_name", rs);
					rec.au_first_name = DbUtils.getString("au_first_name", rs);
					return rec;
				}, bookInfo.bk_id);
				if (!lstAuthor.isEmpty()) {
					bookInfo.authors.addAll(lstAuthor);
				}
				// pobranie gatunków
				List<Genre> lstGenre = jdbcTemplate.query("SELECT * FROM books_genres JOIN genres ON (bg_ge_id=ge_id) WHERE bg_bk_id=? ORDER BY ge_name", (rs, rowNum) -> {
					Genre rec = new Genre();
					rec.ge_id = DbUtils.getLong("ge_id", rs);
					rec.ge_name = DbUtils.getString("ge_name", rs);
					return rec;
				}, bookInfo.bk_id);
				if (!lstGenre.isEmpty()) {
					bookInfo.genres.addAll(lstGenre);
				}
			}
		}

		logger.debug("bookInfo=[" + bookInfo + "]"); // DEBUG:

		logger.debug("BooksLibraryDao.getBookByUid(" + instanceId + "): end time=[" + (System.currentTimeMillis() - ts) + "ms]");

		return bookInfo;
	}

}
