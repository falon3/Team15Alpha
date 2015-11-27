package com.skilltradiez.skilltraderz;

public class TradeIdIsNullException extends Exception {

    //Cover our behinds with constructors for this exception. No point in having a useless
    //empty exception.
    TradeIdIsNullException() {super("TradeIdIsNullException");}


    public TradeIdIsNullException(String message) {super(message);}
    public TradeIdIsNullException(String message, Throwable errorSource) {
        super(message, errorSource);
    }
    public TradeIdIsNullException(Throwable errorSource){ super(errorSource);}
}
