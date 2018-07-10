package de.uni_kassel.vs.cn.planDesigner.serialize;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.io.File;
import java.io.IOException;

public class JSonSerializer {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        Plan plan = new Plan();
        plan.setName("TestMasterPlan");
        plan.setMasterPlan(true);
        plan.setComment("Test Comment String");
        plan.setRelativeDirectory("result.json");

        State state = new State();
        state.setName("Stop");
        state.setComment("Stops the robot");
        state.setParentPlan(plan);

        Behaviour behaviour = new Behaviour();
        behaviour.setFrequency(30);
        behaviour.setComment("Behaviour Comment String");
        behaviour.setName("TestBehaviour");
        behaviour.setRelativeDirectory("result.json");
        state.addAbstractPlan(behaviour);

        Behaviour behaviour2 = new Behaviour();
        behaviour2.setFrequency(30);
        behaviour2.setComment("Behaviour Comment String");
        behaviour2.setName("TestBehaviour2");
        behaviour2.setRelativeDirectory("result.json");
        state.addAbstractPlan(behaviour2);

        plan.getStates().add(state);

        State state2 = new State();
        state2.setName("Stop2");
        state2.setComment("Stops the robot too");
        state2.setParentPlan(plan);

        plan.getStates().add(state2);

        EntryPoint entryPoint = new EntryPoint();
        entryPoint.setMinCardinality(1);
        entryPoint.setMaxCardinality(2);
        entryPoint.setState(state);
        entryPoint.setName("TestEP");
        entryPoint.setPlan(plan);

        state.setEntryPoint(entryPoint);
        plan.getEntryPoints().add(entryPoint);

        TaskRepository taskRepository = new TaskRepository();
        taskRepository.setName("taskrepository");
        taskRepository.setRelativeDirectory("result.json");

        Task task =  new Task();
        task.setName("DefaultTask");

        entryPoint.setTask(task);
        task.setTaskRepository(taskRepository);
        taskRepository.getTasks().add(task);
        taskRepository.setDefaultTask(task);

//        Behaviour behaviour = new Behaviour();
//        behaviour.setFrequency(30);
//        behaviour.setComment("Behaviour Comment String");
//        behaviour.setName("TestBehaviour");
//
//        Variable var = new Variable();
//        var.setQuantifierType("Variable Type String");
//        var.setComment("Variable Comment String");
//        var.setName("TestVariable");
//        behaviour.getVariables().add(var);
//
//        PreCondition preCondition = new PreCondition();
//        preCondition.setEnabled(true);
//        preCondition.setPluginName("Plugin Name String");
//        preCondition.setComment("PreCondition Comment String");
//        preCondition.setName("TestPreCondition");
//        behaviour.setPreCondition(preCondition);
//
//        RuntimeCondition runtimeCondition = new RuntimeCondition();
//        runtimeCondition.setPluginName("Plugin Name String");
//        runtimeCondition.setComment("RuntimeCondition Comment String");
//        runtimeCondition.setName("TestRuntimeCondition");
//        behaviour.setRuntimeCondition(runtimeCondition);
//
//        PostCondition postCondition = new PostCondition();
//        postCondition.setPluginName("Plugin Name String");
//        postCondition.setComment("PostCondition Comment String");
//        postCondition.setName("TestPostCondition");
//        behaviour.setPostCondition(postCondition);
//
//        behaviour.setRelativeDirectory("result.json");
//
//        CapValue val = new CapValue();
//        val.setName("TestCapValue");
//        val.setComment("CapValue Comment String");
//
//        Characteristic charac = new Characteristic();
//        charac.setName("TestCharacteristic");
//        charac.setComment("Characteristic Comment String");
//        charac.setValue(val);
//
//        Role role = new Role();
//        role.setName("TestRole");
//        role.setComment("Role Comment String");
//        role.getCharacteristics().add(charac);

        try {

        File outfile = new File(plan.getRelativeDirectory());
        //TODO adds list brackets to serialized json causing Cannot deserialize instance of `de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan` out of START_ARRAY tokenk
            mapper.writeValue(outfile, plan);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
