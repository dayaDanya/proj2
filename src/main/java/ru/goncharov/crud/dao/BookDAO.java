package ru.goncharov.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;

import java.util.List;

@Component

public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(book_name, author, year_of_writing) " +
                "VALUES(?, ?, ?)", book.getBook_name(), book.getAuthor(), book.getYear_of_writing());
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET book_name=?, author=?, year_of_writing=? " +
                        "WHERE book_id=?", updatedBook.getBook_name(),
                updatedBook.getAuthor(), updatedBook.getYear_of_writing(), id);
    }
    public void updatePerson_id(Person person, int id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                person.getPerson_id(), id);
    }
    public void deletePerson_id(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                null, id);
    }

    public void delete(int id) {

        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }
}


