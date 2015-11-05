package com.skilltradiez.skilltraderz;

/**
 * Created by nweninge on 11/4/15.
 */
public class GetResponse<T> {
    public String _index;
    public String _type;
    public String _id;
    public String _version;
    public String found;
    public T _source;
}
