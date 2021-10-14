/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.28).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Author;
import io.swagger.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-10-05T17:59:37.064Z[GMT]")
@Validated
public interface AuthorsApi {

    @Operation(summary = "Add a new author", description = "", tags={ "authors" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "New author sucessfully added") })
    @RequestMapping(value = "/authors",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> addAuthor(@Parameter(in = ParameterIn.DEFAULT, description = "The new author", schema=@Schema()) @Valid @RequestBody Author body);


    @Operation(summary = "Add a new book for a specific author", description = "", tags={ "authors" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "New book sucessfully added to the author") })
    @RequestMapping(value = "/authors/{author_id}/books",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> addNewBookToAuthor(@Parameter(in = ParameterIn.PATH, description = "The id of the author", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId, @Parameter(in = ParameterIn.DEFAULT, description = "The new book", schema=@Schema()) @Valid @RequestBody Book body);


    @Operation(summary = "List all authors", description = "", tags={ "authors" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Author.class)))) })
    @RequestMapping(value = "/authors",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Author>> allAuthors();


    @Operation(summary = "List all books of a specific author", description = "", tags={ "authors" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Book.class)))) })
    @RequestMapping(value = "/authors/{author_id}/books",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Book>> authorAllBooks(@Parameter(in = ParameterIn.PATH, description = "The id of the author", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId);


    @Operation(summary = "Modify information for a specific author", description = "", tags={ "authors" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "New author sucessfully modified") })
    @RequestMapping(value = "/authors/{author_id}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> modifyAuthor(@Parameter(in = ParameterIn.PATH, description = "The id of the author wanted to be modified", required=true, schema=@Schema()) @PathVariable("author_id") Integer authorId, @Parameter(in = ParameterIn.DEFAULT, description = "The modified author", schema=@Schema()) @Valid @RequestBody Author body);

}
