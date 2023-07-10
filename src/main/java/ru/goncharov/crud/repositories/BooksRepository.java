package ru.goncharov.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByPerson(Person person);
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.person.id = :personId WHERE b.id = :bookId")
    void updatePersonId(@Param("bookId") int bookId, @Param("personId") int personId);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.person = null WHERE b.id = :bookId")
    void deletePersonId(@Param("bookId") int bookId);
}
