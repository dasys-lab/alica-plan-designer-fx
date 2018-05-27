package de.uni_kassel.vs.cn.planDesigner.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.uni_kassel.vs.cn.planDesigner.model.*;

import java.io.File;
import java.io.IOException;

public class JSonSerializer {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Behaviour behaviour = new Behaviour();
        behaviour.setFrequency(30);
        behaviour.setId(1);
        behaviour.setComment("Behaviour Comment String");
        behaviour.setName("TestBehaviour");

        Variable var = new Variable();
        var.setType("Variable Type String");
        var.setId(2);
        var.setComment("Variable Comment String");
        var.setName("TestVariable");
        behaviour.getVariables().add(var);

        PreCondition preCondition = new PreCondition();
        preCondition.setEnabled(true);
        preCondition.setPluginName("Plugin Name String");
        preCondition.setId(3);
        preCondition.setComment("PreCondition Comment String");
        preCondition.setName("TestPreCondition");
        behaviour.setPreCondition(preCondition);

        RuntimeCondition runtimeCondition = new RuntimeCondition();
        runtimeCondition.setPluginName("Plugin Name String");
        runtimeCondition.setId(4);
        runtimeCondition.setComment("RuntimeCondition Comment String");
        runtimeCondition.setName("TestRuntimeCondition");
        behaviour.setRuntimeCondition(runtimeCondition);

        PostCondition postCondition = new PostCondition();
        postCondition.setPluginName("Plugin Name String");
        postCondition.setId(5);
        postCondition.setComment("PostCondition Comment String");
        postCondition.setName("TestPostCondition");
        behaviour.setPostCondition(postCondition);

        behaviour.setDestinationPath("result.json");

        try {
            mapper.writeValue(new File(behaviour.getDestinationPath()), behaviour);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
