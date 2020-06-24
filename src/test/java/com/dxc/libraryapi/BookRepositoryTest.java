package com.dxc.libraryapi;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.repository.BookRepository;



@DataJpaTest // configuring H2, an in-memory database,setting Hibernate, Spring Data, and the
				// DataSource,performing an @EntityScan turning on SQL logging
public class BookRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BookRepository bookRepository;

	private Book[] testData;

	@BeforeEach
	public void fillTestData() {
		testData = new Book[] {
				new Book(101,"circle of reason","Amitav Ghose",LocalDate.now()),
				new Book(102,"Politics","Aristotle",LocalDate.now()),
				new Book(103,"My truth","Indra Gandhi",LocalDate.now())
				};

		// inserting test data into H2 database
		for (Book book : testData) {
			entityManager.persist(book);
		}
		entityManager.flush();

	}

	@AfterEach
	public void clearDatabase() {
		// removing test data into H2 database
		for (Book book : testData) {
			entityManager.remove(book);
		}
		entityManager.flush();
	}

	@Test
	public void findByBNameTest() {
		for (Book book : testData) {
			Assertions.assertEquals(book, bookRepository.findByBname(book.getBname()));
		}
	}

	@Test
	public void findByBNameTestWitnNonExistingTitle() {
		Assertions.assertNull(bookRepository.findByBname("@#1234"));
	}

	@Test
	public void findAllByIssueDateTest() {
		Book[] actualData = bookRepository.findAllByIssueDate(LocalDate.now()).toArray(new Book[] {});
		for (int i = 0; i < actualData.length; i++) {
			Assertions.assertEquals(testData[i], actualData[i]);
		}
	}

	@Test
	public void findAllByIssueDateTestWithNonExisitngDate() {
		List<Book> actualData = bookRepository.findAllByIssueDate(LocalDate.now().plusDays(2));
		Assertions.assertEquals(0, actualData.size());
	}

	
}
