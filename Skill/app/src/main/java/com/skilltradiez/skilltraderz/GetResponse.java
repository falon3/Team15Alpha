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


/**~~DESCRIPTION:
 * Elastic returns a bunch of meta data along with the actual data we want. This is a wrapper
 * around meta data that allows returns in responses.
 *
 * This is going to be correlated with the elastic class that we use (please read up more on
 * elastic for that respective class). In this instance we are basically using this to
 * butcher out the data that we want when we retrieve data from elastic search database.
 *
 * Meta data is evil. ALL ATTRIBUTES LISTED HERE
 *
 *
 * ~~CONSTRUCTOR:
 * There is no constructor aside from the default constructor.
 *
 * ~~ATTRIBUTES/METHODS:
 *     Do we want to retrieve this? Do we not want to? These attributes deal with things that
 *     we actually DO NOT care about getting out from elasticsearch.
 *
 * 1: _INDEX:
 *     Let's say that the database returns to us an index of what is in the database. Let's filter
 *     that!
 *
 * 2: _TYPE:
 *     Basically the same as above, we want to maintain the type when we read from the database.
 *     This is pretty core because the type maintained by the database is going to tell us
 *     information on what type is associated with that object.
 *
 * 3: _ID:
 *     We have users in our application, this is going to be our PRIMARY KEY for each particular
 *     user of our application. Given an ID (A unique numerical identifier) of a user we are then
 *     going to be able to actually retrieve the data that we want for that user.
 *
 *
 * 4: _VERSION:
 *     We're not honestly sure what this is, but it's meta data and we want to get rid of it.
 *
 *
 *
 * 5: FOUND:
 *     True or false will be returned from the database, telling us if it found the thing or not
 *     and this is basically just a very simple boolean that is going to be used in just the
 *     exact fashion that one would expect a boolean to be!
 *
 */

public class GetResponse<T> {
    public String _index;
    public String _type;
    public String _id;
    public String _version;
    public String found;
    public T _source;
}
