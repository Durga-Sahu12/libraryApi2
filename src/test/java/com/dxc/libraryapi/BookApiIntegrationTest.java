package com.dxc.libraryapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;


import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@SpringJUnitConfig
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = SpringBootDataLibraryRestApiDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookApiIntegrationTest {

	@Autowired
	private MockMvc mvcClient;

	@Autowired
	private BookRepository bookRepository;
	
	private List<Book> testData;

	private static final String API_URL = "/books";
	
	@BeforeEach
	public void fillTestData() {
		testData = new ArrayList<>();
		testData.add(new Book(101,"circle of reason","Amitav Ghose",LocalDate.now()));
		testData.add(new Book(102,"Politics","Aristotle",LocalDate.now()));
		testData.add(new Book(103,"My truth","Indra Gandhi",LocalDate.now()));
		
		for(Book book : testData) {
			bookRepository.saveAndFlush(book);
		}
	}

	@AfterEach
	public void clearDatabase() {
		bookRepository.deleteAll();
		testData = null;
	}

	@Test
	public void getAllBooksTest()  {
		try {
			mvcClient.perform(get(API_URL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	}
	
	@Test
	public void getBookByIdTest() {
		Book testRec = testData.get(0);
		
		try {
			mvcClient.perform(get(API_URL + "/" + testRec.getBcode()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bname", is(testRec.getBname())))
				.andExpect(jsonPath("$.author", is(testRec.getAuthor())))
				.andExpect(jsonPath("$.issuedate", is(testRec.getIssueDate())))
				.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}		
	}
	
	@Test
	public void getBookByIdTestNonExsiting() {
		
		try {
			mvcClient.perform(get(API_URL + "/8880"))
				.andExpect(status().isNotFound())
				.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}		
	}
	
	@Test
	public void getBookByNameTest(){
		Book testRec = testData.get(0);
		
		try {
			mvcClient.perform(get(API_URL + "/" + testRec.getBcode()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bname", is(testRec.getBname())))
			.andExpect(jsonPath("$.author", is(testRec.getAuthor())))
			.andExpect(jsonPath("$.issuedate", is(testRec.getIssueDate())))
				.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}	
	}
	
	@Test
	public void getItemByIssueDateTest(){
		Book testRec = testData.get(0);
		
		try {
			mvcClient.perform(get(API_URL + "/" + testRec.getIssueDate()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}	
	}
	
	
	
	private static final ObjectMapper makeMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new ParameterNamesModule());
	    mapper.registerModule(new Jdk8Module());
	    mapper.registerModule(new JavaTimeModule());
	    return mapper;
	}
	
	@Test
	public void createBookTest() {
		Book testRec = new Book(201, "Beer", "Corona", LocalDate.now());
		
		try {	
			mvcClient.perform(post(API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(makeMapper().writeValueAsString(testRec))
					).andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	
	}
	
	@Test
	public void updateBookTest() {
		Book testRec = testData.get(0);
		
		try {
			mvcClient.perform(put(API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(makeMapper().writeValueAsString(testRec))
					).andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	
	}
	
	@Test
	public void deleteBooksByIdTest() {
		
		try {	
			mvcClient.perform(delete(API_URL +"/"+testData.get(0).getBcode()))
			.andExpect(status().isOk())
			.andDo(print());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	}
	
	
}
