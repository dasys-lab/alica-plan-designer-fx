package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import java.util.ArrayList;

/**
 * Offers access to all opened behaviours. There is one {@link BehaviourTab} for each opened behaviour.
 */
public class BehaviourViewModel {

    ArrayList<BehaviourTab> behaviourTabs;

    public BehaviourViewModel () {
        behaviourTabs = new ArrayList<>();
    }

    public void addBehaviourTab(BehaviourTab behaviourTab) {
        behaviourTabs.add(behaviourTab);
    }
}
