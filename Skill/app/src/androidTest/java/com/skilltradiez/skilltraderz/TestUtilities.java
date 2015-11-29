package com.skilltradiez.skilltraderz;

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
