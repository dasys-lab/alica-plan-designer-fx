package de.unikassel.vs.alica.planDesigner.uiextensionmodel;

import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;

public class BendPoint extends PositionedElement {
    private Transition transition;
    private BendPoint next;
    private BendPoint previous;

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public BendPoint getNext() {
        return next;
    }

    public void setNext(BendPoint next) {
        this.next = next;
    }

    public BendPoint getPrevious() {
        return previous;
    }

    public void setPrevious(BendPoint previous) {
        this.previous = previous;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

}
