package com.dxc.libraryapi.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


	

@Entity
@Table(name="bookitems")
public class Book implements Serializable {
	
	@Id
	@Column(name="bcode")
	@NotNull(message="bcode cannot be blank")
	private Integer bcode;
	
	@Column(name="bname", nullable=false)
	@NotBlank(message="bname cannot be empty")
	@Size(min=2,max=50,message="bname must be of 2 to 50 chars in length ")
	private String bname;
	
	@Column(name="author", nullable=false)
	@NotBlank(message="author cannot be empty")
	@Size(min=2,max=50,message="author name mustt be of 2 10 50 chars in length")
	private String author;
	
	@Column(name="issuedate", nullable=true)
	@NotNull(message="date can not be blank")
	@PastOrPresent(message="Issue Date can not be future date")
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate issueDate;
	
	
	public Book()
	{
		//left unimplemented
	}

	public Book(int bcode, String bname, String author, LocalDate issueDate) {
		super();
		this.bcode = bcode;
		this.bname = bname;
		this.author = author;
		this.issueDate = issueDate;
		
	}

	public Integer getBcode() {
		return bcode;
	}

	public void setBcode(Integer bcode) {
		this.bcode = bcode;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	
	

}