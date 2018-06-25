package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.util.ArrayList;

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

    ArrayList<Task> incompleteTasks = new ArrayList<>();

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }

    public void addIncompleteTask(Task incompleteTask) {
        incompleteTasks.add(incompleteTask);
    }
}
