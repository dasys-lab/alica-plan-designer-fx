package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.deserialization.PlanDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefSerializer;
import de.unikassel.vs.alica.planDesigner.deserialization.PlanElementKeyDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefKeySerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

import java.util.HashMap;

public abstract class PlanModelVisualizationObjectMixIn {

    @JsonSerialize(using = ExternalRefSerializer.class)
    @JsonDeserialize(using = PlanDeserializer.class)
    public abstract Plan getPlan();

    @JsonSerialize(keyUsing = InternalRefKeySerializer.class)
    @JsonDeserialize(keyUsing = PlanElementKeyDeserializer.class)
    protected HashMap<PlanElement, PmlUiExtension> extensionMap;
}
