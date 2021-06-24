package Application.Exceptions;

public class UserNotFoundException extends RuntimeException{
        UserNotFoundException(Long id){
            super("Kan geen gebruiker vinden met id: " + id);
        }
}
