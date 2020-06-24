package com.dxc.libraryapi.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.exception.BookException;



@Service
public interface BookService {
	Book add(Book book) throws BookException;

	Book update(Book book) throws BookException;

	boolean deleteById(int bcode) throws BookException;

	Book getById(int bcode);

	List<Book> getAllBooks();

	

	Book findByAuthor(String author);

	List<Book> findByIssueDate(LocalDate issueDate);

	Book findByBname(String bname);

}
