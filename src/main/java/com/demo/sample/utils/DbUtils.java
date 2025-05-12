package com.demo.sample.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {

	public static String getString(String columnLabel, ResultSet rs) throws SQLException {
		return rs.getString(columnLabel);
	}

	public static Long getLong(String columnLabel, ResultSet rs) throws SQLException {
		Long res = rs.getLong(columnLabel);
		if (rs.wasNull()) {
			res = null;
		}

		return res;
	}

	public static BigDecimal getBigDecimal(String columnLabel, ResultSet rs) throws SQLException {
		return rs.getBigDecimal(columnLabel);
	}

}
