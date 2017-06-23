package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;

import java.io.IOException;

/**
 * Created by marci on 07.11.16.
 */
public class StandaloneCodegenerator {
    public static void main(String[] args) throws IOException {
        EMFModelUtils.initializeEMF();
        Codegenerator codegenerator = new Codegenerator();
        codegenerator.generate();
    }
}
