package Exceptions;

public class ThreadNotFoundException extends RuntimeException{
    public ThreadNotFoundException(Long id){
        super("Kan geen forum thread vinden met id: " + id);
    }
}
