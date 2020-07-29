package com.library.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.spring.entity.Authors;

public interface AuthorRepository extends JpaRepository<Authors, Long>{

}
