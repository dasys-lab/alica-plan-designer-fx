package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import java.util.ArrayList;

public class PmlUiExtension extends PositionedElement {

    // ID of the plan, this pmlUIExtension object helps to layout its elements in the Plan Designer
    protected long layoutedModelElementId;
    protected int height;
    protected int width;
    protected boolean collapsed;
    protected boolean visible;
    protected ArrayList<Bendpoint> bendpoints;

    public PmlUiExtension() {
        bendpoints = new ArrayList<>();
    }

    public long getLayoutedModelElementId() {
        return layoutedModelElementId;
    }

    public void setLayoutedModelElementId(long layoutedModelElementId) {
        this.layoutedModelElementId = layoutedModelElementId;
    }

    int getWidth() {return this.width;}

    void setWidth(int width) {this.width = width;}

    int getHeight() {return this.height;}

    void setHeight(int height) {this.height = height;}

    boolean isCollapsed() {return this.collapsed;}

    void setCollapsed(boolean collapsed) {this.collapsed = collapsed;}

    public ArrayList<Bendpoint> getBendpoints() {return this.bendpoints;}

    boolean isVisible() {return this.visible;}

    void setVisible(boolean visible) {this.visible = visible;}

}
