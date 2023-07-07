package ru.goncharov.crud.models;

import org.hibernate.validator.constraints.NotEmpty;
import ru.goncharov.crud.controllers.BookController;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @NotEmpty(message = "name should not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",
    message = "Name should be in format:\n Surname Name Middle name")
    @Column(name = "person_name")
    private String person_name;
    @NotNull(message = "write year of birth please")
    @Min(value = 1900, message = "year of birth should not be less than 1900")
    @Max(value = 2016, message = "year of birth should not be bigger than 2016")
    @Column(name = "birth_year")
    private int birth_year;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    List<Book> bookList;
    public Person(){}

    public Person(int person_id, String person_name, int birth_year) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.birth_year = birth_year;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}

