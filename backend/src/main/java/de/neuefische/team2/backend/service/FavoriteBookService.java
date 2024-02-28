package de.neuefische.team2.backend.service;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.models.FavoriteBook;
import de.neuefische.team2.backend.repos.FavoriteBooksRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class FavoriteBookService {

    private final FavoriteBooksRepo booksRepo;
    private final IdService idService;

    public FavoriteBookService(FavoriteBooksRepo booksRepo, IdService idService) {
        this.booksRepo = booksRepo;
        this.idService= idService;
    }

    public List<FavoriteBook> getBooks() {
        return booksRepo.findAll();
    }

    public FavoriteBook deleteBookById(String id) {

        Optional<FavoriteBook> byId = booksRepo.findByBookId(id);
        if (byId.isPresent()){
          booksRepo.delete(byId.get());
          return byId.get();
        }
        throw (new NoSuchElementException());
    }

    public FavoriteBook addBook(Book bookToSave){

        String id = idService.newId();
        FavoriteBook book = new FavoriteBook(id, bookToSave);
        Optional<FavoriteBook> byBookId = booksRepo.findByBookId(bookToSave.id());
        if (byBookId.isEmpty()){
            return booksRepo.save(book);
        }
        throw new IllegalArgumentException();

    }
}
