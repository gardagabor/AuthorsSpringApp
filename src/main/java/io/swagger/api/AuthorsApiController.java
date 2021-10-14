package io.swagger.api;

import io.swagger.model.Author;
import io.swagger.model.AuthorRespoitory;
import io.swagger.model.Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-10-05T17:59:37.064Z[GMT]")
@RestController
public class AuthorsApiController implements AuthorsApi {

    private static final Logger log = LoggerFactory.getLogger(AuthorsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthorsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;

    }

    public ResponseEntity<Void> addAuthor(@Parameter(in = ParameterIn.DEFAULT, description = "The new author", schema=@Schema()) @Valid @RequestBody Author body) {
        // String accept = request.getHeader("Accept");
        AuthorRespoitory.getInstance().authorsList.add(body);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> addNewBookToAuthor(@Parameter(in = ParameterIn.PATH, description = "The id of the author", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId,@Parameter(in = ParameterIn.DEFAULT, description = "The new book", schema=@Schema()) @Valid @RequestBody Book body) {
        // String accept = request.getHeader("Accept");
        for (Author author : AuthorRespoitory.getInstance().authorsList) {
            if(author.getId().equals(authorId)){
                author.addBooksItem(body);
            }
        }    
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<Author>> allAuthors() {
        return new ResponseEntity<List<Author>>(AuthorRespoitory.getInstance().authorsList, HttpStatus.OK);
    }

    public ResponseEntity<List<Book>> authorAllBooks(@Parameter(in = ParameterIn.PATH, description = "The id of the author", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId) {
        Author selectedAuthor = null;
        for (Author author : AuthorRespoitory.getInstance().authorsList) {
            if(author.getId().equals(authorId)){
               selectedAuthor = author;
            }
        } 
       return new ResponseEntity<List<Book>>(selectedAuthor.getBooks(), HttpStatus.OK);
    }

    public ResponseEntity<Void> modifyAuthor(@Parameter(in = ParameterIn.PATH, description = "The id of the author wanted to be modified", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId,@Parameter(in = ParameterIn.DEFAULT, description = "The modified author", schema=@Schema()) @Valid @RequestBody Author body) {
        Author selectedAuthor = null;
        for (Author author : AuthorRespoitory.getInstance().authorsList) {
            if(author.getId().equals(authorId)){
               selectedAuthor = author;
            }
        }
        AuthorRespoitory.getInstance().authorsList.remove(selectedAuthor);
        AuthorRespoitory.getInstance().authorsList.add(body);
        
    return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
