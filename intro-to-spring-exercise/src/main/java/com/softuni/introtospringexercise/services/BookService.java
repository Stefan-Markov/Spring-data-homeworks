package com.softuni.introtospringexercise.services;

import com.softuni.introtospringexercise.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> getBooksTitleReleaseDateAndCopiesByAuthorNames(String firstName, String lastName);
    List<Book> getAllBooksAfter2000();
}
