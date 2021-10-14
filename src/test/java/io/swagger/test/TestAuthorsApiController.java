package io.swagger.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.AuthorsApiController;
import io.swagger.model.Author;
import io.swagger.model.AuthorRespoitory;
import io.swagger.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorsApiController.class)
class TestAuthorsApiController {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAllAuthors() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.get("/authors")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getAuthorById() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.get("/authors/{id}/books", 100)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void createAuthor() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.post("/authors")
				.content(asJsonString(new Author(0,"Joe Test","2000-01-01",null)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		mvc.perform( MockMvcRequestBuilders
				.get("/authors/{id}/books", 0)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void addBookToAuthor() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.post("/authors/{id}/books",100)
				.content(asJsonString(new Book(0,"First Book","2012-01-01")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		mvc.perform( MockMvcRequestBuilders
				.get("/authors/{id}/books", 100)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}

	@Test
	public void  modifyAuthor() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.put("/authors/{id}", 100)
				.content(asJsonString(new Author(100, "Modified Joe", "2000-01-21",null)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());



		mvc.perform( MockMvcRequestBuilders
				.get("/authors")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[:1].name").value("Modified Joe"));

	}

	@BeforeEach
	public void init() throws Exception{
		mvc.perform( MockMvcRequestBuilders
				.post("/authors")
				.content(asJsonString(new Author(100,"Joe Init","1800-01-01",null)))
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