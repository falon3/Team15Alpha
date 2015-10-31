package com.skilltradiez.skilltraderz;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserID {
    private Number id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserID userID = (UserID) o;

        return !(id != null ? !id.equals(userID.id) : userID.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    UserID(Number num) {
        id = num;
    }

    public Number getID() {
        return id;
    }
}
