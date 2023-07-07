package ru.goncharov.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.goncharov.crud.models.Person;
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
