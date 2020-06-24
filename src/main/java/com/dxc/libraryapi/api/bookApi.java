package com.dxc.libraryapi.api;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dxc.libraryapi.entity.Book;
import com.dxc.libraryapi.exception.BookException;
import com.dxc.libraryapi.service.BookService;

@RestController
@RequestMapping("/bookitems")
public class bookApi {

	@Autowired
private BookService bookService;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks()
	{
		return new ResponseEntity<List<Book>>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	
	@GetMapping("/{bookId:[0-9]{1,5}}")
	public ResponseEntity<Book> getBookById(@PathVariable("bookId")int bcode)
	{
		 ResponseEntity<Book> response=null;
		 
		 Book book= bookService.getById(bcode);
		 
		 if(book==null)
		 {
			 
			 response=new  ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else
			{
				response=new  ResponseEntity<>(book,HttpStatus.OK);
			}
			return  response;
			
		 
	}
	
	
	@GetMapping("/{bname:[A-Za-z]{2,50}}")
	public ResponseEntity<Book> getBookByBName(@PathVariable("bname")String bname)
	{
		 ResponseEntity<Book> response=null;
		 
		 Book book= bookService.findByBname(bname);
		 
		 if(book==null)
		 {
			
			 response=new  ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else
			{
				response=new  ResponseEntity<>(book,HttpStatus.OK);
			}
			return  response;
			
		 
	}
	
	
	
	
	  @GetMapping("//{author:[A-Za-z]{2,50}}") public ResponseEntity<Book>
	  getBookAuthor(@PathVariable("author")String author) { ResponseEntity<Book>
	  response=null;
	  
	  Book book= bookService.findByAuthor(author);
	  
	  if(book==null) {
	  
	  response=new ResponseEntity<>(HttpStatus.NOT_FOUND); } else { response=new
	  ResponseEntity<>(book,HttpStatus.OK); } return response;
	  
	  
	  }
	 
	 
	
	@GetMapping("/{issuedate:[0-9]{4}-[0-9]{2}-[0-9]{2}}")
	public ResponseEntity<List<Book>> getBookByIssueDate(@PathVariable("issuedate") 
	                                                      @DateTimeFormat(iso=ISO.DATE)LocalDate issueDate)
	{
		 ResponseEntity<List<Book>> response=null;
		 
		 List<Book> book= bookService.findByIssueDate(issueDate);
		 
		 if(book==null)
		 {
			 
			 response=new  ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else
			{
				response=new  ResponseEntity<>(book,HttpStatus.OK);
			}
			return  response;
			
		 
	}
	
	
	@PostMapping
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book,BindingResult result) throws BookException 
	{
		ResponseEntity<Book> response=null;
		
		
		if(result.hasErrors())
		{
			StringBuilder errMsg = new StringBuilder();
			for(FieldError err : result.getFieldErrors())
			{
				errMsg.append(err.getDefaultMessage()+",");
			}throw new BookException(errMsg.toString());
		}
		else
		{
			bookService.add(book);
			response=new  ResponseEntity<>(HttpStatus.OK);
		}
		return  response;
		
	}
	
	@PutMapping
	public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book,BindingResult result) throws BookException 
	{
		ResponseEntity<Book> response=null;
		
		
		if(result.hasErrors())
		{
			StringBuilder errMsg = new StringBuilder();
			for(FieldError err : result.getFieldErrors())
			{
				errMsg .append(err.getDefaultMessage()+",");
			}throw new BookException(errMsg.toString());
		}
		else
		{
			bookService.update(book);
			response=new  ResponseEntity<>(HttpStatus.OK);
		}
		return  response;
		
	}
	
	
@DeleteMapping("/{bookId}")
	
	public ResponseEntity<Void> deleteBooksById(@PathVariable("bookId")int bcode) throws BookException 
	{
		bookService.deleteById(bcode);
		return new  ResponseEntity<>(HttpStatus.OK);
	}

}
