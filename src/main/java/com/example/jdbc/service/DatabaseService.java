package com.example.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void createDatabase() {
		jdbcTemplate.execute("SELECT sp_createDatabase()");
	}
	
	public void dropDatabase() {
		jdbcTemplate.execute("SELECT sp_dropDatabase()");
	}
	
	public void clearTable(String tableName) {
		jdbcTemplate.update("SELECT sp_clearTable(?)", tableName);
	}
	
	public void insertBook(String isbn, String title, String author, String genre,
	                       int year, int copies, boolean availability) {
		jdbcTemplate.update("SELECT sp_insertBook(?, ?, ?, ?, ?, ?, ?)",
				isbn, title, author, genre, year, copies, availability);
	}
	
	public void updateBook(String isbn, String title, String author, String genre,
	                       int year, int copies, boolean availability) {
		jdbcTemplate.update("SELECT sp_updateBook(?, ?, ?, ?, ?, ?, ?)",
				isbn, title, author, genre, year, copies, availability);
	}
	
	public void deleteBook(String title) {
		jdbcTemplate.update("SELECT sp_deleteBookByTitle(?)", title);
	}
	
	public void createUser(String username, String password, String role) {
		jdbcTemplate.update("SELECT sp_createUser(?, ?, ?)", username, password, role);
	}
	
	public String searchBook(String title) {
		return jdbcTemplate.queryForObject("SELECT sp_searchBookByTitle(?)", new Object[]{title}, String.class);
	}
}
