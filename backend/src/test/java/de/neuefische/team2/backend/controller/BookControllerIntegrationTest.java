package de.neuefische.team2.backend.controller;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.repos.BooksRepo;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BooksRepo booksRepo;

    @DirtiesContext
    @Test
    void getBooksTest_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        // GIVEN
        booksRepo.save(new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));

        // WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/books"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
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
                             "views": 0
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus() );
    }

    @DirtiesContext
    @Test
    void updateBooksTest_shouldReturnBookWithUpdatedAuthor_whenBookWithUpdatedAuthorSent() throws Exception {
        //GIVEN
        booksRepo.save(new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                                                
                                    {
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
                             "views": 0
                                    }
                                    """).with(oidcLogin()
                        .userInfoToken(token ->
                                token.claim("login", "test-user"))))

                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
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
                             "views": 0
                         }
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus() );
    }

    @DirtiesContext
    @Test
    void getBookByIdTest_shouldReturnObjectWithTheId() throws Exception {
        //GIVEN
        Book book = booksRepo.save(new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling", "link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));
        String id = book.id();
        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", id))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
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
                             "views": 1
                        }
                        """))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus() );

    }

    @DirtiesContext
    @Test
    void getBookByNoExistingIdTest_shouldReturnNoObject() throws Exception {
        //GIVEN
        Book book = booksRepo.save(new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));
        String nonExistingId = "nonExistingId";
        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", nonExistingId))
                //THEN
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());

    }

    @DirtiesContext
    @Test
    void deleteBook_shouldReturnBook_whenThisObjectWasDeletedFromRepository() throws Exception {
        //GIVEN
        booksRepo.save(new Book("1", "Harry Potter und der Stein der Weisen", "J.K. Rowling","link",
                "adventure",2000,"Bloomsbury",
                "London",100,"very good book",0));

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))

                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                {
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
                             "views": 0
                                 }
                                """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus() );
    }

    @DirtiesContext
    @Test
    void addBookTest_shouldReturnOneObject_whenObjectWasSavedInRepository() throws Exception {

        // GIVEN
        // WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/books")
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
                .with(oidcLogin()
                        .userInfoToken(token ->
                                token.claim("login", "test-user"))))
        // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""

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
                """))

                .andExpect(jsonPath("$.id").isNotEmpty());

    }

}
