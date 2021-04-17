package com.softuni.introtospringexercise.controllers;


import com.softuni.introtospringexercise.entities.Author;
import com.softuni.introtospringexercise.entities.Book;
import com.softuni.introtospringexercise.services.AuthorService;
import com.softuni.introtospringexercise.services.BookService;
import com.softuni.introtospringexercise.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;


    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        // seed data

        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();


        //ex.1
//        List<Book> books = this.bookService.getAllBooksAfter2000();
//        for (Book book: books) {
//            System.out.println(book.getTitle());
//        }

        //ex.2

        String dateS = "1990";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = formatter.parse(dateS);

        this.authorService.getAuthorsWithBooksReleasedBeforeDate(date)
                .forEach(System.out::println);


        //ex.3

//        List<Author> authors = this.authorService.findAllAuthorsByCountOfBooks();
//        for (Author author : authors) {
//            System.out.println(author.getFirstName() + " "
//                    + author.getLastName() + " " + author.getBooks().size());
//        }

        //ex.4

//        List<String> books = this.bookService
//                .getBooksTitleReleaseDateAndCopiesByAuthorNames("George", "Powell");
//
//        for (String book : books) {
//            System.out.println(book);
//        }
    }
}
