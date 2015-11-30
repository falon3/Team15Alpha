package com.skilltradiez.skilltraderz;
/*
 *    Team15Alpha
 *    AppName: SkillTradiez (Subject to change)
 *    Copyright (C) 2015  Stephen Andersen, Falon Scheers, Elyse Hill, Noah Weninger, Cole Evans
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Suppose a Trade ID Object is being looked for by application, if it doesn't exist or returns
 * null then we have a large problem that can easily crash numerous parts of our application.
 * Null pointer exceptions aren't fun to deal with. This exception was specially crafted to take
 * into account the possibility that there may be a time when we have a Trade ID equal to null
 * appear- and we want to handle it with grace.
 */
public class TradeIdIsNullException extends Exception {

    //Cover our behinds with constructors for this exception. No point in having a useless
    //empty exception.

    /**
     * Given no parameters, call the superclass of this exception (Exception) with no params.
     */
    TradeIdIsNullException() {super("TradeIdIsNullException");}


    /**
     * Given only a string message as a parameter, invoke the superclass with the message String.
     * @param message String input of displayed message.
     */

    public TradeIdIsNullException(String message) {super(message);}

    /**
     * Given only a throwable of the source of the error, invoke the superclass Exception with it.
     * @param errorSource Throwable Object.
     */
    public TradeIdIsNullException(Throwable errorSource){ super(errorSource);}
    
    /**
     * Given both a String message and a Throwable source of error, call the superclass with both.
     * @param message String Object message to display.
     * @param errorSource Throwable Object.
     */
    public TradeIdIsNullException(String message, Throwable errorSource) {
        super(message, errorSource);
    }


}
