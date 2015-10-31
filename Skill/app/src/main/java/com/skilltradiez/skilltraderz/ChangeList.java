package com.skilltradiez.skilltraderz;

import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.transport.NodeDisconnectedException;

import java.util.ArrayList;
import java.util.List;

class ChangeList {
  private List<Notification> notifications;
  
  ChangeList() {
    notifications = new ArrayList<Notification>();
  }

  public void add(Notification newNote) {
    notifications.add(newNote);
  }

  /*
   * Pushes all notifications to the internet through the User Database
   * - If the internet is not available or it is disconnected, the Notification
   * is not removed from the list
   * - If the commit is successful, then we remove the Notification
   */
  public void push(UserDatabase userDB) {
    List<Notification> finishedNotes = new ArrayList<Notification>();
    for (Notification note:notifications) {
      try {
        note.commit(userDB);
        finishedNotes.add(note);
      } catch (NoNodeAvailableException e1) {
        // Continue
      } catch (NodeDisconnectedException e2) {
        // Continue
      }
    }
    for (Notification done:finishedNotes)
      notifications.remove(done);
  }
}
