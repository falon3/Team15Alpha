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
 * Suppose we have the very very dastardly case in which we have a user attempt to
 * register themselves as a user that we actually already have within out databse. This is a massive big big big problem
 * ad then we have the problem being addressed
 * by raising an exception. In particular this exception. This is going to be our defense mechanism
 * against having multiple users attempt to register as something such as
 * the username "XxXXXxXXxXxxXLegolasXxXxXx23248484Zx" or something obscene.
 */

public class UserAlreadyExistsException extends Exception {
    //Cover our behinds with constructors for this exception. No point in having a useless
    //empty exception.
    UserAlreadyExistsException() {super();}

    public UserAlreadyExistsException(String message) {super(message);}

    public UserAlreadyExistsException(String message, Throwable errorSource) {
        super(message, errorSource);
    }

    public UserAlreadyExistsException(Throwable errorSource){ super(errorSource);}
}
