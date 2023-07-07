package ru.goncharov.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.goncharov.crud.dao.BookDAO;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;
import ru.goncharov.crud.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PeopleService peopleService;
    @Autowired
    public BookController(BookDAO bookDAO, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){return "books/new";}
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}/updateID")
    public String updatePerson_id(@ModelAttribute("person") Person person,
                                  @PathVariable("id") int id){
        bookDAO.updatePerson_id(person, id);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/deleteID")
    public String deletePerson_id(@PathVariable("id") int id){
        bookDAO.deletePerson_id(id);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
