package io.swagger.model;

import java.util.ArrayList;
import java.util.List;

public class AuthorRespoitory {

    private static AuthorRespoitory single_instance = null;

    public List<Author> authorsList;
    
    private AuthorRespoitory()
    {
        authorsList = new ArrayList<>();
    }
    
    public static AuthorRespoitory getInstance()
    {
        if (single_instance == null)
            single_instance = new AuthorRespoitory();
 
        return single_instance;
    }

    public static void deleteRepository(){
        single_instance = new AuthorRespoitory();
    }

}
