package com.skilltradiez.skilltraderz;

/**
 * ~~DESCRIPTION: If we don't have a user that exists, we will throw this exception out!
 * At least it is slightly more graceful than just returning null and causing potential
 * return value issues in other parts of the program.
 */
public class UserDoesNotExistException extends Exception  {

    //Cover our behinds with constructors for this exception. No point in having a useless
    //empty exception.
    UserDoesNotExistException() {super();}


    public UserDoesNotExistException(String message) {super(message);}
    public UserDoesNotExistException(String message, Throwable errorSource) {
        super(message, errorSource);
    }
    public UserDoesNotExistException(Throwable errorSource){ super(errorSource);}

}
