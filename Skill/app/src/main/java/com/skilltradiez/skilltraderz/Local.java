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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * In our application it is going to be essential for local saves to be incorporated through
 * some sort of mecahnsm, this class, the local class is going to be what we're actually
 * going to utilize in order to achieve this end.
 */

public class Local {
    LocalPersistentObject save_object = null;
    private static final String SAVE_FILE = "/sdcard/save_file.sav";

    /**
     * When this constructor is invoked it will attempt to use the readFromFile method provided
     * in this class- if it fails then it will return an IOException error and throw a new
     * RuntimeException.
     */
    Local() {
        try {
            readFromFile();
        } catch (IOException e) {
            // Not sure what this means
            e.printStackTrace();
            throw new RuntimeException("Local could not save");
        }
    }

    /**
     * Classical getter method that returns the save object on the user's device.
     * The returned object is called a LocalPersistentObject.
     *
     * @return LocalPersistentObject Object
     */
    public LocalPersistentObject getLocalData() {
        return save_object;
    }

    /**
     * When this method is called and passed all the parameters, it will take all of the parameters
     * that it is given, set them to the save_object and then attempt to save it to the device.
     *
     * This method takes in a lot of parameters, and will assign to the save_object each of the
     *    parameters one by one.
     * Attempts to invoke the saveToFile method, if this fails then it will catch an IOException
     *    which will throw the RuntimeException that there was a failure in saving locally to the
     *    user's device.
     * If all goes well, the user will have a local save of the information.
     *
     * @param me User Object.
     * @param friends Collection of User Objects.
     * @param skillz Collection of Skill Objects.
     * @param trades Collection of Trade Objects
     * @param changeList Collcetion of ChangeList Objects.
     */
    public void saveToFile(User me, Collection<User> friends, Collection<Skill> skillz,
                           Collection<Trade> trades, ChangeList changeList) {
        save_object.setCurrentUser(me);
        save_object.setUsers(friends);
        save_object.setSkillz(skillz);
        save_object.setTrades(trades);
        //save_object.setNotifications(notifications);
        save_object.setNotifications(changeList);

        try {
            saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("this died in Local");
        }
    }

    /**
     * When invoked, will attempt to save the save_object LocalPersistentObject to the user's
     * device.
     *
     * Assigns a FileOutputStream object to null.
     * Creates a new File object sending in the parameter SAVE_FILE.
     * If a file does not exist, will create a new file.
     * Assigns the FileOutputStream to a new FileOutputStream Object with the parameter of the
     *    File object created above.
     * Creates a new BufferedWriterObject with the parameter of a new OutputStreamWriter which was
     *    instantiated with the FileOutputStream Object created earlier in the method.
     * Creates a new Gson object.
     * Invokes the toJson method on the new Gson Object on the save_object and puts it into the
     *    created ButteredWriter Object.
     * Flushes the output, to ensure that we have updated all of the information and none is left
     *    in a staged state that isn't actually completed.
     * Closes the FileOutputStream.
     * The file should now be saved locally to the user's device.
     *
     * @throws IOException
     */
    public void saveToFile() throws IOException {
        FileOutputStream fop = null;
        File file = new File(SAVE_FILE);

        // create file if doesn't exist
        if (!file.exists()) file.createNewFile();
        fop = new FileOutputStream(file);
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fop));
        Gson gson = new Gson();
        gson.toJson(save_object, output);
        output.flush();
        fop.close();
    }

    /**
     * Reads the LocalPersistentObject from the user's device, and then returns this
     * LocalPersistentObject.
     *
     * Creates a new File Object by passing in the parameter SAVE_FILE.
     * If the file does not exist, create a new file by invoking the File Object's createNewFile
     *    method.
     * Creates a new FileInputStream by passing in the File Object in as a parameter.
     * Creates a new BufferedReader object by passing as a parameter into the constructor an
     *    InputStreamReader by passing into THIS constructor the FileInputStream Object made above.
     * Creates a new Gson Object.
     * Attempts to obtain the Type of the LocalPersistentObject by using the TypeToken standard,
     *   then will assign the save_object variable with the gson.fromJson of the BufferedReader
     *   Object created and the Type. If this fails we will throw a RuntimeException.
     * If the save_object is null after all of that, we create a new LocalPersistentObject Object.
     * We then return the save_object LocalPersistentObject.
     *
     *
     * @return LocalPersistentObject
     * @throws IOException
     */
    public LocalPersistentObject readFromFile() throws IOException {
        FileInputStream fip = null;
        File file = new File(SAVE_FILE);

        // create file if doesn't exist
        if (!file.exists()) file.createNewFile();
        fip = new FileInputStream(file);
        BufferedReader in = new BufferedReader(new InputStreamReader(fip));
        Gson gson = new Gson();

        try {
            Type lpo_type = new TypeToken<LocalPersistentObject>(){}.getType();
            save_object = gson.fromJson(in, lpo_type);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (save_object == null) save_object = new LocalPersistentObject();
        in.close();
        return save_object;
    }

    /**
     * When invoked, will create a new File based upon the parameter SAVE_FILE and then will
     * invoke the File Object's delete method- deleting the file from the user's local device.
     *
     * Creates a new File Object using the class variable SAVE_FILE as a parameter.
     * Invokes the delete method from the File Object.
     *
     * @throws IOException
     */
    public void deleteFile() throws IOException {
        File file = new File(SAVE_FILE);
        file.delete();
    }
}