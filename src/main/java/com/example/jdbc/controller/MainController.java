package com.example.jdbc.controller;

import com.example.jdbc.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
	
	@Autowired
	private DatabaseService databaseService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/createDatabase")
	public String createDatabase(Model model) {
		try {
			databaseService.createDatabase();
			model.addAttribute("message", "База данных успешно создана");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка создания базы данных: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/dropDatabase")
	public String dropDatabase(Model model) {
		try {
			databaseService.dropDatabase();
			model.addAttribute("message", "База данных успешно удалена");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка удаления базы данных: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/clearTable")
	public String clearTable(@RequestParam(defaultValue = "Book") String table, Model model) {
		try {
			databaseService.clearTable(table);
			model.addAttribute("message", "Таблица " + table + " успешно очищена");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка очистки таблицы: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/insertBook")
	public String insertBookForm() {
		return "insertBook";
	}
	
	@PostMapping("/insertBook")
	public String insertBook(@RequestParam String isbn,
	                         @RequestParam String title,
	                         @RequestParam String author,
	                         @RequestParam String genre,
	                         @RequestParam int year,
	                         @RequestParam int copies,
	                         @RequestParam boolean availability,
	                         Model model) {
		try {
			databaseService.insertBook(isbn, title, author, genre, year, copies, availability);
			model.addAttribute("message", "Книга успешно добавлена");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка добавления книги: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/updateBook")
	public String updateBookForm() {
		return "updateBook";
	}
	
	@PostMapping("/updateBook")
	public String updateBook(@RequestParam String isbn,
	                         @RequestParam String title,
	                         @RequestParam String author,
	                         @RequestParam String genre,
	                         @RequestParam int year,
	                         @RequestParam int copies,
	                         @RequestParam boolean availability,
	                         Model model) {
		try {
			databaseService.updateBook(isbn, title, author, genre, year, copies, availability);
			model.addAttribute("message", "Книга успешно обновлена");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка обновления книги: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/deleteBook")
	public String deleteBookForm() {
		return "deleteBook";
	}
	
	@PostMapping("/deleteBook")
	public String deleteBook(@RequestParam String title, Model model) {
		try {
			databaseService.deleteBook(title);
			model.addAttribute("message", "Книга успешно удалена");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка удаления книги: " + e.getMessage());
		}
		return "result";
	}
	
	@GetMapping("/createUser")
	public String createUserForm() {
		return "createUser";
	}
	
	@PostMapping("/createUser")
	public String createUser(@RequestParam String username,
	                         @RequestParam String password,
	                         @RequestParam String role,
	                         Model model) {
		try {
			databaseService.createUser(username, password, role);
			model.addAttribute("message", "Пользователь успешно создан");
		} catch (Exception e) {
			model.addAttribute("message", "Ошибка создания пользователя: " + e.getMessage());
		}
		return "result";
	}
}
