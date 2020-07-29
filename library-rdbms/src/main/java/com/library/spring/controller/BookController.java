package com.library.spring.controller;

import java.awt.print.Book;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.spring.entity.Books;
import com.library.spring.exception.NotFoundException;
import com.library.spring.repository.AuthorRepository;
import com.library.spring.repository.BookRepository;

@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	AuthorRepository authorRepo;

	@GetMapping("/authors/{authorId}/books")
	public List<Books> getBookByAuthor(@PathVariable(value = "authorId") Long authorId) {
		return bookRepo.findBooksByAuthorId(authorId);
	}

	@GetMapping("/books")
	public List<Books> getAllBooks() {
		return bookRepo.findAll();
	}

	@PostMapping("/authors/{authorId}/books")
	public Books addBook(@PathVariable Long authorId, @Valid @RequestBody Books book) {
		return authorRepo.findById(authorId).map(author -> {
			book.setAuthor(author);
			return bookRepo.save(book);
		}).orElseThrow(() -> new NotFoundException("Student not found!"));
	}

	@PutMapping("/authors/{authorId}/books/{bookId}")
	public Books updateBook(@PathVariable Long authorId, @PathVariable Long bookId,
			@Valid @RequestBody Books bookRequest) throws NotFoundException {

		if (!authorRepo.existsById(authorId)) {
			throw new NotFoundException("Instructor not found");

		}

		return bookRepo.findById(bookId).map(book -> {
			book.setTitle(bookRequest.getTitle());
			book.setQuantity(bookRequest.getQuantity());
			return bookRepo.save(book);
		}).orElseThrow(() -> new NotFoundException("Book doesnt exist"));
	}
	
	@DeleteMapping("/authors/{authorId}/books/{bookId}")
	public ResponseEntity < ? > deleteBook(@PathVariable Long authorId,
	        @PathVariable Long bookId) throws NotFoundException {
		
		return bookRepo.findByIdAndAuthorId(bookId, authorId).map(book -> {
			bookRepo.delete(book);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new NotFoundException(
            "Book not found with id " + bookId + " and authorId " + authorId));
		
	}
	

}
