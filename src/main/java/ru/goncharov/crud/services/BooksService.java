package ru.goncharov.crud.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;
import ru.goncharov.crud.repositories.BooksRepository;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Person findPersonByBook(Optional<Book> book){
        if(book.isPresent()){
            Hibernate.initialize(book);
            return book.get().getPerson();

        }
        return null;
    }

    @Transactional
    public void save(Book Book) {
        booksRepository.save(Book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
    @Transactional
    public void updatePersonId(int bookId, int personId){
        booksRepository.updatePersonId(bookId, personId);
    }

    @Transactional
    public void deletePersonId(int bookId){
        booksRepository.deletePersonId(bookId);
    }

    public List<Book> findByPerson(Person person){
        return  booksRepository.findByPerson(person);
    }

}
