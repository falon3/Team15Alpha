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
import java.util.Random;

/**
 * Created by nweninge on 11/29/15.
 */
public class TestUtilities {
    private static Random random = new Random();
    private static String[] components = new String[] {
            "aeo", "rom", "ina", "lao", "nye", "yso", "poa", "oem", "yula"
    };
    public static String getRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(components[random.nextInt(components.length)]);
        }
        return sb.toString();
    }
}
