package de.uni_kassel.vs.cn.planDesigner.view.img;

import javafx.scene.image.Image;

/**
 * Created by marci on 25.11.16.
 */
public class AlicaIcon extends Image {
    public AlicaIcon(String iconName) {
        super(AlicaIcon.class.getClassLoader().getResourceAsStream("images/" +
                iconName.replace("Impl", "").toLowerCase() + "16x16.png"));
    }
}
