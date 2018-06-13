package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

public class ParsedModelReference {
    // SINGLETON
    private static volatile ParsedModelReference instance;
    public static ParsedModelReference getInstance() {
        if (instance == null) {
            synchronized (ParsedModelReference.class) {
                if (instance == null) {
                    instance = new ParsedModelReference();
                }
            }
        }
        return instance;
    }

    long defaultTaskId;

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }
}
