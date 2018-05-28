package de.uni_kassel.vs.cn.planDesigner.common;

import java.util.ResourceBundle;
import java.util.Locale;

public class I18NRepo {

    private static I18NRepo centralRepo;
    private ResourceBundle resourceBundle;

    public static I18NRepo getInstance() {
        if (centralRepo == null) {
            centralRepo = new I18NRepo();
        }
        return centralRepo;
    }

    private I18NRepo() {
        resourceBundle = ResourceBundle.getBundle("pld_globals");
    }

    public void setLocale(String language, String country)
    {
        Locale tmpLocal = new Locale(language, country);
        resourceBundle = ResourceBundle.getBundle("pld_globals", tmpLocal);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
