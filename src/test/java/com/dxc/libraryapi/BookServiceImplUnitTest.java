package com.dxc.libraryapi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.exception.BookException;
import com.dxc.libraryapi.repository.BookRepository;
import com.dxc.libraryapi.service.BookService;
import com.dxc.libraryapi.service.BookServiceImpl;

@SpringJUnitConfig
public class BookServiceImplUnitTest

{
	@TestConfiguration
   static class BookServiceImplTestContextConfiuration{
		
		@Bean
		public BookService bookService()
		{
			return new BookServiceImpl();
			
		}
	}
	@MockBean
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	private Book[] testData;
	
	@BeforeEach
	public void fillTestData()
	{
		testData= new Book[] {
				new Book(101,"circle of reason","Amitav Ghose",LocalDate.now()),
				new Book(102,"Politics","Aristotle",LocalDate.now()),
				new Book(103,"My truth","Indra Gandhi",LocalDate.now())
				
		};
	}
	
	@AfterEach
	public void clearDatabase()
	{
		testData=null;
	}
	
	@Test
	public void addTest()
	{
		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(null);
		Mockito.when(bookRepository.existsById(testData[0].getBcode())).thenReturn(false);
		
		try
		{
			Book actual=bookService.add(testData[0]);
			Assertions.assertEquals(testData[0], actual);
		}catch(BookException e)
		{
			Assertions.fail(e.getMessage());
		}
	}
	
	@Test
	public void addExsistingBookTest()
	{
		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(null);
		Mockito.when(bookRepository.existsById(testData[0].getBcode())).thenReturn(true);
	
		      
			Assertions.assertThrows(BookException.class, ()->{
				bookService.add(testData[0]);
			}); 
	}
	
	@Test
	public void updateExistingBookTest() {

		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(null);

		Mockito.when(bookRepository.existsById(testData[0].getBcode())).thenReturn(true);

		try {
			Book actual = bookService.update(testData[0]);
			Assertions.assertEquals(testData[0], actual);
		} catch (BookException e) {
			Assertions.fail(e.getMessage());
		}
	}
	
	@Test
	public void updateNonExistingBookTest() {
		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(null);

		Mockito.when(bookRepository.existsById(testData[0].getBcode())).thenReturn(false);

		Assertions.assertThrows(BookException.class, () -> {
			bookService.update(testData[0]);
		});

	}
	
	@Test
	public void deleteByIdExsistingRecordTest()
	{
		Mockito.when(bookRepository.existsById(Mockito.isA(Integer.class))).thenReturn(true);
		Mockito.doNothing().when(bookRepository).deleteById(Mockito.isA(Integer.class));
		
		try
		{
			Assertions.assertTrue(bookService.deleteById(testData[0].getBcode()));
		}catch(BookException e)
		{
			Assertions.fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void deleteByNonIdExsistingRecordTest()
	{
		Mockito.when(bookRepository.existsById(Mockito.isA(Integer.class))).thenReturn(false);
		Mockito.doNothing().when(bookRepository).deleteById(Mockito.isA(Integer.class));
		Assertions.assertThrows(BookException.class,
				()->{bookService.deleteById(testData[0].getBcode());});
		
	}
	
	@Test
	public void getAllBooksWhenDataExists() {
		List<Book> expected =Arrays.asList(testData);
		
		Mockito.when(bookRepository.findAll())
		.thenReturn(expected);
		
		Assertions.assertEquals(expected,bookService.getAllBooks());
	}
	
	
	@Test
	public void getAllBooksWhenNoDataExists() {
		List<Book> expected =new ArrayList<>();
		
		Mockito.when(bookRepository.findAll())
		.thenReturn(expected);
		
		Assertions.assertEquals(expected,bookService.getAllBooks());
	}
}
