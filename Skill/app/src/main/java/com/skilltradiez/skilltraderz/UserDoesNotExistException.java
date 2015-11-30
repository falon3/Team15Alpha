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
