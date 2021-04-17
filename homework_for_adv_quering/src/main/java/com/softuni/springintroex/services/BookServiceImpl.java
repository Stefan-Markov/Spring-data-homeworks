package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.domain.entities.*;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.domain.repositories.BookRepository;
import com.softuni.springintroex.domain.repositories.CategoryRepository;
import com.softuni.springintroex.services.models.BookInfo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;


    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void seedBooksInDB() throws IOException {
        String[] lines = this.fileUtil
                .readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Random random = new Random();
        for (String line : lines) {


            String[] data = line.split("\\s+");

            long authorIndex = random.nextInt((int) this.authorRepository.count()) + 1;
            Author author = this.authorRepository.findById(authorIndex).get();
            EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(data[1], formatter);

            int copies = Integer.parseInt(data[2]);
            BigDecimal price = new BigDecimal(data[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
            StringBuilder titleBuilder = new StringBuilder();

            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }

            String title = titleBuilder.toString().trim();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(localDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(this.getRandomCategories());

            this.bookRepository.saveAndFlush(book);


        }
    }

    @Override
    public void printAllBooksByAgeRestriction(String age) {
        this.bookRepository
                .findAllByAgeRestriction(AgeRestriction
                        .valueOf(age.toUpperCase()))
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksByEditionTypeAndCopies() {
        this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksByPriceInBounds() {
        this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getPrice()));
    }

    @Override
    public void printAllBooksNotInYear(String year) {
        this.bookRepository.findAllByReleaseDateNotInYear(year)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksBeforeDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        this.bookRepository.findAllByReleaseDateIsLessThan(localDate)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getEditionType()
                        + " " + b.getPrice()));
    }

    @Override
    public void printAllBooksByTitleContainsMessage(String message) {
        this.bookRepository.findAllByTitleContains(message.toLowerCase())
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksWithAuthorStartingLastName(String name) {
        this.bookRepository.findAllByAuthorLastNameStartingWith(name)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getAuthor().getFirstName()
                        + " " + b.getAuthor().getLastName()));
    }

    @Override
    public void printAllBooksWitchTitleLength(int length) {
        System.out.println(
                this.bookRepository.getNumberOfBooksWithTitleLength(length));
    }

    @Override
    public BookInfo findBookByTitle(String title) {

        Book book = this.bookRepository.findByTitle(title);
        BookInfo bookInfo = new BookInfo(book.getTitle(), book.getPrice(), book.getCopies());
        return bookInfo;


    }

    @Override
    public void printUpdatedCountCopies(int copies, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        int updatedRows = this.bookRepository.updateCopies(copies, localDate);

        System.out.println(updatedRows);

    }

    @Override
    public Integer removeBooksWithCopiesLessThan(int minCopies) {

        return this.bookRepository.removeBooksWithCopiesLessThan(minCopies);
    }

    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            long categoryIndex = random.nextInt((int) this.categoryRepository.count()) + 1;
            Category category = this.categoryRepository.findById(categoryIndex).get();
            categories.add(category);
        }

        return categories;
    }
}
