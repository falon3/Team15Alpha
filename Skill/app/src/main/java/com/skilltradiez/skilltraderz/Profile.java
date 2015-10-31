package com.skilltradiez.skilltraderz;

/**
 * Created by sja2 on 10/28/15.
 */
public class Profile {
    private String username, nickname = "", email = "";
    private Object password;
    private Boolean shouldDownloadImages = true;
    private Image avatar;

    Profile(String username, String password) {
        this.username = username;
        try {
            setPassword(password);
        } catch (IllegalArgumentException e){}
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
}
