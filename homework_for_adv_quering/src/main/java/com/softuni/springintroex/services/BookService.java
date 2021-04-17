package com.softuni.springintroex.services;

import com.softuni.springintroex.services.models.BookInfo;

import java.io.IOException;
import java.time.LocalDate;

public interface BookService {
    void seedBooksInDB() throws IOException;

    void printAllBooksByAgeRestriction(String name);

    void printAllBooksByEditionTypeAndCopies();

    void printAllBooksByPriceInBounds();

    void printAllBooksNotInYear(String year);

    void printAllBooksBeforeDate(String date);

    void printAllBooksByTitleContainsMessage(String message);

    void printAllBooksWithAuthorStartingLastName(String name);

    void printAllBooksWitchTitleLength(int length);

    BookInfo findBookByTitle(String title);

    void printUpdatedCountCopies(int copies, String date);

    Integer removeBooksWithCopiesLessThan(int minCopies);
}
