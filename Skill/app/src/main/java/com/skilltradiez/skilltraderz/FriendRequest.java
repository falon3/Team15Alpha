package com.skilltradiez.skilltraderz;

/**
 * Created by Stephen on 2015-10-31.
 */
public class FriendRequest implements Notification {
    User requester, requested;

    FriendRequest(User actor1, User actor2) {
        requester = actor1;
        requested = actor2;
    }

    public void commit(UserDatabase userDB) {
        //TODO
    }
}
