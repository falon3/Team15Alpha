package com.skilltradiez.skilltraderz;

/**
 * If we don't have a user that exists, we will throw this exception out!
 * At least it is slightly more graceful than just returning null and causing potential
 * return value issues in other parts of the program.
 */
public class UserDoesNotExistException extends Exception  {

    //Cover our behinds with constructors for this exception. No point in having a useless
    //empty exception.
    /**
     * Given no parameters, call the superclass of this exception (Exception) with no params.
     */
    UserDoesNotExistException() {super();}

    /**
     * Given only a string message as a parameter, invoke the superclass with the message String.
     * @param message String input of displayed message.
     */
    public UserDoesNotExistException(String message) {super(message);}

    /**
     * Given both a String message and a Throwable source of error, call the superclass with both.
     * @param message String Object message to display.
     * @param errorSource Throwable Object.
     */
    public UserDoesNotExistException(String message, Throwable errorSource) {
        super(message, errorSource);
    }

    /**
     * Given only a throwable of the source of the error, invoke the superclass Exception with it.
     * @param errorSource Throwable Object.
     */
    public UserDoesNotExistException(Throwable errorSource){ super(errorSource);}

}
