package ru.goncharov.crud.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Size(max = 100, message = "name of book might not be larger than 100 characters")
    @Column(name = "book_name")
    private String book_name;
    @NotEmpty(message = "name should not be empty")
    @Size(max = 100, message = "name of author might not be larger than 100 characters")
    // @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "example: Alexander Pushkin")
    @Column(name = "author")
    private String author;
    @NotNull(message = "write year of writing please")
    @Max(value = 2023, message = "year of writing should not be larger than current: 2023")
    @Column(name = "year_of_writing")
    private int yearOfWriting;

    public Book() {
    }

    public Book(int bookId, Person person, String book_name, String author, int year_of_writing) {
        this.person = person;
        this.book_name = book_name;
        this.author = author;
        this.yearOfWriting = year_of_writing;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(@Nullable Person person) {
        this.person = person;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_of_writing() {
        return yearOfWriting;
    }

    public void setYear_of_writing(int year_of_writing) {
        this.yearOfWriting = year_of_writing;
    }
}
