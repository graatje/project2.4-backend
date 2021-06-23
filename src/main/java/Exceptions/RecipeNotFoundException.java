package Exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long id){
        super("Kan geen recept vinden met id: " + id);
    }
}
