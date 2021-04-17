package com.softuni.introtospringexercise.impl;

import com.softuni.introtospringexercise.constants.GlobalConstants;
import com.softuni.introtospringexercise.entities.*;
import com.softuni.introtospringexercise.repositories.BookRepository;
import com.softuni.introtospringexercise.services.AuthorService;
import com.softuni.introtospringexercise.services.BookService;
import com.softuni.introtospringexercise.services.CategoryService;
import com.softuni.introtospringexercise.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final FileUtil fileUtil;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, FileUtil fileUtil, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.fileUtil = fileUtil;
        this.categoryService = categoryService;
    }


    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] fileContent = fileUtil.readFileContent(GlobalConstants.BOOKS_FILE_PATH);
        Arrays.stream(fileContent).forEach(r -> {
            String[] params = r.split("\\s+");

            Author author = this.getRandomAuthor();
            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);
            int copies = Integer.parseInt(params[2]);

            BigDecimal price = new BigDecimal(params[3]);
            AgeRegistration ageRegistration = AgeRegistration
                    .values()[Integer.parseInt(params[4])];

            String title = this.getTitle(params);
            Set<Category> categories = this.getRandomCategories();
            Book book = new Book();

            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRegistration(ageRegistration);
            book.setTitle(title);
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        });

    }

    @Override
    public List<String> getBooksTitleReleaseDateAndCopiesByAuthorNames(String firstName, String lastName) {
        return this.bookRepository
                .getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName)
                .stream()
                .map(book -> String.format("%s - %s - %d",
                        book.getTitle(), book.getReleaseDate().toString(), book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate = LocalDate.parse("31/12/2000",formatter);
        return this.bookRepository.findAllByReleaseDateAfter(localDate);
    }

    private Set<Category> getRandomCategories() {

        Set<Category> result = new HashSet<>();

        Random random = new Random();
        int bound = random.nextInt(3) + 1;

        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;

            result.add(this.categoryService.getCategoryById((long) categoryId));
        }
        return result;
    }

    private String getTitle(String[] params) {

        StringBuilder builder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            builder.append(params[i]).append(" ");
        }
        return builder.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) randomId);

    }
}
