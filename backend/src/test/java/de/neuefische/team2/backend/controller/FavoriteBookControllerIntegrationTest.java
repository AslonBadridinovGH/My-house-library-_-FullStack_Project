package de.neuefische.team2.backend.controller;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.models.FavoriteBook;
import de.neuefische.team2.backend.repos.BooksRepo;
import de.neuefische.team2.backend.repos.FavoriteBooksRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteBookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FavoriteBooksRepo booksRepo;

    @DirtiesContext
    @Test
    void getFavoriteBooksTest_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Book book = new Book("1","Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0);

        // GIVEN
        booksRepo.save(new FavoriteBook("1", book ));

        // WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/favoriteBooks"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "id": "1",
                             book: {
                              "id": "1",
                              "title": "Harry Potter und der Stein der Weisen",
                             "author": "J.K. Rowling",
                             "img": "link",
                             "genre": "adventure",
                             "year": 2000,
                             "publisher": "Bloomsbury",
                             "city": "London",
                             "page": 100,
                             "description": "very good book",
                             "views": 0}
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus() );
    }

    @DirtiesContext
    @Test
    void addFavoriteTest_shouldReturnOneObject_whenObjectWasSavedInRepository() throws Exception {

        // GIVEN
        // WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/favoriteBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                         {
                                      
                             "title": "Harry Potter und der Stein der Weisen",
                             "author": "J.K. Rowling",
                             "img": "link",
                             "genre": "adventure",
                             "year": 2000,
                             "publisher": "Bloomsbury",
                             "city": "London",
                             "page": 100,
                             "description": "very good book",
                             "views": 0
                         }
                        """)
                )
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""

                  {
                          book: {
                             "title": "Harry Potter und der Stein der Weisen",
                             "author": "J.K. Rowling",
                             "img": "link",
                             "genre": "adventure",
                             "year": 2000,
                             "publisher": "Bloomsbury",
                             "city": "London",
                             "page": 100,
                             "description": "very good book",
                             "views": 0}             

                  }
                """))

                .andExpect(jsonPath("$.id").isNotEmpty());

    }




        @DirtiesContext
        @Test
        void deleteFavoriteBook_shouldReturnBook_whenThisObjectWasDeletedFromRepository() throws Exception {

            Book book = new Book("1","Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                    "adventure",2000,"Bloomsbury",
                    "London",100,"very good book",0);

            //GIVEN
            booksRepo.save(new FavoriteBook("1", book));

            //WHEN
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/api/favoriteBooks/1"))

                    //THEN
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                {
                              "id": "1",
                               book: {
                               id: "1",
                             "title": "Harry Potter und der Stein der Weisen",
                             "author": "J.K. Rowling",
                             "img": "link",
                             "genre": "adventure",
                             "year": 2000,
                             "publisher": "Bloomsbury",
                             "city": "London",
                             "page": 100,
                             "description": "very good book",
                             "views": 0
                               }
                                 }
                                """))
                    .andReturn();

            assertEquals(200, mvcResult.getResponse().getStatus() );
        }

}
