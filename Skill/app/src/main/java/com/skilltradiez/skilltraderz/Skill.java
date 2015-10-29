package com.skilltradiez.skilltraderz;

/**
 * A skill represents something that a person can do.
 */
public class Skill {
    private String name;
    private String category;
    private Image image;
    private boolean visible;
    private String description;

    Skill(String skill_name) {
        setName(skill_name);
        setCategory("Misc.");
        setVisible(true);//Default is visible
        setDescription("");//Empty String
        setImage(new NullImage());
    }

    Skill(String skill_name, String category) {
        setName(skill_name);
        setCategory(category);
        setVisible(true);//Default is visible
        setDescription("");//Empty String
        setImage(new NullImage());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void deleteImage() {
        setImage(new NullImage());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
