package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.uni_kassel.vs.cn.planDesigner.deserialization.SimpleLongPropertyDeserializer;
import javafx.beans.property.SimpleLongProperty;

public abstract class PlanMixIn {

    @JsonDeserialize(using = SimpleLongPropertyDeserializer.class)
    protected SimpleLongProperty id;
}
