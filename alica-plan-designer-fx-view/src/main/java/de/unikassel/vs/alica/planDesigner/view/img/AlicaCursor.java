package de.unikassel.vs.alica.planDesigner.view.img;

import javafx.scene.ImageCursor;

public class AlicaCursor extends ImageCursor {

    public enum Type {
        //transitions
        transition,
        forbidden_transition,
        add_transition,
        bendpoint_transition,
        bendpoint_transition_delete,

        // state
        state,
        forbidden_state,
        add_state,

        //successstate
        successstate,
        forbidden_successstate,
        add_successstate,

        //failurestate
        failurestate,
        forbidden_failurestate,
        add_failurestate,

        //entrypoint
        entrypoint,
        forbidden_entrypoint,
        add_entrypoint,

        //behaviour
        behaviour,
        forbidden_behaviour,
        add_behaviour,

        //initstateconnection
        initstateconnection,
        forbidden_initstateconnection,
        add_initstateconnection,

        //synchronisation
        synchronisation,
        forbidden_synchronisation,
        add_synchronisation,

        //synctransition
        synctransition,
        forbidden_synctransition,
        add_synctransition,
        bendpoint_synctransition,

        //postcondition
        postcondition,
        forbidden_postcondition,
        add_postcondition,

        //plantypes
        tasks,
        plantype,
        masterplan,
        plan,

        //common
        add,
        forbidden,

        //folder
        folder
    }

    public AlicaCursor(Type type) {
        super(new AlicaIcon(type.name(), AlicaIcon.Size.SMALL));
    }

    public AlicaCursor(Type type, int x, int y) {
        super(new AlicaIcon(type.name(), AlicaIcon.Size.SMALL), x, y);
    }

    //For get BigIcon
    public AlicaCursor(Type type, String string) {
        super(new AlicaIcon(type.name(), AlicaIcon.Size.BIG));
    }
}
