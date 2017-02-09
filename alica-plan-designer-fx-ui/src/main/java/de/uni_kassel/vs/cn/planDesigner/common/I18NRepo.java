package de.uni_kassel.vs.cn.planDesigner.common;

import java.util.ResourceBundle;

/**
 * Created by marci on 27.11.16.
 */
public class I18NRepo {

    private static I18NRepo centralRepo;
    private final ResourceBundle resourceBundle;

    public static I18NRepo getInstance() {
        if (centralRepo == null) {
            centralRepo = new I18NRepo();
        }
        return centralRepo;
    }

    private I18NRepo() {
        resourceBundle = ResourceBundle.getBundle("pld_globals");
    }

    public static String getString(String key) {
        return getInstance().resourceBundle.getString(key);
    }
}
