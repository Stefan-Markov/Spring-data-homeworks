package com.softuni.introtospringexercise.repositories;

import com.softuni.introtospringexercise.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {

    List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc
            (String firstName, String lastName);

    List<Book> findAllByReleaseDateAfter(LocalDate localDateTime);
}
