package de.uni_kassel.vs.cn.planDesigner.ui;

import java.io.File;

/**
 * Created by marci on 18.11.16.
 */
public class FileWrapper {

    private File file;

    public FileWrapper(File file) {
        this.file = file;
    }

    public static FileWrapper wrap(String path) {
        return new FileWrapper(new File(path));
    }

    public File unwrap() {
        return file;
    }

    @Override
    public String toString() {
        String[] split = file.getAbsolutePath().split(File.separator);
        return split[split.length-1];
    }
}
