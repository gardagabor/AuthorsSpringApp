package io.swagger.testingweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.AuthorsApiController;
import io.swagger.api.BooksApiController;
import io.swagger.model.Author;
import io.swagger.model.AuthorRespoitory;
import io.swagger.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BooksApiController.class)
class TestBooksApiController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllBooks() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void modifyBook() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .put("/books/{book_id}",0)
                .content(asJsonString(new Book(0, "Modified Book", "2010-11-21")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform( MockMvcRequestBuilders
                .get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[:1].title").value("Modified Book"));
    }

    @BeforeEach
    public void init() throws Exception{
        ArrayList<Book>books = new ArrayList<>();
        books.add(new Book(0,"Test Book","2000-01-01"));

        mvc.perform( MockMvcRequestBuilders
                .post("/authors")
                .content(asJsonString(new Author(100,"Joe Init","1800-01-01",books)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @AfterEach
    public void teardown() throws Exception{
        AuthorRespoitory.deleteRepository();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
