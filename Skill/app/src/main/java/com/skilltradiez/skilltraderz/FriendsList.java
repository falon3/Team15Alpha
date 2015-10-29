package com.skilltradiez.skilltraderz;

import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class FriendsList {
    private UserID owner;
    private List<UserID> friendsList,
            potentialFriendsList,
            blockedUsers;
    FriendsList(UserID owner_id) {
        owner = owner_id;
    }

    public List<UserID> getFriends() {
        return friendsList;
    }

    public void requestAddFriend(User other_guy) {

    }

    public void confirmFriendRequest(User other_guy) {

    }

    public UserID getFriend(Integer index) {
        return friendsList.get(index);
    }

    public void removeFriend(User terrible_person) {
        for (UserID id:friendsList)
            if (id.equals(terrible_person.getUserID())) {
                friendsList.remove(id);
                return;
            }
    }

    public void blockUser(User worst_person) {
        blockedUsers.add(worst_person.getUserID());
    }

    public Boolean hasPendingFriendRequest(User that_guy) {
        for (UserID id:potentialFriendsList)
            if (id.equals(that_guy.getUserID()))
                return true;
        return false;
    }

    public Boolean hasFriend(User that_guy) {
        for (UserID id:friendsList)
            if (id.equals(that_guy.getUserID()))
                return true;
        return false;
    }
}
