package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * Suppose we have the very very dastardly case in which we have a user attempt to
 * register themselves as a user that we actually already have within out databse. This is a massive big big big problem
 * ad then we have the problem being addressed
 * by raising an exception. In particular this exception. This is going to be our defense mechanism
 * against having multiple users attempt to register as something such as
 * the username "XxXXXxXXxXxxXLegolasXxXxXx23248484Zx" or something obscene.
 *
 * ~~ACCESS:
 * This is actually going to be a fully public case in which we will actually have the ability
 * to utilize and create nd use this exception in ANY other class or anything of the
 * application! So suppose we wanted to use this xception in some manner.... we will now have the
 * ability in order to have the user make a second username for a username that we already have in
 * the database and then we have this exception be thrown in that instance.
 *
 * ~~CONSTRICTORS:
 * We have no specific constructor present within this exception being brought up that we
 * need to actually really worry about... so here we just create a new exception
 * and then this exception is thrown. We need nothing more.
 *
 * ATTRBUTES/METHODS:
 * This is an exception. We don't actually nee any attributes or methods present within this class!
 * Keeping it short and sweet and to the point!
 */
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
 * Created by sja2 on 10/28/15.
 */
public class UserAlreadyExistsException extends Exception {

}
