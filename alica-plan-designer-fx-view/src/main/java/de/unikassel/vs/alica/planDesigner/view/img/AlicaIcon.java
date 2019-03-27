package de.unikassel.vs.alica.planDesigner.view.img;

import javafx.scene.image.Image;

public class AlicaIcon extends Image {

    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    private String resourcePath;

    public enum Size {
        SMALL("16x16"),
        BIG("24x24"),
        SYNC("36x24"),
        NONE("");

        String size;
        Size(String size) {
            this.size = size;
        }
    }

    public AlicaIcon(String iconName, Size size) {
        super(AlicaIcon.class.getClassLoader().getResourceAsStream("images/" +
                iconName.toLowerCase() + size.size + ".png"));
        resourcePath = "images/" + iconName.toLowerCase() + size.size + ".png";
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
