package com.softuni.springintroex;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.models.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Engine implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public Engine(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;

        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws IOException {
//        seedData();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //ex.1
//        this.bookService.printAllBooksByAgeRestriction(reader.readLine());

        //ex.2
//        this.bookService.printAllBooksByEditionTypeAndCopies();

        //ex.3
//        this.bookService.printAllBooksByPriceInBounds();

        //ex.4
//        this.bookService.printAllBooksNotInYear(reader.readLine());

        //ex.5
//        this.bookService.printAllBooksBeforeDate(reader.readLine());

        //ex.6
//        this.authorService.printAllAuthorsWithStartString(reader.readLine());

        //ex.7
//        this.bookService.printAllBooksByTitleContainsMessage(reader.readLine());

        //ex.8
//        this.bookService.printAllBooksWithAuthorStartingLastName(reader.readLine());

        //ex.9
//        this.bookService
//                .printAllBooksWitchTitleLength(Integer.parseInt(reader.readLine()));


        //ex.10
        this.authorService.printAllAuthorsByBookCopies();

        //ex.11
//        BookInfo bookByTitle = this.bookService.findBookByTitle(reader.readLine());
//        System.out.println(bookByTitle.getTitle() + " " + bookByTitle.getCopies());

        //ex.12
//        this.bookService.printUpdatedCountCopies(
//                Integer.parseInt(reader.readLine()),reader.readLine());

        //ex.13
//        int minCopies = Integer.parseInt(reader.readLine());
//            Integer deletedTitles = this.bookService.removeBooksWithCopiesLessThan(minCopies);
//            System.out.printf("%d books were removed%n", deletedTitles);

        //ex.14
//        System.out.print("Enter author's name to find books for: ");
//        String[] names = reader.readLine().split("\\s+");
//        Integer books = this.authorService.getAuthorBooksCount(names[0], names[1]);
//        if (books == null) {
//            System.out.printf("%s %s has not written any books yet%n", names[0], names[1]);
//        } else if (books == 1) {
//            System.out.printf("%s %s has written 1 book%n", names[0], names[1]);
//        } else {
//            System.out.printf("%s %s has written %d books%n", names[0], names[1], books);
//        }
    }


    void seedData() throws IOException {
        this.categoryService.seedCategoriesInDB();
        this.authorService.seedAuthorsInDB();
        this.bookService.seedBooksInDB();
    }
}
