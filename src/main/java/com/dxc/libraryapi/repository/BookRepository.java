package com.dxc.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.libraryapi.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	Book findByBname(String bname);

	Book findByAuthor(String author);

	List<Book> findAllByIssueDate(LocalDate issueDate);

}
