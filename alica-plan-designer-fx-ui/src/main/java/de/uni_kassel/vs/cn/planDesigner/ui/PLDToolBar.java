package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.common.DragableAlicaType;
import javafx.geometry.Orientation;
import javafx.scene.control.ToolBar;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marci on 23.11.16.
 */
public class PLDToolBar extends ToolBar {
    public PLDToolBar() {
        super();
        setOrientation(Orientation.VERTICAL);
        getItems().addAll(
                Stream.of(
                        DragableAlicaType.values())
                        .map(e -> {
                            try {
                                return new DragableHBox(PLDToolBar.class.getClassLoader()
                                        .loadClass(e.getAssociatedClass().getName()).getSimpleName().toLowerCase(), e);
                            } catch (ClassNotFoundException e1) {
                                return null;
                            }
                        })
                        .collect(Collectors.toList())
        );
    }
}
