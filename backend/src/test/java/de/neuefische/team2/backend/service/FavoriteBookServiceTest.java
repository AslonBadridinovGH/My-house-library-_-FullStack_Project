package de.neuefische.team2.backend.service;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.models.BookDto;
import de.neuefische.team2.backend.models.FavoriteBook;
import de.neuefische.team2.backend.repos.BooksRepo;
import de.neuefische.team2.backend.repos.FavoriteBooksRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FavoriteBookServiceTest {

    FavoriteBooksRepo booksRepo = Mockito.mock(FavoriteBooksRepo.class);
    IdService idService = Mockito.mock(IdService.class);


    @Test
    void getFavoriteBooksTest_returnListOfAllBook() {
        Book book = new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure", 2000, "Bloomsbury",
                "London", 100, "very good book", 0);
        Book book1 = new Book("2", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0);
        //GIVEN
        Mockito.when(booksRepo.findAll()).thenReturn(List.of(
                new FavoriteBook("1", book ),
                new FavoriteBook("2",book1))
        );

        FavoriteBookService bookService = new FavoriteBookService(booksRepo, idService);

        //WHEN
        List<FavoriteBook> actual = bookService.getBooks();

        //THEN
        assertEquals(List.of(

         new FavoriteBook("1", new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                 "adventure",2000,"Bloomsbury",
                 "London",100,"very good book",0)) ,

                new FavoriteBook("2",   new Book("2", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                        "adventure",2000,"Bloomsbury",
                        "London",100,"very good book",0))
        ), actual);

        Mockito.verify(booksRepo, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(booksRepo);
    }

    @Test
    void addFavoriteBookTest_returnBook(){

        Book bookToSave = new Book( "test-id","Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0);

        FavoriteBook favoriteBook = new FavoriteBook("test-id", new Book("test-id", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure", 2000, "Bloomsbury",
                "London", 100, "very good book", 0));


        // GIVEN
        Mockito.when(booksRepo.save(favoriteBook)).thenReturn(favoriteBook);
        Mockito.when(idService.newId()).thenReturn("test-id");

        FavoriteBookService bookService = new FavoriteBookService(booksRepo, idService);


        // WHEN
        FavoriteBook actual = bookService.addBook(bookToSave);

        // THEN
        Mockito.verify(booksRepo).save(favoriteBook);
        Mockito.verify(idService).newId();

        FavoriteBook expected = new FavoriteBook("test-id", new Book("test-id","Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));
        assertEquals(expected, actual);

    }

    @Test
    void deleteFavoriteBookTest(){

        //GIVEN
        Mockito.when(booksRepo.findByBookId(Mockito.any())).thenReturn(
                Optional.of(new FavoriteBook("1", new Book("2", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                                "adventure",2000,"Bloomsbury",
                                "London",100,"very good book",0))
                )
        );

        FavoriteBookService bookService = new FavoriteBookService(booksRepo, idService);

        // WHEN
        FavoriteBook actual = bookService.deleteBookById("1");

        //THEN
        assertEquals(
                new FavoriteBook("1", new Book("2", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                        "adventure",2000,"Bloomsbury",
                        "London",100,"very good book",0))

                , actual);

        Mockito.verify(booksRepo, Mockito.times(1)).findByBookId(Mockito.any());
        Mockito.verify(booksRepo, Mockito.times(1)).delete(Mockito.any());
        Mockito.verifyNoMoreInteractions(booksRepo);
    }
}



