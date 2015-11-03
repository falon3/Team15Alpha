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
public class Profile implements Notification {
    private String username, nickname = "", email = "";
    private Object password;
    private Boolean shouldDownloadImages = true;
    private Image avatar;

    Profile(String username, String password) {
        this.username = username;
        try {
            setPassword(password);
        } catch (IllegalArgumentException e) {
        }
        deleteAvatar();
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String name) throws IllegalArgumentException {
        if (name.length() > 12)
            throw new IllegalArgumentException();
        this.nickname = name;
    }

    protected void setPassword(String password) throws IllegalArgumentException {
        if (password.length() > 12)
            throw new IllegalArgumentException();
        // Encrypt it?
        this.password = password.getBytes();
    }

    protected Boolean isPassword(String password) {
        return this.password.equals(password.getBytes());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email.length() > 12)
            throw new IllegalArgumentException();
        this.email = email;
    }

    public void deleteAvatar() {
        avatar = new NullImage();
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Boolean getShouldDownloadImages() {
        return shouldDownloadImages;
    }

    public void setShouldDownloadImages(Boolean shouldDownloadImages) {
        this.shouldDownloadImages = shouldDownloadImages;
    }

    public void commit(UserDatabase userDB) {
        //TODO
    }
}
