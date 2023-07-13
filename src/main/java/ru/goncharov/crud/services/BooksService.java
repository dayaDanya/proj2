package ru.goncharov.crud.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;
import ru.goncharov.crud.repositories.BooksRepository;

import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Book> findAll(Integer page, Integer booksPerPage, String sortParam) {
        if(sortParam == null)
            sortParam = "bookId";

        if (page != null && booksPerPage != null)
            return booksRepository
                    .findAll(PageRequest.of(page, booksPerPage, Sort.by(sortParam)))
                    .getContent();
        else
            return booksRepository.findAll(Sort.by(sortParam));
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Person findPersonByBook(Optional<Book> book) {
        if (book.isPresent()) {
            Hibernate.initialize(book);
            return book.get().getPerson();

        }
        return null;
    }
    public List<Book> search(String searchQuery){
        List<Book> foundBooks = new ArrayList<>();
        for(Book book : booksRepository.findAll()){
            Hibernate.initialize(book);
            if(book.getBook_name().toLowerCase().contains(searchQuery.toLowerCase()) ||
            book.getAuthor().toLowerCase().contains(searchQuery.toLowerCase()) ||
            String.valueOf(book.getYearOfWriting()).contains(searchQuery.toLowerCase()) ||
                    searchQuery.toLowerCase().contains(book.getBook_name().toLowerCase()) ||
                    searchQuery.toLowerCase().contains(book.getAuthor().toLowerCase()) ||
                    searchQuery.contains(String.valueOf(book.getYearOfWriting())))
                foundBooks.add(book);
        }
        return foundBooks;
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
    public void updatePersonId(Book book, int personId) {
        //book.setDateOfIssue(new Date());
        //System.out.println(book.getDateOfIssue());
        updateDateOfIssue(book, new Date());
        booksRepository.updatePersonId(book.getBookId(), personId);
    }

    @Transactional
    public void deletePersonId(int bookId) {
        booksRepository.deletePersonId(bookId);
    }
    @Transactional
    void updateDateOfIssue(Book book, Date date){
        booksRepository.updateDateOfIssue(book.getBookId(), date);
    }

    public List<Book> findByPerson(Person person) {
        List<Book> books = booksRepository.findByPerson(person);
        Date curDate = new Date();
        for(Book book : books){
            Period period = Period.between(
            book.getDateOfIssue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    curDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
            int days = period.getDays();
            book.setOverdue(days >= 10);

        }
        return books;
    }

}
