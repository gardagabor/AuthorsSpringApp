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

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BooksApiController implements BooksApi {

    private static final Logger log = LoggerFactory.getLogger(BooksApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public BooksApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Book>> allBooks() {
        //String accept = request.getHeader("Accept");
        List<Book> booksList = new ArrayList<>();
        for (Author author : AuthorRespoitory.getInstance().authorsList){
            for (Book book : author.getBooks()){
                booksList.add(book);
            }
        }
        return new ResponseEntity<List<Book>>(booksList,HttpStatus.OK);
    }

    public ResponseEntity<Void> modifyBook(@Parameter(in = ParameterIn.PATH, description = "The id of the book wanted to be modified", required=true, schema=@Schema()) @PathVariable("book_id") Integer bookId,@Parameter(in = ParameterIn.DEFAULT, description = "The modified Book", schema=@Schema()) @Valid @RequestBody Book body) {
        Book selectedBook = null;
        Author selectedAuthor = null;
        for (Author author : AuthorRespoitory.getInstance().authorsList){
            for (Book book : author.getBooks()){
                if(book.getId() == bookId){
                    selectedBook = book;
                    selectedAuthor = author;
                }

            }
        }

        selectedAuthor.getBooks().remove(selectedBook);
        selectedAuthor.getBooks().add(body);
        
    return new ResponseEntity<Void>(HttpStatus.OK);
    }




}
