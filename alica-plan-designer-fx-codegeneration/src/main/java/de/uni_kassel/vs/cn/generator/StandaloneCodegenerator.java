package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.generator.cpp.CPPGeneratorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This Codegenerator console application (re)generates all plans/behaviours and terminates afterwards.
 */
public class StandaloneCodegenerator {
    private static final Logger LOG = LogManager.getLogger(StandaloneCodegenerator.class);

    public static void main(String[] args) throws IOException {
        Codegenerator codegenerator = new Codegenerator();
        codegenerator.generate();
    }
}
