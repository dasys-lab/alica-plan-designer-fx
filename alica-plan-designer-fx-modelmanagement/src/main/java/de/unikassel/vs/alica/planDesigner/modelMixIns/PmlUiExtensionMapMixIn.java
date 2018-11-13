package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.deserialization.PlanElementKeyDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefKeySerializer;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

import java.util.HashMap;

public abstract class PmlUiExtensionMapMixIn {
    @JsonSerialize(keyUsing = InternalRefKeySerializer.class)
    @JsonDeserialize(keyUsing = PlanElementKeyDeserializer.class)
    protected HashMap<PlanElement, PmlUiExtension> extensionHashMap;


    /**
     * Ignore this method during serialization, because the protected field extensionHashMap is serialized instead
     *
     * @return  the extensionHashMap
     */
    @JsonIgnore
    public abstract HashMap<PlanElement, PmlUiExtension> getExtension();
}
