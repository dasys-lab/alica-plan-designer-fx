package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;

import java.util.ArrayList;

public interface IShowUsageHandler {
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
}
