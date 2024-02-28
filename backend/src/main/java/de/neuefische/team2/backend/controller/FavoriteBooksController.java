package de.neuefische.team2.backend.controller;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.models.FavoriteBook;
import de.neuefische.team2.backend.service.FavoriteBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/favoriteBooks")
@RequiredArgsConstructor
public class FavoriteBooksController {

    private final FavoriteBookService bookService;

    @GetMapping
    public List<FavoriteBook> getBooks() {
        return bookService.getBooks();
    }

    @DeleteMapping("/{id}")
    public FavoriteBook deleteBookById(@PathVariable String id) {
        return bookService.deleteBookById(id);
    }

    @PostMapping
    public FavoriteBook addBook(@RequestBody Book bookDto) {
        return bookService.addBook(bookDto);
    }

}
