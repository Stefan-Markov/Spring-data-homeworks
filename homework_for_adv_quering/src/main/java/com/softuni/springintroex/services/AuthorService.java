package com.softuni.springintroex.services;

import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.services.models.BookInfo;

import java.io.IOException;

public interface AuthorService {
    void seedAuthorsInDB() throws IOException;

    void printAllAuthorsWithStartString(String start);

    void printAllAuthorsByBookCopies();

    Integer getAuthorBooksCount(String firstName, String lastName);
}
