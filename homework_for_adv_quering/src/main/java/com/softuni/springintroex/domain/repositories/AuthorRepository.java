package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Set<Author> findAllByFirstNameEndingWith(String start);

    @Procedure(name = "udp_find_books_by_author")
    Integer getAuthorBooksCount(@Param("first_name") String first_name,
                                @Param("last_name") String last_name);

}
