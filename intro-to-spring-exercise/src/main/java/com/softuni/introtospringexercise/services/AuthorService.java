package com.softuni.introtospringexercise.services;

import com.softuni.introtospringexercise.entities.Author;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);

    List<String> getAuthorsWithBooksReleasedBeforeDate(Date date);

    List<Author> findAllAuthorsByCountOfBooks();
}
