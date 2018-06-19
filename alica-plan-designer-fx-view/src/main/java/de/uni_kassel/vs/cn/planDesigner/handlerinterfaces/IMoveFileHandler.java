package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import java.nio.file.Path;

public interface IMoveFileHandler {
    public void moveFile(long id, Path originalPath, Path newPath);
}
