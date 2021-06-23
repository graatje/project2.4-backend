package Exceptions;

public class ChatMessageNotFoundException extends RuntimeException{
    ChatMessageNotFoundException(long id){ super("Kan geen chatbericht vinden met id: " + id); }
}
