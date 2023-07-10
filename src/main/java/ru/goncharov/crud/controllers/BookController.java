package ru.goncharov.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.goncharov.crud.models.Book;
import ru.goncharov.crud.models.Person;
import ru.goncharov.crud.services.BooksService;
import ru.goncharov.crud.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "booksPerPage", required = false) Integer booksPerPage,
                        @RequestParam(value = "sortParam", required = false) String sortParam,
                        Model model) {
        System.out.println(page);
        System.out.println(booksPerPage);
        System.out.println(sortParam);
        model.addAttribute("books", booksService.findAll(page, booksPerPage, sortParam));
        return "/books/index";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("owner", booksService
                .findPersonByBook(Optional.ofNullable(book)));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/updateID")
    public String updatePerson_id(@ModelAttribute("person") Person person,
                                  @PathVariable("id") int id) {
        booksService.updatePersonId(id, person.getPerson_id());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/deleteID")
    public String deletePerson_id(@PathVariable("id") int id) {

        booksService.deletePersonId(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

}
