package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;

import java.util.Comparator;

public class PlanElementComparator implements Comparator<PlanElement> {
    @Override
    public int compare(PlanElement o1, PlanElement o2) {
        return Long.signum(o1.getId() - o2.getId());
    }
}