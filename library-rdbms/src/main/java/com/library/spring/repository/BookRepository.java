package com.library.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.library.spring.entity.Books;
import com.library.spring.entity.Authors;

public interface BookRepository extends JpaRepository<Books, Long> {
	
	List<Books> findBooksByAuthorId(Long authorId);

	Optional<Books> findByIdAndAuthorId(Long bookId, Long authorId);

}
