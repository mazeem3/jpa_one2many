package com.library.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.spring.entity.Authors;
import com.library.spring.exception.NotFoundException;
import com.library.spring.repository.AuthorRepository;

@RestController
@RequestMapping("/api")
public class AuthorController {
	
	//ADDING
	@Autowired
	AuthorRepository authorRepo;

	@GetMapping("/authors")
	public List<Authors> getAllAuthors() {
		return authorRepo.findAll();
	}

	@GetMapping("/authors/{id}")
	public Authors getAuthorbyId(@PathVariable Long id) {
		Optional<Authors> author = authorRepo.findById(id);
		if (author.isPresent()) {
			return author.get();
		} else {
			throw new NotFoundException("Post not found with id " + id);
		}
	}
	
	@PostMapping("/authors")
	public Authors createAuthor(@Valid @RequestBody Authors author) {
		return authorRepo.save(author);
	}
	
	@PutMapping("/authors/{id}")
	public Authors updateAuthor(@PathVariable Long id,
            @Valid @RequestBody Authors updatedAuthor) {
        return authorRepo.findById(id)
                .map(author -> {
                    author.setFirstName(updatedAuthor.getFirstName());
                    author.setLastName(updatedAuthor.getLastName());
                    author.setBio(updatedAuthor.getBio());
                    return authorRepo.save(author);
                }).orElseThrow(() -> new NotFoundException("Author not found with id " + id));
		
	}
	
	@DeleteMapping("/authors/{id}")
	public String deleteAuthor(@PathVariable Long id) {
		return authorRepo.findById(id)
				.map(author -> {
					authorRepo.delete(author);
					return "Delete Successfully!";
					}).orElseThrow(() -> new NotFoundException("Author not found with id " + id));
	}
	

}
