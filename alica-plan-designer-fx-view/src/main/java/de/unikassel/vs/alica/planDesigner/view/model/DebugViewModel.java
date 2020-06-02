package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DebugViewModel extends SerializableViewModel {
    private AlicaConfigurationViewModel alicaConfigurationViewModel;
    private GlobalsConfViewModel globalsConfViewModel;

    public DebugViewModel(long id, String name, String type, AlicaConfigurationViewModel alicaConfigurationViewModel,
                          GlobalsConfViewModel globalsConfViewModel) {
        super(id, name, type);
        this.alicaConfigurationViewModel = alicaConfigurationViewModel;
        this.globalsConfViewModel = globalsConfViewModel;
    }

    public AlicaConfigurationViewModel getAlicaConfigurationViewModel() {
        return alicaConfigurationViewModel;
    }

    public GlobalsConfViewModel getGlobalsConfViewModel() {
        return globalsConfViewModel;
    }

    public void setAlicaConfigurationViewModel(AlicaConfigurationViewModel alicaConfigurationViewModel) {
        this.alicaConfigurationViewModel = alicaConfigurationViewModel;
    }

    public void setGlobalsConfViewModel(GlobalsConfViewModel globalsConfViewModel) {
        this.globalsConfViewModel = globalsConfViewModel;
    }
}
