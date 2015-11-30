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
 * This is a broken version of the HTTPClient primarily used for testing. Has a few methods to
 * allow us to check out what is going on.
 */
import android.util.Log;
import java.io.IOException;

public class BrokenHTTPClient extends HTTPClient{

    /**
     * Does an HTTP GET to the given URL. Returns this as a String.
     * @param url TheURL to be used by the HTTPClient.
     * @return String of gettingness.
     * @throws IOException
     */
        public String get(String url) throws IOException {
            Log.d("get string", "GET" + url);
            throw new IOException();
        }

        /**
         * Does an HTTP POST to the given url, with the given data (JSON string) and returns the
         * response as a string.
         * @param url String of the URL.
         * @param data String of the data.
         * @return String of the post.
         * @throws IOException
         */
        public String post(String url, String data) throws IOException {
            Log.d("post string", "POST" + url);
            throw new IOException();
        }

        /**
         * Does an HTTP DELETE on the given url and returns the response as a string.
         * @param url String of the URL to be utilized by this method.
         * @return String of deletion.
         * @throws IOException
         */
        public String delete(String url) throws IOException {
            Log.d("delete string", "DELETE" + url);
            throw new IOException();
        }
}
