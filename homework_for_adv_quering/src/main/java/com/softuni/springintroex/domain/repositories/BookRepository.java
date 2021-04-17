package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.AgeRestriction;
import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Set<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    Set<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    @Query("select b from Book b where substring(b.releaseDate,0,4) not  like :year")
    Set<Book> findAllByReleaseDateNotInYear(String year);

    Set<Book> findAllByReleaseDateIsLessThan(LocalDate date);

    Set<Book> findAllByTitleContains(String message);

    Set<Book> findAllByAuthorLastNameStartingWith(String name);

    @Query("select count(b) from Book  b where length(b.title)> :length")
    int getNumberOfBooksWithTitleLength(int length);

    Book findByTitle(String title);

    @Transactional
    @Modifying
    @Query("update  Book b set b.copies = b.copies + :copies where b.releaseDate > :date")
    int updateCopies(int copies, LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book AS b WHERE b.copies < :copies")
    Integer removeBooksWithCopiesLessThan(int copies);
}
