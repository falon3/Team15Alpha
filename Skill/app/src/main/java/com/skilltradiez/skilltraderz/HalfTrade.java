package com.skilltradiez.skilltraderz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nweninge on 11/5/15.
 */
public class HalfTrade extends Notification {
    private ID user;
    private List<ID> offer;
    private boolean accepted;
    private ID tradeID;
    private int part;

    public HalfTrade(ID trade, ID user, int part) {
        tradeID = trade;
        this.user = user;
        offer = new ArrayList<ID>();
        accepted = false;
        this.part = part;
    }

    public ID getUser() {
        return user;
    }

    public void setUser(ID user) {
        this.user = user;
        notifyDB();
    }

    public List<ID> getOffer() {
        return offer;
    }

    public void setOffer(List<Skill> offer) {
        List<ID> ids = new ArrayList<ID>();
        for (Skill skill : offer) {
            ids.add(skill.getSkillID());
        }
        this.offer = ids;
        notifyDB();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
        notifyDB();
    }

    @Override
    public boolean commit(UserDatabase userDB) {
        Elastic ela = userDB.getElastic();
        try {
            ela.updateDocument("trade", tradeID.toString(), this, "half" + part);
        } catch (IOException e) {
            notifyDB();
            return false; // try again later
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HalfTrade halfTrade = (HalfTrade) o;

        if (part != halfTrade.part) return false;
        return !(tradeID != null ? !tradeID.equals(halfTrade.tradeID) : halfTrade.tradeID != null);

    }

    @Override
    public int hashCode() {
        int result = tradeID != null ? tradeID.hashCode() : 0;
        result = 31 * result + part;
        return result;
    }
}
