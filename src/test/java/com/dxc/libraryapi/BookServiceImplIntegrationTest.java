package com.dxc.libraryapi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.exception.BookException;
import com.dxc.libraryapi.repository.BookRepository;
import com.dxc.libraryapi.service.BookService;



@SpringJUnitConfig
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceImplIntegrationTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	private Book[] testData;

	@BeforeEach
	public void fillTestData() {
		testData = new Book[] {
				new Book(101,"circle of reason","Amitav Ghose",LocalDate.now()),
				new Book(102,"Politics","Aristotle",LocalDate.now()),
				new Book(103,"My truth","Indra Gandhi",LocalDate.now())
				};

		for (Book book : testData) {
			bookRepository.saveAndFlush(book);
		}
	}

	@AfterEach
	public void clearDatabase() {
		bookRepository.deleteAll();
		testData = null;
	}

	@Test
	public void addTest() {
		try {
			Book expected = new Book(106, "half girlfriend","Chetan Bhagat", LocalDate.now().minusYears(1));
			Book actual = bookService.add(expected);
			Assertions.assertEquals(expected, actual);
		} catch (BookException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	public void addExistingBoookTest() {
		Assertions.assertThrows(BookException.class, () -> {
			bookService.add(testData[0]);
		});
	}

	@Test
	public void updateExistingBookTest() {
		try {
			Book actual = bookService.update(testData[0]);
			Assertions.assertEquals(testData[0], actual);
		} catch (BookException e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	public void updateNonExistingBookTest() {
		Book nonExistingItem = new Book(106, "half girlfriend","Chetan Bhagat", LocalDate.now().minusYears(1));
		Assertions.assertThrows(BookException.class, () -> {
			bookService.update(testData[0]);
		});
	}

	@Test
	public void deleteByIdExistingRecordTest() {
		try {
			Assertions.assertTrue(bookService.deleteById(testData[0].getBcode()));
		} catch (BookException e) {
			Assertions.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void deleteByIdNonExistingRecordTest() {
		Assertions.assertThrows(BookException.class, () -> {
			bookService.deleteById(testData[0].getBcode());
		});
	}

	@Test
	public void getByIdExisitngRecordTest() {
		Assertions.assertEquals(testData[0].getBcode(), 
				bookService.getById(testData[0].getBcode()).getBcode());
	}

	@Test
	public void getByIdNonExisitngRecordTest() {
		Assertions.assertNull(bookService.getById(testData[0].getBcode()));
	}

	@Test
	public void getAllBooksWhenDataExists() {
		List<Book> expected = Arrays.asList(testData);
		//Assertions.assertSame(expected, itemService.getAllItems());
		Assertions.assertIterableEquals(expected, bookService.getAllBooks());
	}

	@Test
	public void getAllBooksWhenNoDataExists() {
		List<Book> expected = new ArrayList<>();
		bookRepository.deleteAll();
		Assertions.assertEquals(expected, bookService.getAllBooks());
	}
}
