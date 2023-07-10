package ru.goncharov.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;

import javax.persistence.OneToMany;
import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
