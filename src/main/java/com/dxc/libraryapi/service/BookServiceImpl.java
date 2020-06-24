package com.dxc.libraryapi.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.exception.BookException;
import com.dxc.libraryapi.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService {
	
    @Autowired
	private BookRepository bookRepo;
    
    @Transactional
	@Override
	public Book add(Book book) throws BookException {
		if(book!=null)
		{
		 if(bookRepo.existsById(book.getBcode()))
		 {
			 throw new BookException("An book with bcode"+book.getBcode()+"already exsist");
		 }
		 bookRepo.save(book);
		}
		return book;
	}

    @Transactional
	@Override
	public Book update(Book book) throws BookException {
		if(book!=null)
		{
			if(bookRepo.existsById(book.getBcode()))
			{
				throw new BookException(" no books founds to update!");
			}
			bookRepo.save(book);
		}
		
		return book;
	}

    @Transactional
	@Override
	public boolean deleteById(int bcode) throws BookException {
		boolean deleted=false;
		if(!bookRepo.existsById(bcode))
		{
			throw new BookException("no books found to delete!");
		}
		bookRepo.deleteById(bcode);
		deleted=true;
		return deleted;
	}
    
   
	@Override
	public Book getById(int bcode) {
		
		return bookRepo.findById(bcode).orElse(null);
	}
   
   
	@Override
	public List<Book> getAllBooks() {
		return bookRepo.findAll();
		
	}
    
    
	@Override
	public Book findByBname(String bname) {
		
		return bookRepo.findByBname(bname);
	}
     
    //@Transactional
	@Override
	public Book findByAuthor(String author) {
		
		return bookRepo.findByAuthor(author);
	}
    
   
	@Override
	public List<Book> findByIssueDate(LocalDate issueDate) {
		
		return bookRepo.findAllByIssueDate(issueDate);
	}

}
