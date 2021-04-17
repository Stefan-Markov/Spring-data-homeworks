USE `spring_book_shop`;

DROP PROCEDURE IF EXISTS udp_find_books_by_author;

DELIMITER //
CREATE PROCEDURE udp_find_books_by_author(IN first_name VARCHAR(255),
                                          IN last_name VARCHAR(255),out books_count INT)
BEGIN

     SET books_count = (SELECT COUNT(*) AS books
                       FROM `books` AS b
                                JOIN
                            `authors` AS a ON b.author_id = a.id
                       WHERE a.first_name = first_name
                         AND a.last_name = last_name
                       GROUP BY a.id);

END //
DELIMITER ;

#
# CALL udp_find_books_by_author('Amanda', 'Rice', @books);
# SELECT @books;