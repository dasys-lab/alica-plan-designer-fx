package de.uni_kassel.vs.cn.planDesigner.ui.img;

import javafx.scene.image.Image;

/**
 * Created by marci on 25.11.16.
 */
public class AlicaIcon extends Image {
    public AlicaIcon(Class alicaClass) {
        super(AlicaIcon.class.getClassLoader().getResourceAsStream("images/" +
                alicaClass.getSimpleName().replace("Impl", "").toLowerCase() + "16x16.png"));
    }
}
