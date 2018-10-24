package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.deserialization.PlanElementKeyDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefKeySerializer;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

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
