package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.services.models.BookInfo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthorsInDB() throws IOException {
        String[] lines = this.fileUtil
                .readFileContent(GlobalConstants.AUTHORS_FILE_PATH);

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            Author author = new Author(tokens[0], tokens[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public void printAllAuthorsWithStartString(String start) {
        this.authorRepository.findAllByFirstNameEndingWith(start)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    @Override
    public void printAllAuthorsByBookCopies() {

        List<Author> authors = this.authorRepository.findAll();
        Map<String, Integer> authorCopies = new HashMap<>();
        authors.forEach(author -> {
            int sum = author.getBooks().stream().mapToInt(Book::getCopies).sum();
            authorCopies.put(author.getFirstName() + " " + author.getLastName(),
                    sum);
        });

        authorCopies.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach(a -> System.out.println(a.getKey() + " " + a.getValue()));
    }

    @Override
    public Integer getAuthorBooksCount(String firstName, String lastName) {
        return this.authorRepository.getAuthorBooksCount(firstName, lastName);

    }


}
