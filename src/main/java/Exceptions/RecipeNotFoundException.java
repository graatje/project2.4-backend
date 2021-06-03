package Exceptions;

public class RecipeNotFoundException extends RuntimeException {

    RecipeNotFoundException(Long id){
        super("Kan geen recept vinden met id: " + id);
    }
}
