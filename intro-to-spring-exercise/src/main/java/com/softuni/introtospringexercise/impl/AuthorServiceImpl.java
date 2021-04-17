package com.softuni.introtospringexercise.impl;

import com.softuni.introtospringexercise.constants.GlobalConstants;
import com.softuni.introtospringexercise.entities.Author;
import com.softuni.introtospringexercise.repositories.AuthorRepository;
import com.softuni.introtospringexercise.services.AuthorService;
import com.softuni.introtospringexercise.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] fileContent =
                this.fileUtil.readFileContent(GlobalConstants.AUTHOR_FILE_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            String[] params = r.split("\\s+");
            Author author = new Author(params[0], params[1]);

            this.authorRepository.saveAndFlush(author);
        });

    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<String> getAuthorsWithBooksReleasedBeforeDate(Date date) {
        return this.authorRepository.findAuthorByCountOfBooksAfterYear()
        .stream().map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {

        return this.authorRepository.findAuthorByCountOfBooksAfterYear();
    }
}
